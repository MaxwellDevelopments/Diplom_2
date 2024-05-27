package stellarburgers.api.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import stellarburgers.api.checkers.Checkers;
import stellarburgers.pojo.GetIngredientsResponse;
import stellarburgers.pojo.Ingredients;
import stellarburgers.api.responses.ResponseStringMaker;
import stellarburgers.api.responses.ResponseWithToString;
import tests.stellarburger.utils.BaseTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class OrderApi extends BaseTest {

    private static final String INGREDIENTS_ENDPOINT = "/api/ingredients";
    private static final String MAKE_ORDER_ENDPOINT = "/api/orders";
    private static final String GET_ORDERS_ENDPOINT = "/api/orders";

    private static ResponseStringMaker getIngredientsInfoResponseMaker() {
        String requestDescription = "Get ingredients info GET " + INGREDIENTS_ENDPOINT;
        return UtilMethods.getPrettyResponseMaker(requestDescription);
    }

    private static ResponseStringMaker getMakeOrderResponseMaker() {
        String requestDescription = "Make order POST " + MAKE_ORDER_ENDPOINT;
        return UtilMethods.getPrettyResponseMaker(requestDescription);
    }

    private static ResponseStringMaker getUserOrdersResponseMaker() {
        String requestDescription = "Get user orders GET " + GET_ORDERS_ENDPOINT;
        return UtilMethods.getPrettyResponseMaker(requestDescription);
    }

    public static Response getIngredientsData() {
        Response response = given().get(INGREDIENTS_ENDPOINT);
        return new ResponseWithToString(response, getIngredientsInfoResponseMaker());
    }

    @Step("Making an order")
    public static Response makeOrder(Ingredients ingredients, String accessToken) {
        RequestSpecification requestWithHeaders = UtilMethods.makeHeadersForResponse(accessToken);

        Response response = requestWithHeaders
                                .body(ingredients)
                                .when()
                                .post(MAKE_ORDER_ENDPOINT);

        return new ResponseWithToString(response, getMakeOrderResponseMaker());
    }

    @Step("Getting user's orders")
    public static Response getUserOrders(String accessToken) {
        RequestSpecification requestWithHeaders = UtilMethods.makeHeadersForResponse(accessToken);
        Response response = requestWithHeaders.get(GET_ORDERS_ENDPOINT);
        return new ResponseWithToString(response, getMakeOrderResponseMaker());
    }

    @Step("Get total amount of available ingredients")
    public static int getIngredientsSize() {
        Response responseGetIngredients = OrderApi.getIngredientsData();
        Checkers.check200Success(responseGetIngredients);

        GetIngredientsResponse ingredients = responseGetIngredients.body().as(GetIngredientsResponse.class);
        return ingredients.getData().size();
    }

    @Step("Get specific amount of ingredients hash-codes")
    public static List<String> getIngredientsHashCodes(int amount) {
        ArrayList<String> ingredientsHashCodes = new ArrayList<>();

        Response responseGetIngredients = OrderApi.getIngredientsData();
        Checkers.check200Success(responseGetIngredients);

        GetIngredientsResponse ingredients = responseGetIngredients.body().as(GetIngredientsResponse.class);
        ingredients.getData().forEach(x -> ingredientsHashCodes.add(x.getId()));

        int ingredientsTotalAmount = ingredientsHashCodes.size();
        int randomStartIndex = new Random().nextInt(ingredientsTotalAmount - amount);
        return ingredientsHashCodes.subList(randomStartIndex, randomStartIndex+amount);
    }

}
