package tests.stellarburger.utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import stellarburgers.api.checkers.Checkers;
import stellarburgers.api.requests.OrderApi;
import stellarburgers.api.requests.UserApi;
import stellarburgers.pojo.GetIngredientsResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;

public class Precondition {


    private Precondition(){

    }

    public static class Ingredients {
        private Ingredients() {

        }

        @Step("Precondition: get ingredients hashcode")
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


    public static class User {
        private User() {

        }

        @Step("Precondition: create user")
        public static Response register(stellarburgers.pojo.User user) {
            Boolean success = true;

            Response responseRegister = UserApi.createUser(user);
            Checkers.check200Success(responseRegister);
            Checkers.checkAnswerInResponse(responseRegister, "success", is(success));
            return responseRegister;
        }

    }
}
