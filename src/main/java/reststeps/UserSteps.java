package reststeps;

import io.restassured.response.Response;
import requests.LoginRequest;
import requests.RegisterUserRequest;
import responses.GetListOfIngredients;
import responses.IngredientInfo;
import responses.LoginResponse;

import java.util.HashMap;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.*;
import static org.apache.http.HttpStatus.SC_OK;
import static reststeps.SendRequest.*;

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

    public static LoginResponse loginUnderUser(RegisterUserRequest registeredUser) {
        LoginRequest loginRequest = new LoginRequest(registeredUser.getEmail(), registeredUser.getPassword());
        Response loginResponse = sendRequestLoginUser(loginRequest);
        return loginResponse.as(LoginResponse.class);
    }

    public static List<IngredientInfo> getListOsIngredients() {
        Response response = sendRequestGetIngredients();
        response.then().statusCode(SC_OK);

        GetListOfIngredients parserResponse = response.as(GetListOfIngredients.class);
        return parserResponse.getData();
    }
}
