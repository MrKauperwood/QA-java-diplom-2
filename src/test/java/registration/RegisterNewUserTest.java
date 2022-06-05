package registration;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import requests.RegisterUserRequest;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static reststeps.Constants.BASE_URI;
import static reststeps.Constants.USER_ALREADY_EXISTS_MSG;
import static reststeps.SendRequest.sendRequestRegisterNewUser;
import static reststeps.UserSteps.*;
import static reststeps.Utils.checkStatusCodeAndResponseForFailedRegisterRequest;
import static reststeps.Utils.checkStatusCodeAndResponseForSuccessfulRegisterRequest;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class RegisterNewUserTest {

    @Before
    public void setUp() {
        baseURI = BASE_URI;
    }

    @Test
    @DisplayName("Check SC 200 after successful registration new user")
    public void checkSC200AfterSuccessfulRegistrationNewUser() {
        HashMap<String, String> newUserData = generateDataForNewUser();

        RegisterUserRequest registerUserRequest = new RegisterUserRequest(
                newUserData.get("email"),
                newUserData.get("password"),
                newUserData.get("name"));

        Response response = sendRequestRegisterNewUser(registerUserRequest);
        checkStatusCodeAndResponseForSuccessfulRegisterRequest(
                response,
                registerUserRequest.getEmail(),
                registerUserRequest.getName());
    }

    @Test
    @DisplayName("Check SC 403 after attempt registration an existing user")
    public void checkSC403AfterAttemptRegistrationAnExistingUser() {
        RegisterUserRequest registeredUser = registerNewUser();

        Response response = sendRequestRegisterNewUser(registeredUser);
        checkStatusCodeAndResponseForFailedRegisterRequest(response, SC_FORBIDDEN, USER_ALREADY_EXISTS_MSG);
    }
}
