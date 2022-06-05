package requests;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class GetUpdateRemoveUserInfoRequest {

    private String email;
    private String name;

    public GetUpdateRemoveUserInfoRequest(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

}
