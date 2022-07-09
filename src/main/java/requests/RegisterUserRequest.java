package requests;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class RegisterUserRequest {

    private String email;
    private String password;
    private String name;

    public RegisterUserRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

}
