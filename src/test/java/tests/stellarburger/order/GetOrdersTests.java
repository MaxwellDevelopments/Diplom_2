package tests.stellarburger.order;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stellarburgers.api.checkers.Checkers;
import stellarburgers.api.requests.OrderApi;
import stellarburgers.pojo.Ingredients;
import stellarburgers.pojo.User;
import tests.stellarburger.utils.*;

import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

@Epic("Tests for getting specific user orders functionality")
class GetOrdersTests extends BaseTest {

    static String accessTokenValue;
    static User user = FakerUtils.getFakeUser();

    @BeforeAll
    static void setUser() {
        user = FakerUtils.getFakeUser();

        Response responseRegister = Precondition.User.register(user);
        accessTokenValue = UtilMethods.getBearerTokenFromRegisterResponse(responseRegister);
    }

    @Test
    @DisplayName("POST /api/orders should not take orders. Negative test trying take user orders.")
    @Description("Negative test for taking user orders. Expected status code 401. Also checking json structure")
    void takingSpecificUserOrdersWithoutToken() {
        boolean success = false;
        String message = "You should be authorised";

        Response responseUserOrders = OrderApi.getUserOrders(null);

        Checkers.check401Unauthorized(responseUserOrders);
        UtilMethods.checkSuccessResponseStructure(responseUserOrders, success, message);
    }

    @Test
    @DisplayName("POST /api/orders should take orders. Positive test trying take user orders.")
    @Description("Positive test for taking user orders. Expected status code 200." +
            " Also checking amount of orders and json structure")
    void takingSpecificUserOrdersWithToken() {
        int total = new Random().nextInt(5);
        boolean success = true;

        setUpForTakingOrdersWithToken(total);

        Response responseGetOrders = OrderApi.getUserOrders(accessTokenValue);

        Checkers.check200Success(responseGetOrders);

        Checkers.checkAnswerInResponse(responseGetOrders, "success", is(success));
        Checkers.checkAnswerInResponse(responseGetOrders, "orders", hasSize(total));
        Checkers.checkAnswerInResponse(responseGetOrders, "total", is(total));
        Checkers.checkAnswerInResponse(responseGetOrders, "totalToday", is(total));

    }

    @Step("Set up for making orders")
    private void setUpForTakingOrdersWithToken(int ordersAmount) {
        for (int i = 0; i < ordersAmount; i++) {
            int ingredientsSize = OrderApi.getIngredientsSize();
            int ingredientsAmountToGet = new Random().nextInt(ingredientsSize);
            List<String> ingredientsHashCodes = OrderApi.getIngredientsHashCodes(ingredientsAmountToGet);
            Ingredients ingredients = new Ingredients(ingredientsHashCodes);
            makeOrder(ingredients);
        }
    }

    @Step("Making order for user")
    private void makeOrder(Ingredients ingredients) {
        Response responseMakeOrder = OrderApi.makeOrder(ingredients, accessTokenValue);
        Checkers.check200Success(responseMakeOrder);
    }

    @AfterAll
    static void deleteUser() {
        Postcondition.User.delete(accessTokenValue);
    }
}
