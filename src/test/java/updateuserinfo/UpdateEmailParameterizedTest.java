package updateuserinfo;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import requests.LoginRequest;
import requests.RegisterUserRequest;
import requests.UpdateUserInfoRequest;
import responses.LoginResponse;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static reststeps.Constants.*;
import static reststeps.SendRequest.*;
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

    public UpdateEmailParameterizedTest(String email, List<Object> expectedResult) {
        this.email = email;
        this.expectedResult = expectedResult;
    }

    @Before
    public void setUp() {
        baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Parameterized.Parameters(name = "Test data: {0}")
    public static Object[][] getUserData() {
        return new Object[][]{
                {"", List.of(SC_FORBIDDEN, INCORRECT_EMAIL)},
                {" ", List.of(SC_FORBIDDEN, INCORRECT_EMAIL)},
                {"asdgmail.ru", List.of(SC_FORBIDDEN, INCORRECT_EMAIL)},
                {"русский@пьфш.ру", List.of(SC_FORBIDDEN, INCORRECT_EMAIL)}
        };
    }

    @Test
    public void checkUpdatingUserEmailWithLogin() {
        RegisterUserRequest registeredUser = registerNewUser();
        LoginRequest loginRequest = new LoginRequest(registeredUser.getEmail(), registeredUser.getPassword());
        Response loginResponse = sendRequestLoginUser(loginRequest);
        LoginResponse parsedLoginResponse = loginResponse.as(LoginResponse.class);
        UpdateUserInfoRequest updateUserInfoRequest =
                new UpdateUserInfoRequest(email, null);

        Response updateInfoResponse =
                sendRequestUpdateUserInfo(updateUserInfoRequest, parsedLoginResponse.getAccessToken());
        checkStatusCodeAndResponseForFailedUpdateUserInfoRequest(
                updateInfoResponse,
                (int) expectedResult.get(0),
                expectedResult.get(1).toString());
    }
}
