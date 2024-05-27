package tests.stellarburger.utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import stellarburgers.api.checkers.Checkers;
import stellarburgers.api.requests.UserApi;

import static org.hamcrest.CoreMatchers.is;


public class Postcondition {

    private Postcondition(){

    }

    public static class User {
        private User() {

        }

        @Step("Postcondition: delete user")
        public static void delete(String accessToken) {
            Boolean success = true;
            String message = "User successfully removed";

            Response deleteResponse = UserApi.deleteUser(accessToken);
            Checkers.check202Accepted(deleteResponse);
            Checkers.checkAnswerInResponse(deleteResponse, "success", is(success));
            Checkers.checkAnswerInResponse(deleteResponse, "message", is(message));
        }

    }


}