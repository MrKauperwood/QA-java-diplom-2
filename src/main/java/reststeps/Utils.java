package reststeps;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import responses.*;

import java.util.Locale;

import static org.apache.http.HttpStatus.*;
import static reststeps.Constants.INTERNAL_SERVER_ERROR_MSG;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class Utils {

    public static void checkStatusCodeAndResponseForFailedRegisterRequest(
            Response response, int expectedStatus, String expectedMessage) {

        Assert.assertTrue(INTERNAL_SERVER_ERROR_MSG, response.getStatusCode() < SC_INTERNAL_SERVER_ERROR);
        Assert.assertEquals(expectedStatus, response.getStatusCode());

        AuthorizationAndRegistrationUserFailedResponse parsedResponse = response.as(AuthorizationAndRegistrationUserFailedResponse.class);
        Assert.assertEquals(false, parsedResponse.getSuccess());
        Assert.assertEquals(expectedMessage, parsedResponse.getMessage());
    }

    public static void checkStatusCodeAndResponseForSuccessfulRegisterRequest(
            Response response, String email, String name) {

        Assert.assertEquals("SC should be 200", SC_OK, response.getStatusCode());

        AuthorizationAndRegistrationUserSuccessResponse parsedResponse = response.as(AuthorizationAndRegistrationUserSuccessResponse.class);
        Assert.assertEquals(true, parsedResponse.getSuccess());
        Assert.assertTrue(parsedResponse.getAccessToken().startsWith("Bearer "));
        Assert.assertFalse("Refresh Token shouldn't be empty", parsedResponse.getRefreshToken().isEmpty());
        Assert.assertEquals(name, parsedResponse.getUser().getName());
        Assert.assertEquals(email.toLowerCase(Locale.ROOT), parsedResponse.getUser().getEmail());
    }

    public static void checkStatusCodeAndResponseForSuccessfulLoginRequest(
            Response response, String email, String name) {

        Assert.assertEquals("SC should be 200", SC_OK, response.getStatusCode());

        LoginResponse parsedResponse = response.as(LoginResponse.class);
        Assert.assertEquals(true, parsedResponse.getSuccess());
        Assert.assertTrue(parsedResponse.getAccessToken().startsWith("Bearer "));
        Assert.assertFalse("Refresh Token shouldn't be empty", parsedResponse.getRefreshToken().isEmpty());
        Assert.assertEquals(name, parsedResponse.getUser().getName());
        Assert.assertEquals(email.toLowerCase(Locale.ROOT), parsedResponse.getUser().getEmail());
    }

    public static void checkStatusCodeAndResponseForSuccessfulUpdateUserInfoRequest(
            Response response, String email, String name) {

        Assert.assertEquals("SC should be 200", SC_OK, response.getStatusCode());

        UpdateUserInfoResponse parsedResponse = response.as(UpdateUserInfoResponse.class);
        Assert.assertEquals(true, parsedResponse.getSuccess());
        Assert.assertEquals(name, parsedResponse.getUser().getName());
        Assert.assertEquals(email.toLowerCase(Locale.ROOT), parsedResponse.getUser().getEmail());
    }

    public static void checkStatusCodeAndResponseForFailedUpdateUserInfoRequest(
            Response response, int statusCode, String expectedMessage) {

        Assert.assertEquals(String.format("SC should be %s", statusCode), statusCode, response.getStatusCode());

        UpdateUserInfoFailedResponse parsedResponse = response.as(UpdateUserInfoFailedResponse.class);
        Assert.assertEquals(false, parsedResponse.getSuccess());
        Assert.assertEquals(expectedMessage, parsedResponse.getMessage());
    }
}
