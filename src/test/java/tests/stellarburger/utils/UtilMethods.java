package tests.stellarburger.utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import stellarburgers.api.checkers.Checkers;

import static org.hamcrest.CoreMatchers.is;

public class UtilMethods {
    private UtilMethods() {

    }

    @Step("Check success status and message in json")
    public static void checkSuccessResponseStructure(Response response, boolean success, String message) {
        Checkers.checkAnswerInResponse(response, "success", is(success));
        Checkers.checkAnswerInResponse(response, "message", is(message));
    }

    public static <T> T getDataFromResponse(Response response, String jsonPath) {
        return response.jsonPath().get(jsonPath);
    }

    public static String getBearerTokenFromRegisterResponse(Response response) {
        return UtilMethods.getDataFromResponse(response, Constants.ACCESS_TOKEN_JPATH_REGISTER);
    }

}
