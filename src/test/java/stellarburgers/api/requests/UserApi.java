package stellarburgers.api.requests;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import stellarburgers.api.responses.ResponseStringMaker;
import stellarburgers.pojo.User;
import stellarburgers.api.responses.ResponseWithToString;

import static io.restassured.RestAssured.given;

public class UserApi {

    private UserApi () {

    }
    private static final String CREATE_USER_ENDPOINT = "/api/auth/register";
    private static final String DELETE_USER_ENDPOINT = "/api/auth/user";
    private static final String LOGIN_USER_ENDPOINT = "api/auth/login";
    private static final String CHANGE_USER_INFO_ENDPOINT = "/api/auth/user";

    private static ResponseStringMaker getRegisterResponseMaker() {
        String requestDescription = "Register user POST " + CREATE_USER_ENDPOINT;
        return UtilMethods.getPrettyResponseMaker(requestDescription);
    }

    private static ResponseStringMaker getDeleteResponseMaker() {
        String requestDescription = "Delete user DELETE " + DELETE_USER_ENDPOINT;
        return UtilMethods.getPrettyResponseMaker(requestDescription);
    }

    private static ResponseStringMaker getChangeInfoResponseMaker() {
        String requestDescription = "Change user info. PATCH " + CHANGE_USER_INFO_ENDPOINT;
        return UtilMethods.getPrettyResponseMaker(requestDescription);
    }

    private static ResponseStringMaker getLoginResponseMaker() {
        String requestDescription = "Login user POST " + LOGIN_USER_ENDPOINT;
        return UtilMethods.getPrettyResponseMaker(requestDescription);
    }

    @Step("POST /api/auth/register. Creating a user.")
    public static Response createUser(stellarburgers.pojo.User user) {
        Response response = given()
                                .filter(new AllureRestAssured())
                                .header("Content-type", "application/json")
                                .and()
                                .body(user)
                                .when()
                                .post(CREATE_USER_ENDPOINT);

        return new ResponseWithToString(response, getRegisterResponseMaker());

    }

    @Step("DELETE /api/auth/user. Deleting a user.")
    public static Response deleteUser(String accessToken) {
        Response response = given()
                                .filter(new AllureRestAssured())
                                .header("Authorization", accessToken)
                                .when()
                                .delete(DELETE_USER_ENDPOINT);

        return new ResponseWithToString(response, getDeleteResponseMaker());
    }

    @Step("POST /api/auth/login. Login a user.")
    public static Response loginUser(User user) {
        Response response = given()
                                .filter(new AllureRestAssured())
                                .header("Content-type", "application/json")
                                .and()
                                .body(user)
                                .when()
                                .post(LOGIN_USER_ENDPOINT);

        return new ResponseWithToString(response, getLoginResponseMaker());
    }

    @Step("PATCH /api/auth/user. Change user info.")
    public static Response changeUserInfo(User user, String accessToken) {
        RequestSpecification requestWithHeaders = UtilMethods.makeHeadersForResponse(accessToken);

        Response response = requestWithHeaders
                                        .body(user)
                                        .when()
                                        .patch(CHANGE_USER_INFO_ENDPOINT);

        return new ResponseWithToString(response, getChangeInfoResponseMaker());
    }

}
