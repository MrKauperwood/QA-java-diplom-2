package reststeps;

import io.restassured.response.Response;
import org.junit.Assert;
import responses.*;

import java.util.ArrayList;
import java.util.Locale;

import static org.apache.http.HttpStatus.*;
import static reststeps.Constants.INTERNAL_SERVER_ERROR_MSG;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class Utils {

    private static final String SHOULD_BE_200_MSG = "SC should be 200";

    public static void checkStatusCodeAndResponseForFailedRegisterRequest(
            Response response, int expectedStatus, String expectedMessage) {

        Assert.assertTrue(INTERNAL_SERVER_ERROR_MSG, response.getStatusCode() < SC_INTERNAL_SERVER_ERROR);
        Assert.assertEquals(expectedStatus, response.getStatusCode());

        FailedResponse parsedResponse = response.as(FailedResponse.class);
        Assert.assertEquals(false, parsedResponse.getSuccess());
        Assert.assertEquals(expectedMessage, parsedResponse.getMessage());
    }

    public static void checkStatusCodeAndResponseForSuccessfulRegisterRequest(
            Response response, String email, String name) {

        Assert.assertEquals(SHOULD_BE_200_MSG, SC_OK, response.getStatusCode());

        AuthorizationAndRegistrationResponse parsedResponse = response.as(AuthorizationAndRegistrationResponse.class);
        Assert.assertEquals(true, parsedResponse.getSuccess());
        Assert.assertTrue(parsedResponse.getAccessToken().startsWith("Bearer "));
        Assert.assertFalse("Refresh Token shouldn't be empty", parsedResponse.getRefreshToken().isEmpty());
        Assert.assertEquals(name, parsedResponse.getUser().getName());
        Assert.assertEquals(email.toLowerCase(Locale.ROOT), parsedResponse.getUser().getEmail());
    }

    public static void checkStatusCodeAndResponseForSuccessfulLoginRequest(
            Response response, String email, String name) {

        Assert.assertEquals(SHOULD_BE_200_MSG, SC_OK, response.getStatusCode());

        LoginResponse parsedResponse = response.as(LoginResponse.class);
        Assert.assertEquals(true, parsedResponse.getSuccess());
        Assert.assertTrue(parsedResponse.getAccessToken().startsWith("Bearer "));
        Assert.assertFalse("Refresh Token shouldn't be empty", parsedResponse.getRefreshToken().isEmpty());
        Assert.assertEquals(name, parsedResponse.getUser().getName());
        Assert.assertEquals(email.toLowerCase(Locale.ROOT), parsedResponse.getUser().getEmail());
    }

    public static void checkStatusCodeAndResponseForSuccessfulUpdateUserInfoRequest(
            Response response, String email, String name) {

        Assert.assertEquals(SHOULD_BE_200_MSG, SC_OK, response.getStatusCode());

        UpdateUserInfoResponse parsedResponse = response.as(UpdateUserInfoResponse.class);
        Assert.assertEquals(true, parsedResponse.getSuccess());
        Assert.assertEquals(name, parsedResponse.getUser().getName());
        Assert.assertEquals(email.toLowerCase(Locale.ROOT), parsedResponse.getUser().getEmail());
    }

    public static void checkStatusCodeAndResponseForFailedUpdateUserInfoRequest(
            Response response, int statusCode, String expectedMessage) {

        Assert.assertEquals(String.format("SC should be %s", statusCode), statusCode, response.getStatusCode());

        FailedResponse parsedResponse = response.as(FailedResponse.class);
        Assert.assertEquals(false, parsedResponse.getSuccess());
        Assert.assertEquals(expectedMessage, parsedResponse.getMessage());
    }

    public static void checkStatusCodeAndResponseForSuccessfulOrderCreationRequest(
            Response response, String name) {

        Assert.assertEquals(SHOULD_BE_200_MSG, SC_OK, response.getStatusCode());

        CreateOrderResponse parsedResponse = response.as(CreateOrderResponse.class);
        Assert.assertEquals(true, parsedResponse.getSuccess());
        Assert.assertEquals(name, parsedResponse.getName());
        Assert.assertNotNull(parsedResponse.getOrder().getNumber());
    }

    public static void checkStatusCodeAndResponseForFailedOrderCreationRequest(
            Response response, int statusCode, String expectedMessage) {

        Assert.assertEquals(String.format("SC should be %s", statusCode), statusCode, response.getStatusCode());

        FailedResponse parsedResponse = response.as(FailedResponse.class);
        Assert.assertEquals(false, parsedResponse.getSuccess());
        Assert.assertEquals(expectedMessage, parsedResponse.getMessage());
    }

    public static void checkStatusCodeAndResponseForSuccessfulGetUserOrderRequest(
            Response response, ArrayList<OrderInfo> expectedUserOrders) {

        Assert.assertEquals(SHOULD_BE_200_MSG, SC_OK, response.getStatusCode());

        GetUserOrdersResponse parsedResponse = response.as(GetUserOrdersResponse.class);
        Assert.assertEquals(true, parsedResponse.getSuccess());
        Assert.assertNotNull("Count of total orders shouldn't be null", parsedResponse.getTotal());
        Assert.assertNotNull("Count of total today orders shouldn't be null", parsedResponse.getTotalToday());
        Assert.assertEquals(expectedUserOrders.getClass(), parsedResponse.getOrders().getClass());
    }

    public static void checkStatusCodeAndResponseForFailedGetUserOrderRequest(
            Response response, int statusCode, String expectedMessage) {

        Assert.assertEquals(String.format("SC should be %s", statusCode), statusCode, response.getStatusCode());

        FailedResponse parsedResponse = response.as(FailedResponse.class);
        Assert.assertEquals(false, parsedResponse.getSuccess());
        Assert.assertEquals(expectedMessage, parsedResponse.getMessage());
    }
}
