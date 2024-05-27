package tests.stellarburger.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import stellarburgers.api.checkers.Checkers;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class OrderHelpers {

    private OrderHelpers() {

    }

    @Step("Checking json structure for making order response")
    public static void checkJsonGoodStructureInMakeOrderResponse(Response response) {
        boolean success = true;

        Checkers.checkAnswerInResponse(response, "success", is(success));
        Checkers.checkAnswerInResponse(response, "name", any(String.class));
        Checkers.checkAnswerInResponse(response, "order", notNullValue());
        Checkers.checkAnswerInResponse(response, "order.number", notNullValue());
        Checkers.checkAnswerInResponse(response, "order.number", any(Integer.class));
    }

}
