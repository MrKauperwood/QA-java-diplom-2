package reststeps;

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

    public static Response sendRequestRegisterNewUser(RegisterUserRequest user) {
        return
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(user)
                        .when()
                        .post(BASE_URI + HANDLER_POST_REGISTER_NEW_USER);
    }

    public static Response sendRequestLoginUser(LoginRequest userData) {
        return
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(userData)
                        .when()
                        .post(BASE_URI + HANDLER_POST_LOGIN);
    }

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

    public static Response sendRequestDeleteUser(String token) {
        return
                given()
                        .header("Content-type", "application/json")
                        .header("authorization", token)
                        .when()
                        .delete(BASE_URI + HANDLER_DELETE_USER);
    }

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

    public static Response sendRequestGetIngredients() {
        return
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .get(BASE_URI + HANDLER_GET_INGREDIENTS_INFO);
    }

    public static Response sendRequestGetUserOrders(String token) {
        return
                given()
                        .header("Content-type", "application/json")
                        .header("authorization", token)
                        .when()
                        .get(BASE_URI + HANDLER_GET_USER_ORDERS);
    }

    public static Response sendRequestGetInformationAboutAllOrders(String token) {
        return
                given()
                        .header("Content-type", "application/json")
                        .header("authorization", token)
                        .when()
                        .get(BASE_URI + HANDLER_GET_ALL_ORDERS);
    }

}
