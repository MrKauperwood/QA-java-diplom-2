package registration;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import requests.RegisterUserRequest;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static org.apache.http.HttpStatus.*;
import static reststeps.Constants.INCORRECT_EMAIL;
import static reststeps.Constants.REQUIRED_FIELDS_ARE_EMPTY_MSG;
import static reststeps.SendRequest.sendRequestRegisterNewUser;
import static reststeps.UserSteps.generateDataForNewUser;
import static reststeps.Utils.checkStatusCodeAndResponseForFailedRegisterRequest;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
@RunWith(Parameterized.class)
public class RegisterNewUserParameterizedTest {

    private RegisterUserRequest data;
    private List<Object> expectedResult;

    public RegisterNewUserParameterizedTest(List<Object> data, List<Object> expectedResult) {
        this.data = new RegisterUserRequest(data.get(0).toString(), data.get(1).toString(), data.get(2).toString());
        this.expectedResult = expectedResult;
    }

    @Before
    public void setUp() {
        baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Parameterized.Parameters(name = "Test data: {0}")
    public static Object[][] getUserData() {
        HashMap<String, String> newUserData = generateDataForNewUser();
        String pas = newUserData.get("password");
        String name = newUserData.get("name");
        String email = newUserData.get("email");
        return new Object[][]{
                {List.of("", pas, name), List.of(SC_FORBIDDEN, REQUIRED_FIELDS_ARE_EMPTY_MSG)},
                {List.of("asd@gmail", pas, name), List.of(SC_BAD_REQUEST, INCORRECT_EMAIL)}, // bug
                {List.of("asdgmail.ru", pas, name), List.of(SC_BAD_REQUEST, INCORRECT_EMAIL)}, // bug
                {List.of(" ", pas, name), List.of(SC_BAD_REQUEST, INCORRECT_EMAIL)}, // bug
                {List.of(" " + email, pas, name), List.of(SC_BAD_REQUEST, INCORRECT_EMAIL)}, // bug
                {List.of(email + " ", pas, name), List.of(SC_BAD_REQUEST, INCORRECT_EMAIL)}, // bug
                {List.of(name + " @gmail.com", pas, name), List.of(SC_BAD_REQUEST, INCORRECT_EMAIL)}, // bug
                {List.of(name + "@ gmail.com", pas, name), List.of(SC_BAD_REQUEST, INCORRECT_EMAIL)}, // bug
                {List.of(name + "@gmai.l.com", pas, name), List.of(SC_BAD_REQUEST, INCORRECT_EMAIL)}, // bug
                {List.of("русский@пьфш.ру", pas, name), List.of(SC_BAD_REQUEST, INCORRECT_EMAIL)}, // bug
                {List.of("1" + email, pas, name), List.of(SC_BAD_REQUEST, INCORRECT_EMAIL)}, // bug

                {List.of(email, "", name), List.of(SC_FORBIDDEN, REQUIRED_FIELDS_ARE_EMPTY_MSG)},

                {List.of(email, pas, ""), List.of(SC_FORBIDDEN, REQUIRED_FIELDS_ARE_EMPTY_MSG)},

        };
    }

    @Test
    public void checkRegisterNewUserWithIncorrectParameters() {
        Response response = sendRequestRegisterNewUser(data);
        checkStatusCodeAndResponseForFailedRegisterRequest(response, (int) expectedResult.get(0), expectedResult.get(1).toString());

    }

}
