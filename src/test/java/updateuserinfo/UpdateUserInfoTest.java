package updateuserinfo;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.LoginRequest;
import requests.RegisterUserRequest;
import requests.GetUpdateRemoveUserInfoRequest;
import responses.LoginResponse;

import java.util.Locale;

import static io.restassured.RestAssured.baseURI;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static reststeps.Constants.*;
import static reststeps.SendRequest.sendRequestLoginUser;
import static reststeps.SendRequest.sendRequestUpdateUserInfo;
import static reststeps.UserSteps.*;
import static reststeps.Utils.*;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class UpdateUserInfoTest {

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
    @DisplayName("Check SC 200 and response after successful email updating user info")
    public void checkSC200AndResponseAfterSuccessfulUpdatingEmailUserInfo() {
        RegisterUserRequest registeredUser = registerNewUser();
        LoginResponse parsedLoginResponse = loginUnderUser(registeredUser);
        token = parsedLoginResponse.getAccessToken();

        GetUpdateRemoveUserInfoRequest UpdateUserInfoRequest =
                new GetUpdateRemoveUserInfoRequest("new" + parsedLoginResponse.getUser().getEmail(), null, null);

        Response updateInfoResponse =
                sendRequestUpdateUserInfo(UpdateUserInfoRequest, parsedLoginResponse.getAccessToken());
        checkStatusCodeAndResponseForSuccessfulUpdateUserInfoRequest(
                updateInfoResponse,
                "new" + parsedLoginResponse.getUser().getEmail(),
                parsedLoginResponse.getUser().getName());
    }

    @Test
    @DisplayName("Check SC 200 and response after successful updating name in user info")
    public void checkSC200AndResponseAfterSuccessfulUpdatingNameInUserInfo() {
        RegisterUserRequest registeredUser = registerNewUser();
        LoginRequest loginRequest = new LoginRequest(registeredUser.getEmail(), registeredUser.getPassword());
        Response loginResponse = sendRequestLoginUser(loginRequest);

        LoginResponse parsedLoginResponse = loginResponse.as(LoginResponse.class);
        token = parsedLoginResponse.getAccessToken();

        GetUpdateRemoveUserInfoRequest getUpdateRemoveUserInfoRequest =
                new GetUpdateRemoveUserInfoRequest(null, "new" + parsedLoginResponse.getUser().getName(), null);

        Response updateInfoResponse =
                sendRequestUpdateUserInfo(getUpdateRemoveUserInfoRequest, parsedLoginResponse.getAccessToken());

        checkStatusCodeAndResponseForSuccessfulUpdateUserInfoRequest(
                updateInfoResponse,
                parsedLoginResponse.getUser().getEmail().toLowerCase(Locale.ROOT),
                "new" + parsedLoginResponse.getUser().getName());
    }

    @Test
    @DisplayName("Check SC 200 and response after successful updating password in user info")
    public void checkSC200AndResponseAfterSuccessfulUpdatingPasswordInUserInfo() {
        RegisterUserRequest registeredUser = registerNewUser();
        LoginRequest loginRequest = new LoginRequest(registeredUser.getEmail(), registeredUser.getPassword());
        Response loginResponse = sendRequestLoginUser(loginRequest);

        LoginResponse parsedLoginResponse = loginResponse.as(LoginResponse.class);
        token = parsedLoginResponse.getAccessToken();

        GetUpdateRemoveUserInfoRequest getUpdateRemoveUserInfoRequest =
                new GetUpdateRemoveUserInfoRequest(null, null, registeredUser.getPassword() + "new");

        Response updateInfoResponse =
                sendRequestUpdateUserInfo(getUpdateRemoveUserInfoRequest, parsedLoginResponse.getAccessToken());

        checkStatusCodeAndResponseForSuccessfulUpdateUserInfoRequest(
                updateInfoResponse,
                parsedLoginResponse.getUser().getEmail().toLowerCase(Locale.ROOT),
                parsedLoginResponse.getUser().getName());
    }

    @Test
    @DisplayName("Check SC 403 and response after failed updating user info to the same email")
    public void checkSC403AndResponseAfterFailedUpdatingUserInfoToTheSameEmail() {
        RegisterUserRequest registeredUser = registerNewUser();
        LoginRequest loginRequest =
                new LoginRequest(registeredUser.getEmail(), registeredUser.getPassword());
        Response loginResponse = sendRequestLoginUser(loginRequest);

        LoginResponse parsedLoginResponse = loginResponse.as(LoginResponse.class);
        token = parsedLoginResponse.getAccessToken();

        GetUpdateRemoveUserInfoRequest getUpdateRemoveUserInfoRequest =
                new GetUpdateRemoveUserInfoRequest(parsedLoginResponse.getUser().getEmail(), null, null);

        Response updateInfoResponse =
                sendRequestUpdateUserInfo(getUpdateRemoveUserInfoRequest, parsedLoginResponse.getAccessToken());

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
        token = parsedLoginResponse.getAccessToken();

        GetUpdateRemoveUserInfoRequest getUpdateRemoveUserInfoRequest =
                new GetUpdateRemoveUserInfoRequest(null, parsedLoginResponse.getUser().getName(), null);

        Response updateInfoResponse =
                sendRequestUpdateUserInfo(getUpdateRemoveUserInfoRequest, parsedLoginResponse.getAccessToken());

        checkStatusCodeAndResponseForSuccessfulUpdateUserInfoRequest(
                updateInfoResponse,
                parsedLoginResponse.getUser().getEmail().toLowerCase(Locale.ROOT),
                parsedLoginResponse.getUser().getName());
    }


    @Test
    @DisplayName("Check SC 401 and response after failed updating email without authorization")
    public void checkSC401AndResponseAfterFailedUpdatingEmailWithoutAuthorization() {
        RegisterUserRequest registeredUser = registerNewUser();

        GetUpdateRemoveUserInfoRequest getUpdateRemoveUserInfoRequest =
                new GetUpdateRemoveUserInfoRequest("new" + registeredUser.getEmail().toLowerCase(Locale.ROOT), null, null);

        Response updateInfoResponse =
                sendRequestUpdateUserInfo(getUpdateRemoveUserInfoRequest, "");

        checkStatusCodeAndResponseForFailedUpdateUserInfoRequest(
                updateInfoResponse,
                SC_UNAUTHORIZED,
                SHOULD_BE_AUTHORISED_MSG);
    }

    @Test
    @DisplayName("Check SC 401 and response after failed updating user name without authorization")
    public void checkSC401AndResponseAfterFailedUpdatingNameWithoutAuthorization() {
        RegisterUserRequest registeredUser = registerNewUser();

        GetUpdateRemoveUserInfoRequest getUpdateRemoveUserInfoRequest =
                new GetUpdateRemoveUserInfoRequest(null, "new" + registeredUser.getName(), null);

        Response updateInfoResponse =
                sendRequestUpdateUserInfo(getUpdateRemoveUserInfoRequest, "");

        checkStatusCodeAndResponseForFailedUpdateUserInfoRequest(
                updateInfoResponse,
                SC_UNAUTHORIZED,
                SHOULD_BE_AUTHORISED_MSG);
    }

    @Test
    @DisplayName("Check SC 401 and response after failed updating user password without authorization")
    public void checkSC401AndResponseAfterFailedUpdatingPasswordWithoutAuthorization() {
        RegisterUserRequest registeredUser = registerNewUser();

        GetUpdateRemoveUserInfoRequest getUpdateRemoveUserInfoRequest =
                new GetUpdateRemoveUserInfoRequest(null, null, registeredUser.getPassword() + "New");

        Response updateInfoResponse =
                sendRequestUpdateUserInfo(getUpdateRemoveUserInfoRequest, "");

        checkStatusCodeAndResponseForFailedUpdateUserInfoRequest(
                updateInfoResponse,
                SC_UNAUTHORIZED,
                SHOULD_BE_AUTHORISED_MSG);
    }
}
