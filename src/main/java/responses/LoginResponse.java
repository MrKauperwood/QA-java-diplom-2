package responses;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class LoginResponse {

    private Boolean success;
    private String accessToken;
    private String refreshToken;
    private UserInfo user;

    public LoginResponse(Boolean success, String accessToken, String refreshToken, UserInfo user) {
        this.success = success;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public UserInfo getUser() {
        return user;
    }

}
