package tests.stellarburger.user;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stellarburgers.pojo.User;
import stellarburgers.api.checkers.Checkers;
import tests.stellarburger.utils.Postcondition;
import tests.stellarburger.utils.Precondition;
import stellarburgers.api.requests.UserApi;
import tests.stellarburger.utils.UtilMethods;
import tests.stellarburger.utils.FakerUtils;
import tests.stellarburger.utils.BaseTest;

@Epic("Tests for checking user login functionality")
class LoginTests extends BaseTest {

    @Test
    @DisplayName("POST /api/auth/login. Positive test. Login with existed user.")
    @Description("Positive test for login. Status code should be 200, also checking json")
    void shouldLoginUserExisted() {
        User user = FakerUtils.getFakeUser();

        Precondition.User.register(user);

        Response responseLogin = UserApi.loginUser(
                User.with()
                        .email(user.getEmail())
                        .password(user.getPassword())
        );
        Checkers.check200Success(responseLogin);

        UserTestsHelper.checkLoginResponseStructure(responseLogin, user);

        String accessToken = getBearerTokenFromLoginResponse(responseLogin);
        Postcondition.User.delete(accessToken);
    }

    @Test
    @DisplayName("POST /api/auth/login. Negative test, trying login with non-existed user.")
    @Description("Negative test for login. Status code should be 401, also checking json. Should not login")
    void shouldNotLoginUserDidNotExist() {
        boolean success = false;
        String message = "email or password are incorrect";

        User user = User.with()
                        .email(FakerUtils.getFakeEmail())
                        .password(FakerUtils.getFakePassword());

        Response responseLogin = UserApi.loginUser(user);
        Checkers.check401Unauthorized(responseLogin);
        UtilMethods.checkSuccessResponseStructure(responseLogin, success, message);
    }

    @Test
    @DisplayName("POST /api/auth/login. Negative test, trying login with existed user and wrong password.")
    @Description("Negative test for login. Status code should be 401, also checking json. Should not login")
    void shouldNotLoginUserExisted() {
        boolean success = false;
        String message = "email or password are incorrect";

        User userToCreate = FakerUtils.getFakeUser();
        User badUser = User.with()
                            .email(userToCreate.getEmail())
                            .password(FakerUtils.getFakePassword());

        Response responseCreate = Precondition.User.register(userToCreate);

        Response responseLogin = UserApi.loginUser(badUser);
        Checkers.check401Unauthorized(responseLogin);
        UtilMethods.checkSuccessResponseStructure(responseLogin, success, message);

        String accessToken = getBearerTokenFromLoginResponse(responseCreate);
        Postcondition.User.delete(accessToken);
    }

    private String getBearerTokenFromLoginResponse(Response response) {
        return UtilMethods.getDataFromResponse(response, UserTestsHelper.ACCESS_TOKEN_JPATH_LOGIN);
    }

}
