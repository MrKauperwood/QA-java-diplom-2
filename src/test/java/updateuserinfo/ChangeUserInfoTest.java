package updateuserinfo;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import requests.LoginRequest;
import requests.RegisterUserRequest;
import requests.UpdateUserInfoRequest;
import responses.LoginResponse;

import java.util.Locale;

import static io.restassured.RestAssured.baseURI;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static reststeps.Constants.CHANGE_TO_THE_SAME_EMAIL_MSG;
import static reststeps.Constants.SHOULD_BE_AUTHORISED_MSG;
import static reststeps.SendRequest.sendRequestLoginUser;
import static reststeps.SendRequest.sendRequestUpdateUserInfo;
import static reststeps.UserSteps.registerNewUser;
import static reststeps.Utils.*;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class ChangeUserInfoTest {

    @Before
    public void setUp() {
        baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    @DisplayName("Check SC 200 and response after successful email updating user info")
    public void checkSC200AndResponseAfterSuccessfulUpdatingEmailUserInfo() {
        RegisterUserRequest registeredUser = registerNewUser();
        LoginRequest loginRequest =
                new LoginRequest(registeredUser.getEmail(), registeredUser.getPassword());
        Response loginResponse = sendRequestLoginUser(loginRequest);

        LoginResponse parsedLoginResponse = loginResponse.as(LoginResponse.class);

        UpdateUserInfoRequest updateUserInfoRequest =
                new UpdateUserInfoRequest("new" + parsedLoginResponse.getUser().getEmail(), null);

        Response updateInfoResponse =
                sendRequestUpdateUserInfo(updateUserInfoRequest, parsedLoginResponse.getAccessToken());
        checkStatusCodeAndResponseForSuccessfulUpdateUserInfoRequest(
                updateInfoResponse,
                "new" + parsedLoginResponse.getUser().getEmail(),
                parsedLoginResponse.getUser().getName());
    }

    @Test
    @DisplayName("Check SC 200 and response after successful updating name in user info")
    public void checkSC200AndResponseAfterSuccessfulUpdatingNameInUserInfo() {
        RegisterUserRequest registeredUser = registerNewUser();
        LoginRequest loginRequest =
                new LoginRequest(registeredUser.getEmail(), registeredUser.getPassword());
        Response loginResponse = sendRequestLoginUser(loginRequest);

        LoginResponse parsedLoginResponse = loginResponse.as(LoginResponse.class);

        UpdateUserInfoRequest updateUserInfoRequest =
                new UpdateUserInfoRequest(null, "new" + parsedLoginResponse.getUser().getName());

        Response updateInfoResponse =
                sendRequestUpdateUserInfo(updateUserInfoRequest, parsedLoginResponse.getAccessToken());

        checkStatusCodeAndResponseForSuccessfulUpdateUserInfoRequest(
                updateInfoResponse,
                parsedLoginResponse.getUser().getEmail().toLowerCase(Locale.ROOT),
                "new" + parsedLoginResponse.getUser().getName());
    }

    @Test
    @DisplayName("Check SC 403 and response after failed updating user info to the same email")
    public void checkSC403AndResponseAfterFailedUpdatingUserInfoToTheSameEmail() {
        RegisterUserRequest registeredUser = registerNewUser();
        LoginRequest loginRequest =
                new LoginRequest(registeredUser.getEmail(), registeredUser.getPassword());
        Response loginResponse = sendRequestLoginUser(loginRequest);

        LoginResponse parsedLoginResponse = loginResponse.as(LoginResponse.class);

        UpdateUserInfoRequest updateUserInfoRequest =
                new UpdateUserInfoRequest(parsedLoginResponse.getUser().getEmail(), null);

        Response updateInfoResponse =
                sendRequestUpdateUserInfo(updateUserInfoRequest, parsedLoginResponse.getAccessToken());

        checkStatusCodeAndResponseForFailedUpdateUserInfoRequest(
                updateInfoResponse,
                SC_FORBIDDEN,
                CHANGE_TO_THE_SAME_EMAIL_MSG);
    }

    @Test
    @DisplayName("Check SC 200 and response after updating user name to the same name")
    public void checkSC200AndResponseAfterUpdatingUserNameToTheSameName() {
        RegisterUserRequest registeredUser = registerNewUser();
        LoginRequest loginRequest =
                new LoginRequest(registeredUser.getEmail(), registeredUser.getPassword());
        Response loginResponse = sendRequestLoginUser(loginRequest);

        LoginResponse parsedLoginResponse = loginResponse.as(LoginResponse.class);

        UpdateUserInfoRequest updateUserInfoRequest =
                new UpdateUserInfoRequest(null, parsedLoginResponse.getUser().getName());

        Response updateInfoResponse =
                sendRequestUpdateUserInfo(updateUserInfoRequest, parsedLoginResponse.getAccessToken());

        checkStatusCodeAndResponseForSuccessfulUpdateUserInfoRequest(
                updateInfoResponse,
                parsedLoginResponse.getUser().getEmail().toLowerCase(Locale.ROOT),
                parsedLoginResponse.getUser().getName());
    }


    @Test
    @DisplayName("Check SC 401 and response after failed updating email without authorization")
    public void checkSC401AndResponseAfterFailedUpdatingEmailWithoutAuthorization() {
        RegisterUserRequest registeredUser = registerNewUser();

        UpdateUserInfoRequest updateUserInfoRequest =
                new UpdateUserInfoRequest("new" + registeredUser.getEmail().toLowerCase(Locale.ROOT), null);

        Response updateInfoResponse =
                sendRequestUpdateUserInfo(updateUserInfoRequest, "");

        checkStatusCodeAndResponseForFailedUpdateUserInfoRequest(
                updateInfoResponse,
                SC_UNAUTHORIZED,
                SHOULD_BE_AUTHORISED_MSG);
    }

    @Test
    @DisplayName("Check SC 401 and response after failed updating user name without authorization")
    public void checkSC401AndResponseAfterFailedUpdatingNameWithoutAuthorization() {
        RegisterUserRequest registeredUser = registerNewUser();

        UpdateUserInfoRequest updateUserInfoRequest =
                new UpdateUserInfoRequest(null, "new" + registeredUser.getName());

        Response updateInfoResponse =
                sendRequestUpdateUserInfo(updateUserInfoRequest, "");

        checkStatusCodeAndResponseForFailedUpdateUserInfoRequest(
                updateInfoResponse,
                SC_UNAUTHORIZED,
                SHOULD_BE_AUTHORISED_MSG);
    }
}
