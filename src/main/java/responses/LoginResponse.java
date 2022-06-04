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

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}
