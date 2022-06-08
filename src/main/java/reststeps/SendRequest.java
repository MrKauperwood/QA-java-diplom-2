package reststeps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import requests.CreateOrderRequest;
import requests.LoginRequest;
import requests.RegisterUserRequest;
import requests.GetUpdateRemoveUserInfoRequest;

import static io.restassured.RestAssured.given;
import static reststeps.Constants.BASE_URI;
import static reststeps.Handlers.*;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class SendRequest {

    @Step("Send POST request: register new user")
    public static Response sendRequestRegisterNewUser(RegisterUserRequest user) {
        return
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(user)
                        .when()
                        .post(BASE_URI + HANDLER_POST_REGISTER_NEW_USER);
    }

    @Step("Send POST request: login user")
    public static Response sendRequestLoginUser(LoginRequest userData) {
        return
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(userData)
                        .when()
                        .post(BASE_URI + HANDLER_POST_LOGIN);
    }

    @Step("Send PATCH request: update user info")
    public static Response sendRequestUpdateUserInfo(GetUpdateRemoveUserInfoRequest userData, String token) {
        return
                given()
                        .header("Content-type", "application/json")
                        .header("authorization", token)
                        .and()
                        .body(userData)
                        .when()
                        .patch(BASE_URI + HANDLER_PATCH_USER_INFO);
    }

    @Step("Send DELETE request: delete user")
    public static Response sendRequestDeleteUser(String token) {
        return
                given()
                        .header("Content-type", "application/json")
                        .header("authorization", token)
                        .when()
                        .delete(BASE_URI + HANDLER_DELETE_USER);
    }

    @Step("Send POST request: create new order")
    public static Response sendRequestCreateOrder(CreateOrderRequest orderData, String token) {
        return
                given()
                        .header("Content-type", "application/json")
                        .header("authorization", token)
                        .and()
                        .body(orderData)
                        .when()
                        .post(BASE_URI + HANDLER_POST_CREATE_ORDER);
    }

    @Step("Send GET request: get ingredients")
    public static Response sendRequestGetIngredients() {
        return
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .get(BASE_URI + HANDLER_GET_INGREDIENTS_INFO);
    }

    @Step("Send GET request: get user's orders")
    public static Response sendRequestGetUserOrders(String token) {
        return
                given()
                        .header("Content-type", "application/json")
                        .header("authorization", token)
                        .when()
                        .get(BASE_URI + HANDLER_GET_USER_ORDERS);
    }

    @Step("Send GET request: get info about all orders")
    public static Response sendRequestGetInformationAboutAllOrders(String token) {
        return
                given()
                        .header("Content-type", "application/json")
                        .header("authorization", token)
                        .when()
                        .get(BASE_URI + HANDLER_GET_ALL_ORDERS);
    }

}
