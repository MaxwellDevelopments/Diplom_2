package tests.stellarburger.user;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import stellarburgers.pojo.User;
import stellarburgers.api.checkers.Checkers;
import tests.stellarburger.utils.*;
import stellarburgers.api.requests.UserApi;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@Epic("Tests for checking user change info functionality")
class ChangeInfoTests extends BaseTest {

    private String ACCESS_TOKEN_VALUE;
    User userToCreate;

    @BeforeEach
    void createUser() {
        userToCreate = FakerUtils.getFakeUser();
        Response responseRegister = Precondition.User.register(userToCreate);
        ACCESS_TOKEN_VALUE = getBearerTokenFromRegisterResponse(responseRegister);
    }

    private static Stream<Arguments> shouldChangeInfoData() {

        return Stream.of(
                arguments(User.with().email(FakerUtils.getFakeEmail())),
                arguments(FakerUtils.getFakeUser()),
                arguments(
                        User.with()
                                .password(FakerUtils.getFakePassword())
                                .email(FakerUtils.getFakeEmail())
                ),
                arguments(
                        User.with()
                                .password(FakerUtils.getFakePassword())
                                .name(FakerUtils.getFakeName())
                ),
                arguments(
                        User.with()
                                .name(FakerUtils.getFakeName())
                                .email(FakerUtils.getFakeEmail())
                )
        );
    }

    @ParameterizedTest
    @MethodSource("shouldChangeInfoData")
    @DisplayName("PATCH /api/auth/user should change user info. Positive test, authorized user")
    @Description("Positive test for changing user's info. Status code should be 200, also checking json structure")
    void shouldChangeInfoAuthorizedUser(User userWithNewInfo) {
        String newPassword = userWithNewInfo.getPassword();

        Response responseChangeInfo = UserApi.changeUserInfo(userWithNewInfo, ACCESS_TOKEN_VALUE);
        Checkers.check200Success(responseChangeInfo);


        UserTestsHelper.checkChangeInfoJsonGood(responseChangeInfo, userWithNewInfo);
        if (newPassword != null) {
            String newEmail =
                    userWithNewInfo.getEmail() != null ? userWithNewInfo.getEmail() : userToCreate.getEmail();
            UserTestsHelper.checkChangeInfoPasswordChanged(newPassword, newEmail);
        }

    }

    @Test
    @DisplayName("PATCH /api/auth/user should change user info. Negative test, unauthorized user")
    @Description("Negative test for changing user's info. Status code should be 401, also checking json structure")
    void shouldNotChangeInfoUnauthorizedUser() {
        User userWithNewInfo = FakerUtils.getFakeUser();

        Response responseChangeInfo = UserApi.changeUserInfo(userWithNewInfo, null);
        Checkers.check401Unauthorized(responseChangeInfo);

        Response responseLogin = UserApi.loginUser(userToCreate);
        Checkers.check200Success(responseLogin);

        unauthorizedUserDoNotChangeInfo(responseLogin);
    }

    @Step("Check that info has not been changed")
    private void unauthorizedUserDoNotChangeInfo(Response response) {
        UserTestsHelper.checkLoginResponseStructure(response, userToCreate);
    }


    @AfterEach
    void deleteUser() {
        Postcondition.User.delete(ACCESS_TOKEN_VALUE);
    }

    private String getBearerTokenFromRegisterResponse(Response response) {
        return UtilMethods.getDataFromResponse(response, UserTestsHelper.ACCESS_TOKEN_JPATH_REGISTER);
    }
}
