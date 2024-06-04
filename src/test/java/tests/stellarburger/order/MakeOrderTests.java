package tests.stellarburger.order;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import stellarburgers.pojo.Ingredients;
import stellarburgers.pojo.User;
import stellarburgers.api.checkers.Checkers;
import stellarburgers.api.requests.OrderApi;
import tests.stellarburger.utils.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@Epic("Tests for making orders functionality")
class MakeOrderTests extends BaseTest {
    static List<String> ingredientsHashCodes;
    static String ACCESS_TOKEN_VALUE;

    @BeforeAll
    static void getIngredientsAndRegisterUser() {
        int ingredientsSize = OrderApi.getIngredientsSize();
        int ingredientsAmountToGet = new Random().nextInt(ingredientsSize);
        User user = FakerUtils.getFakeUser();
        ingredientsHashCodes = Precondition.Ingredients.getIngredientsHashCodes(ingredientsAmountToGet);

        Response responseRegister = Precondition.User.register(user);
        ACCESS_TOKEN_VALUE = UtilMethods.getBearerTokenFromRegisterResponse(responseRegister);
    }

    private static Stream<Arguments> accessTokens() {
        return Stream.of(
                arguments(ACCESS_TOKEN_VALUE),
                arguments("null")
        );
    }

    @ParameterizedTest
    @MethodSource("accessTokens")
    @DisplayName("POST /api/orders. Positive test. Should make order")
    @Description("Tests for making an order with authorized and unauthorized users. Expected status code 200")
    void shouldMakeOrder(String accessToken) {
        String tokenParameter = accessToken.equals("null") ? null : accessToken;
        Ingredients ingredients = new Ingredients(ingredientsHashCodes);

        Response makeOrderResponse = OrderApi.makeOrder(ingredients, tokenParameter);
        Checkers.check200Success(makeOrderResponse);

        OrderHelpers.checkJsonGoodStructureInMakeOrderResponse(makeOrderResponse);
    }

    @Test
    @DisplayName("POST /api/orders. Negative test. Should not make order (missed ingredients)")
    @Description("Tests for making an order without any ingredient. Expected status code 400")
    void shouldNotMakeOrder() {
        boolean success = false;
        String message = "Ingredient ids must be provided";

        Ingredients ingredients = new Ingredients();

        Response makeOrderResponse = OrderApi.makeOrder(ingredients, ACCESS_TOKEN_VALUE);
        Checkers.check400BadRequest(makeOrderResponse);

        UtilMethods.checkSuccessResponseStructure(makeOrderResponse, success, message);
    }

    @Test
    @DisplayName("POST /api/orders. Negative test. Should not make order (bad values)")
    @Description("Tests for making an order without any ingredient. Expected status code 500. " +
            "Ingredients passes with bad hash")
    void shouldNotMakeOrderBadHash() {
        List<String> ingredientsHashCodes = List.of(FakerUtils.getFakeHash(), FakerUtils.getFakeHash());
        Ingredients ingredients = new Ingredients(ingredientsHashCodes);

        Response makeOrderResponse = OrderApi.makeOrder(ingredients, null);
        Checkers.check500InternalServerError(makeOrderResponse);
    }

    @AfterAll
    static void deleteUser() {
        Postcondition.User.delete(ACCESS_TOKEN_VALUE);
    }

}
