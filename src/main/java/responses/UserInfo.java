package responses;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class UserInfo {

    private String email;
    private String password;

    public UserInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
