package tests.stellarburger.user;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import stellarburgers.api.requests.UserApi;
import stellarburgers.pojo.User;
import stellarburgers.api.checkers.Checkers;
import tests.stellarburger.utils.Postcondition;
import tests.stellarburger.utils.Precondition;
import tests.stellarburger.utils.UtilMethods;
import tests.stellarburger.utils.FakerUtils;
import tests.stellarburger.utils.BaseTest;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@Epic("Tests for checking user registration functionality")
class RegisterTests extends BaseTest {

    @Test
    @DisplayName("POST /api/auth/register should create user. Positive test.")
    @Description("Positive test for register user. Status code should be 200, also checking json structure")
    void shouldCreateUser() {
        User user = FakerUtils.getFakeUser();

        Response registerResponse = UserApi.createUser(user);
        Checkers.check200Success(registerResponse);
        UserTestsHelper.checkRegisterResponseStructure(registerResponse, user);

        String accessToken = getBearerTokenFromRegisterResponse(registerResponse);
        Postcondition.User.delete(accessToken);
    }

    @Test
    @DisplayName("POST /api/auth/register should not create user. Negative test trying create user with same email.")
    @Description("Negative test for register user. Status code should be 403, also checking json structure")
    void shouldNotCreateCourierWithSameLogin() {
        boolean success = false;
        String message = "User already exists";

        User user = FakerUtils.getFakeUser();

        Response registerResponse = Precondition.User.register(user);

        Response registerWithSameUserResponse = UserApi.createUser(user);
        Checkers.check403Forbidden(registerWithSameUserResponse);
        UtilMethods.checkSuccessResponseStructure(registerWithSameUserResponse, success, message);

        String accessToken = getBearerTokenFromRegisterResponse(registerResponse);
        Postcondition.User.delete(accessToken);
    }

    private static Stream<Arguments> missedParameterData() {
        User user = FakerUtils.getFakeUser();
        return Stream.of(
                arguments(null, user.getEmail(), user.getPassword()),
                arguments(user.getName(), null, user.getPassword()),
                arguments(user.getName(), user.getEmail(), null)
        );
    }

    @ParameterizedTest
    @MethodSource("missedParameterData")
    @DisplayName("POST /api/auth/register should not create user. Negative test, trying create user " +
            "with missed parameter in json.")
    @Description("Negative test for register user. Status code should be 403, also checking json structure")
    void shouldNotCreateCourierWithMissedParameter(String name, String email, String password) {
        boolean success = false;
        String message = "Email, password and name are required fields";

        User user = User.with()
                .name(name)
                .email(email)
                .password(password);

        Response registerResponse = UserApi.createUser(user);
        Checkers.check403Forbidden(registerResponse);
        UtilMethods.checkSuccessResponseStructure(registerResponse, success, message);

        if (registerResponse.statusCode() == 200) {
            String accessToken = getBearerTokenFromRegisterResponse(registerResponse);
            Postcondition.User.delete(accessToken);
        }
    }

    private String getBearerTokenFromRegisterResponse(Response response) {
        return UtilMethods.getDataFromResponse(response, UserTestsHelper.ACCESS_TOKEN_JPATH_REGISTER);
    }

}
