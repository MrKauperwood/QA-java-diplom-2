package getuserorders;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.RegisterUserRequest;
import responses.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static reststeps.Constants.BASE_URI;
import static reststeps.Constants.SHOULD_BE_AUTHORISED_MSG;
import static reststeps.SendRequest.*;
import static reststeps.UserSteps.*;
import static reststeps.Utils.*;

/**
 * Author: Alexey Bondarenko
 * Date: 05.06.2022
 */
public class GetUserOrdersTest {

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
    @DisplayName("Check successful getting user's orders")
    public void checkSuccessfulGettingUserOrder() {
        LoginResponse parsedLoginResponse = loginUnderUser(registeredUser);
        token = parsedLoginResponse.getAccessToken();
        CreateOrderResponse createOrderResponse = createNewOrder(accessibleIngredients, token);

        ArrayList<OrderInfo> expectedUserOrders =
                getInfoAboutCreatedOrdersByNumbers(
                        parsedLoginResponse.getAccessToken(),
                        List.of(createOrderResponse.getOrder().getNumber()));

        Response getUserOrdersResponse = sendRequestGetUserOrders(token);

        checkStatusCodeAndResponseForSuccessfulGetUserOrderRequest(
                getUserOrdersResponse,
                expectedUserOrders);
    }

    @Test
    @DisplayName("Check successful getting user's several orders")
    public void checkSuccessfulGettingUserSeveralOrders() {
        LoginResponse parsedLoginResponse = loginUnderUser(registeredUser);
        token = parsedLoginResponse.getAccessToken();

        CreateOrderResponse createOrderResponse = createNewOrder(accessibleIngredients, token);
        CreateOrderResponse createAnotherOrderResponse = createNewOrder(accessibleIngredients, token);

        ArrayList<OrderInfo> expectedUserOrders =
                getInfoAboutCreatedOrdersByNumbers(
                        parsedLoginResponse.getAccessToken(),
                        List.of(
                                createOrderResponse.getOrder().getNumber(),
                                createAnotherOrderResponse.getOrder().getNumber())
                );

        Response getUserOrdersResponse = sendRequestGetUserOrders(token);

        checkStatusCodeAndResponseForSuccessfulGetUserOrderRequest(
                getUserOrdersResponse,
                expectedUserOrders);
    }

    @Test
    @DisplayName("Check failed getting user's orders without login")
    public void checkFailedGettingUserOrderWithoutLogin() {
        CreateOrderResponse createOrderResponse = createNewOrder(accessibleIngredients, "");
        getInfoAboutCreatedOrdersByNumbers("", List.of(createOrderResponse.getOrder().getNumber()));

        Response getUserOrdersResponse = sendRequestGetUserOrders("");

        checkStatusCodeAndResponseForFailedGetUserOrderRequest(
                getUserOrdersResponse,
                SC_UNAUTHORIZED,
                SHOULD_BE_AUTHORISED_MSG);
    }

}
