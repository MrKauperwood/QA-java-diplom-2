package reststeps;

import io.restassured.response.Response;
import requests.RegisterUserRequest;

import java.util.HashMap;

import static org.apache.commons.lang3.RandomStringUtils.*;
import static org.apache.http.HttpStatus.SC_OK;
import static reststeps.SendRequest.sendRequestRegisterNewUser;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class UserSteps {

    public static HashMap<String, String> generateDataForNewUser() {
        String name = randomAlphabetic(7) + " " + randomAlphabetic(10);
        String password = randomAlphanumeric(10);
        String email = randomAlphabetic(12) + "@gmail.com";

        HashMap<String, String> newUserData = new HashMap<>();
        newUserData.put("email", email);
        newUserData.put("password", password);
        newUserData.put("name", name);

        return newUserData;
    }

    public static RegisterUserRequest registerNewUser() {
        HashMap<String, String> newUserData = generateDataForNewUser();
        RegisterUserRequest registerUserRequest = new RegisterUserRequest(
                newUserData.get("email"),
                newUserData.get("password"),
                newUserData.get("name"));

        Response response = sendRequestRegisterNewUser(registerUserRequest);
        assert(response.getStatusCode() == SC_OK);
        return registerUserRequest;
    }
}
