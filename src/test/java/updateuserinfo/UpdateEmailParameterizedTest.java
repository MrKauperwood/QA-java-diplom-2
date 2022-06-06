package updateuserinfo;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import requests.LoginRequest;
import requests.RegisterUserRequest;
import requests.GetUpdateRemoveUserInfoRequest;
import responses.LoginResponse;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static reststeps.Constants.*;
import static reststeps.SendRequest.*;
import static reststeps.UserSteps.deleteUser;
import static reststeps.UserSteps.registerNewUser;
import static reststeps.Utils.*;

/**
 * Author: Alexey Bondarenko
 * Date: 05.06.2022
 */
@RunWith(Parameterized.class)
public class UpdateEmailParameterizedTest {

    private String email;
    private List<Object> expectedResult;
    private String token;

    public UpdateEmailParameterizedTest(String email, List<Object> expectedResult) {
        this.email = email;
        this.expectedResult = expectedResult;
    }

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

    @Parameterized.Parameters(name = "Test data for email: \"{0}\"")
    public static Object[][] getUserData() {
        return new Object[][]{
                {"", List.of(SC_FORBIDDEN, INCORRECT_EMAIL_MSG)},
                {" ", List.of(SC_FORBIDDEN, INCORRECT_EMAIL_MSG)},
                {"asdgmail.ru", List.of(SC_FORBIDDEN, INCORRECT_EMAIL_MSG)},
                {"русский@пьфш.ру", List.of(SC_FORBIDDEN, INCORRECT_EMAIL_MSG)}
        };
    }

    @Test
    public void checkUpdatingUserEmailWithLogin() {
        RegisterUserRequest registeredUser = registerNewUser();
        LoginRequest loginRequest = new LoginRequest(registeredUser.getEmail(), registeredUser.getPassword());
        Response loginResponse = sendRequestLoginUser(loginRequest);
        LoginResponse parsedLoginResponse = loginResponse.as(LoginResponse.class);
        token = parsedLoginResponse.getAccessToken();

        GetUpdateRemoveUserInfoRequest getUpdateRemoveUserInfoRequest =
                new GetUpdateRemoveUserInfoRequest(email, null);

        Response updateInfoResponse =
                sendRequestUpdateUserInfo(getUpdateRemoveUserInfoRequest, parsedLoginResponse.getAccessToken());
        checkStatusCodeAndResponseForFailedUpdateUserInfoRequest(
                updateInfoResponse,
                (int) expectedResult.get(0),
                expectedResult.get(1).toString());
    }
}
