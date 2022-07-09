package requests;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class GetUpdateRemoveUserInfoRequest {

    private String email;
    private String name;
    private String password;

    public GetUpdateRemoveUserInfoRequest(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
