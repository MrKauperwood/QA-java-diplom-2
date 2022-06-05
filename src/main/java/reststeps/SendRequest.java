package reststeps;

import io.restassured.response.Response;
import requests.LoginRequest;
import requests.RegisterUserRequest;
import requests.UpdateUserInfoRequest;

import static io.restassured.RestAssured.given;
import static reststeps.Constants.BASE_URL;
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
                        .post(BASE_URL + HANDLER_POST_REGISTER_NEW_USER);
    }

    public static Response sendRequestLoginUser(LoginRequest userData) {
        return
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(userData)
                        .when()
                        .post(BASE_URL + HANDLER_POST_LOGIN);
    }

    public static Response sendRequestUpdateUserInfo(UpdateUserInfoRequest userData, String token) {
        return
                given()
                        .header("Content-type", "application/json")
                        .header("authorization", token)
                        .and()
                        .body(userData)
                        .when()
                        .patch(BASE_URL + HANDLER_PATCH_USER_INFO);
    }

}
