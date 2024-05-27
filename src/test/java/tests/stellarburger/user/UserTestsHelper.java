package tests.stellarburger.user;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import stellarburgers.pojo.User;
import stellarburgers.api.checkers.Checkers;
import stellarburgers.api.requests.UserApi;

import static org.hamcrest.CoreMatchers.*;

class UserTestsHelper {

    private UserTestsHelper() {

    }

    protected static final String ACCESS_TOKEN_JPATH_REGISTER = "accessToken";
    protected static final String ACCESS_TOKEN_JPATH_LOGIN = "accessToken";

    @Step("Check json structure in register response")
    protected static void checkRegisterResponseStructure(Response response, User user) {
        Checkers.checkAnswerInResponse(response, "success", is(true));
        Checkers.checkAnswerInResponse(response, "user.email", is(user.getEmail()));
        Checkers.checkAnswerInResponse(response, "user.name", is(user.getName()));
        Checkers.checkAnswerInResponse(response, ACCESS_TOKEN_JPATH_REGISTER, startsWith("Bearer"));
        Checkers.checkAnswerInResponse(response, "refreshToken", notNullValue());
    }

    @Step("Check json structure in login response")
    protected static void checkLoginResponseStructure(Response response, User user) {
        Checkers.checkAnswerInResponse(response, "success", is(true));
        Checkers.checkAnswerInResponse(response, "user.email", is(user.getEmail()));
        Checkers.checkAnswerInResponse(response, "user.name", is(user.getName()));
        Checkers.checkAnswerInResponse(response, ACCESS_TOKEN_JPATH_LOGIN, startsWith("Bearer"));
        Checkers.checkAnswerInResponse(response, "refreshToken", notNullValue());
    }



    @Step("Checking json structure in change user info response")
    protected static void checkChangeInfoJsonGood(Response response, User userWithNewInfo) {
        boolean success = true;

        String userEmail = userWithNewInfo.getEmail();
        String userName = userWithNewInfo.getName();

        Checkers.checkAnswerInResponse(response, "success", is(success));
        Checkers.checkAnswerInResponse(response, "user", notNullValue());
        if (userEmail != null) {
            Checkers.checkAnswerInResponse(response, "user.email", is(userEmail));
        }
        if (userName != null) {
            Checkers.checkAnswerInResponse(response, "user.name", is(userName));
        }
    }

    @Step("Checking that password changed and can login with new password")
    protected static void checkChangeInfoPasswordChanged(String newPassword, String email) {
        User user = User.with()
                .email(email)
                .password(newPassword);

        Response responseLogin = UserApi.loginUser(user);
        Checkers.check200Success(responseLogin);
    }



}
