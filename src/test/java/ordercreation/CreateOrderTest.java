package ordercreation;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.CreateOrderRequest;
import requests.RegisterUserRequest;
import responses.IngredientInfo;
import responses.LoginResponse;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static org.apache.http.HttpStatus.*;
import static reststeps.Constants.*;
import static reststeps.SendRequest.*;
import static reststeps.UserSteps.*;
import static reststeps.Utils.*;

/**
 * Author: Alexey Bondarenko
 * Date: 05.06.2022
 */
public class CreateOrderTest {

    private RegisterUserRequest registeredUser;
    private List<IngredientInfo> accessibleIngredients;
    private String token;

    @Before
    public void setUp() {
        baseURI = BASE_URI;
        registeredUser = registerNewUser();
        accessibleIngredients = getListOfIngredients();
        token = null;
    }

    @After
    public void setDown() {
        if (token != null) {
            deleteUser(token);
        }
    }

    @Test
    @DisplayName("Check successful order creation")
    public void checkSuccessfulOrderCreation() {
        LoginResponse parsedLoginResponse = loginUnderUser(registeredUser);
        token = parsedLoginResponse.getAccessToken();

        CreateOrderRequest createOrderRequest = new CreateOrderRequest(
                List.of(
                        getRandomBunId(accessibleIngredients),
                        getRandomFillingId(accessibleIngredients)));

        Response createOrderResponse = sendRequestCreateOrder(createOrderRequest, parsedLoginResponse.getAccessToken());

        checkStatusCodeAndResponseForSuccessfulOrderCreationRequest(
                createOrderResponse,
                BURGER_NAME);
    }

    @Test
    @DisplayName("Check failed order creation without authorization")
    public void checkFailedOrderCreationWithoutAuthorization() {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest(
                List.of(
                        getRandomBunId(accessibleIngredients),
                        getRandomFillingId(accessibleIngredients)));

        Response createOrderResponse = sendRequestCreateOrder(createOrderRequest, "");

        checkStatusCodeAndResponseForFailedOrderCreationRequest(
                createOrderResponse,
                SC_FORBIDDEN,
                SHOULD_BE_AUTHORISED_MSG);
    }

    @Test
    @DisplayName("Check failed order creation without ingredients")
    public void checkFailedOrderCreationWithoutIngredients() {
        LoginResponse parsedLoginResponse = loginUnderUser(registeredUser);
        token = parsedLoginResponse.getAccessToken();

        CreateOrderRequest createOrderRequest = new CreateOrderRequest(List.of());

        Response createOrderResponse = sendRequestCreateOrder(createOrderRequest, parsedLoginResponse.getAccessToken());

        checkStatusCodeAndResponseForFailedOrderCreationRequest(
                createOrderResponse,
                SC_BAD_REQUEST,
                EMPTY_LIST_OF_INGREDIENTS_MSG);
    }

    @Test
    @DisplayName("Check failed order creation with non-existent ingredient id")
    public void checkFailedOrderCreationWithNonExistentIngredientId() {
        LoginResponse parsedLoginResponse = loginUnderUser(registeredUser);
        token = parsedLoginResponse.getAccessToken();

        CreateOrderRequest createOrderRequest = new CreateOrderRequest(
                List.of(NON_EXISTING_INGREDIENT));

        Response createOrderResponse = sendRequestCreateOrder(createOrderRequest, parsedLoginResponse.getAccessToken());

        checkStatusCodeAndResponseForFailedOrderCreationRequest(
                createOrderResponse,
                SC_BAD_REQUEST,
                WRONG_INGREDIENTS_MSG);
    }

    @Test
    @DisplayName("Check failed order creation with invalid ingredient id")
    public void checkFailedOrderCreationWithInvalidIngredientId() {
        LoginResponse parsedLoginResponse = loginUnderUser(registeredUser);
        token = parsedLoginResponse.getAccessToken();

        CreateOrderRequest createOrderRequest = new CreateOrderRequest(
                List.of("vernika500ky"));

        Response createOrderResponse = sendRequestCreateOrder(createOrderRequest, parsedLoginResponse.getAccessToken());

        checkStatusCodeAndResponseForFailedOrderCreationRequest(
                createOrderResponse,
                SC_INTERNAL_SERVER_ERROR,
                WRONG_INGREDIENTS_MSG);
    }

}
