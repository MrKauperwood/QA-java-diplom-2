package reststeps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Assert;
import requests.CreateOrderRequest;
import requests.LoginRequest;
import requests.RegisterUserRequest;
import responses.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.*;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static reststeps.SendRequest.*;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class UserSteps {

    @Step("Generate data for new user")
    public static HashMap<String, String> generateDataForNewUser() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String name = randomAlphabetic(7) + " " + randomAlphabetic(10);
        String password = randomAlphanumeric(10);
        String email = "Ab" + timestamp.getTime() + "@gmail.com";

        HashMap<String, String> newUserData = new HashMap<>();
        newUserData.put("email", email);
        newUserData.put("password", password);
        newUserData.put("name", name);

        return newUserData;
    }

    @Step("Register new user")
    public static RegisterUserRequest registerNewUser() {
        HashMap<String, String> newUserData = generateDataForNewUser();
        RegisterUserRequest registerUserRequest = new RegisterUserRequest(
                newUserData.get("email"),
                newUserData.get("password"),
                newUserData.get("name"));

        Response response = sendRequestRegisterNewUser(registerUserRequest);
        if (response.getStatusCode() != SC_OK) {
            FailedResponse failedResponse = response.as(FailedResponse.class);
            Assert.assertEquals("User wasn't created. Error msg:\n" + failedResponse.getMessage(), SC_OK, response.getStatusCode());
        }
        return registerUserRequest;
    }

    @Step("Login user")
    public static LoginResponse loginUnderUser(RegisterUserRequest registeredUser) {
        LoginRequest loginRequest = new LoginRequest(registeredUser.getEmail(), registeredUser.getPassword());
        Response loginResponse = sendRequestLoginUser(loginRequest);
        loginResponse.then().statusCode(SC_OK);
        return loginResponse.as(LoginResponse.class);
    }

    @Step("Get list of ingredients")
    public static List<IngredientInfo> getListOfIngredients() {
        Response response = sendRequestGetIngredients();
        response.then().statusCode(SC_OK);
        GetListOfIngredients parsedResponse = response.as(GetListOfIngredients.class);
        return parsedResponse.getData();
    }

    @Step("Get bun from all list of ingredients")
    public static String getRandomBunId(List<IngredientInfo> list) {
        String bunId = null;
        for (IngredientInfo ingredientInfo : list) {
            if (ingredientInfo.getType().equals("bun")) {
                bunId = ingredientInfo.get_id();
                break;
            }
        }
        Assert.assertNotNull("List of ingredients not contain buns", bunId);
        return bunId;
    }

    @Step("Get filling from all list of ingredients")
    public static String getRandomFillingId(List<IngredientInfo> list) {
        String fillingId = null;
        for (IngredientInfo ingredientInfo : list) {
            if (ingredientInfo.getType().equals("main")) {
                fillingId = ingredientInfo.get_id();
                break;
            }
        }
        Assert.assertNotNull("List of ingredients not contain fillings", fillingId);
        return fillingId;
    }

    public static Response createNewOrderRaw(List<IngredientInfo> accessibleIngredients, String token) {
        CreateOrderRequest createOrderRequest =
                new CreateOrderRequest(
                        List.of(
                                getRandomBunId(accessibleIngredients),
                                getRandomFillingId(accessibleIngredients)));
        return sendRequestCreateOrder(createOrderRequest, token);
    }

    @Step("Create new order")
    public static CreateOrderResponse createNewOrder(List<IngredientInfo> accessibleIngredients, String token) {
        Response createOrderResponse = createNewOrderRaw(accessibleIngredients, token);
        assert (createOrderResponse.getStatusCode() == SC_OK);
        return createOrderResponse.as(CreateOrderResponse.class);
    }

    @Step("Delete registered user")
    public static void deleteUser(String token) {
        Response createOrderResponse = sendRequestDeleteUser(token);
        assert (createOrderResponse.getStatusCode() == SC_ACCEPTED);
    }

    @Step("Get info about created orders by order numbers")
    public static ArrayList<OrderInfo> getInfoAboutCreatedOrdersByNumbers(String token, List<Integer> orderNumbers) {
        ArrayList<OrderInfo> allUsersOrders = new ArrayList<>();

        Response response = sendRequestGetInformationAboutAllOrders(token);
        Assert.assertEquals("Get all orders request failed", SC_OK, response.getStatusCode());

        GetAllOrdersResponse parsedResponse = response.as(GetAllOrdersResponse.class);

        for (OrderInfo orderInfo : parsedResponse.getOrders()) {
            if (orderNumbers.contains(orderInfo.getNumber())) {
                allUsersOrders.add(orderInfo);
            }
            if (allUsersOrders.size() == orderNumbers.size()) {
                break;
            }
        }
        return allUsersOrders;
    }
}
