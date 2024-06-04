package stellarburgers.api.requests;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import stellarburgers.api.checkers.Checkers;
import stellarburgers.api.responses.ResponseStringMaker;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class UtilMethods {
    private UtilMethods() {

    }

    protected static RequestSpecification makeHeadersForResponse(String accessToken) {
        RequestSpecification requestWithHeaders;
        if (accessToken != null) {
            requestWithHeaders = given()
                    .header("Authorization", accessToken)
                    .and()
                    .header("Content-type", "application/json");
        }
        else {
            requestWithHeaders = given().header("Content-type", "application/json");
        }
        return requestWithHeaders.filter(new AllureRestAssured());
    }

    protected static ResponseStringMaker getPrettyResponseMaker(String requestDescription) {
        return x -> String.format(
                        "%s. \n" +
                        "Status code: %d." +
                        "\nBody:\n%s",
                requestDescription,
                x.statusCode(),
                x.body().asPrettyString()
        );
    }
}
