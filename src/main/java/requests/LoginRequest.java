package requests;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class LoginRequest {

    private String email;
    private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
