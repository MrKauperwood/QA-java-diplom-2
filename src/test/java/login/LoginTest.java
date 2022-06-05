package login;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.LoginRequest;
import requests.RegisterUserRequest;
import responses.LoginResponse;

import static io.restassured.RestAssured.baseURI;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static reststeps.Constants.BASE_URI;
import static reststeps.Constants.INCORRECT_EMAIL_OR_PASSWORD_MSG;
import static reststeps.SendRequest.sendRequestLoginUser;
import static reststeps.UserSteps.deleteUser;
import static reststeps.UserSteps.registerNewUser;
import static reststeps.Utils.checkStatusCodeAndResponseForFailedRegisterRequest;
import static reststeps.Utils.checkStatusCodeAndResponseForSuccessfulLoginRequest;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class LoginTest {

    private String token;

    @Before
    public void setUp() {
        baseURI = BASE_URI;
        token = null;
    }

    @After
    public void setDown() {
        if (token != null) {
            deleteUser(token);
        }
    }

    @Test
    @DisplayName("Check SC 200 and response after successful login")
    public void checkSC200AndResponseAfterSuccessfulLoginUser() {
        RegisterUserRequest registeredUser = registerNewUser();
        LoginRequest loginRequest = new LoginRequest(registeredUser.getEmail(), registeredUser.getPassword());
        Response response = sendRequestLoginUser(loginRequest);

        checkStatusCodeAndResponseForSuccessfulLoginRequest(
                response,
                registeredUser.getEmail(),
                registeredUser.getName());
        token = response.as(LoginResponse.class).getAccessToken();
    }

    @Test
    @DisplayName("Check SC 401 and response after login with incorrect password")
    public void checkSC401AndResponseAfterLoginWithIncorrectPassword() {
        RegisterUserRequest registeredUser = registerNewUser();
        LoginRequest loginRequest = new LoginRequest(registeredUser.getEmail(), "324235123142");
        Response response = sendRequestLoginUser(loginRequest);

        checkStatusCodeAndResponseForFailedRegisterRequest(
                response,
                SC_UNAUTHORIZED,
                INCORRECT_EMAIL_OR_PASSWORD_MSG);
    }

    @Test
    @DisplayName("Check SC 401 and response after login with incorrect email")
    public void checkSC401AndResponseAfterLoginWithIncorrectEmail() {
        RegisterUserRequest registeredUser = registerNewUser();
        LoginRequest loginRequest = new LoginRequest(randomAlphabetic(7) + "@gmail.com", registeredUser.getPassword());
        Response response = sendRequestLoginUser(loginRequest);

        checkStatusCodeAndResponseForFailedRegisterRequest(
                response,
                SC_UNAUTHORIZED,
                INCORRECT_EMAIL_OR_PASSWORD_MSG);
    }
}
