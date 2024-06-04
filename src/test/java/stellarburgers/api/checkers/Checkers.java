package stellarburgers.api.checkers;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class Checkers {

    @Step("Check json parameter in response")
    public static <T> void checkAnswerInResponse(Response response, String jsonPath, Matcher<? super T> matcher) {
        response.then().assertThat().body(jsonPath, matcher);
    }

    @Step("Check response has status 200")
    public static void check200Success(Response response) {
        checkTStatus(response, 200);
    }

    @Step("Check response has status 202")
    public static void check202Accepted(Response response) {
        checkTStatus(response, 202);
    }

    @Step("Check response has status 400")
    public static void check400BadRequest(Response response) {
        checkTStatus(response, 400);
    }

    public static void check401Unauthorized(Response response) {
        checkTStatus(response, 401);
    }

    @Step("Check response has status 403")
    public static void check403Forbidden(Response response) {
        checkTStatus(response, 403);
    }

    @Step("Check response has status 404")
    public static void check404NotFound(Response response) {
        checkTStatus(response, 404);
    }

    @Step("Check response has status 409")
    public static void check409Conflict(Response response) {
        checkTStatus(response, 409);
    }

    @Step("Check response has status 500")
    public static void check500InternalServerError(Response response) {
        checkTStatus(response, 500);
    }

    private static void checkTStatus(Response response, int statusCode) {
        response.then().assertThat().statusCode(statusCode);
    }


}
