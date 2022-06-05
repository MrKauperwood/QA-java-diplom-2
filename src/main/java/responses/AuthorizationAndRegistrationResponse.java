package responses;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class AuthorizationAndRegistrationResponse {

    private Boolean success;
    private UserInfo user;
    private String accessToken;
    private String refreshToken;

    public AuthorizationAndRegistrationResponse(Boolean success, UserInfo user, String accessToken, String refreshToken) {
        this.success = success;
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public Boolean getSuccess() {
        return success;
    }

    public UserInfo getUser() {
        return user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

}
