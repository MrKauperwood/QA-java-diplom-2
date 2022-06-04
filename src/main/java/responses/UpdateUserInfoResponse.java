package responses;

import requests.UpdateUserInfoRequest;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class UpdateUserInfoResponse {

    private Boolean success;
    private UpdateUserInfoRequest user;

    public UpdateUserInfoResponse(Boolean success, UpdateUserInfoRequest user) {
        this.success = success;
        this.user = user;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public UpdateUserInfoRequest getUser() {
        return user;
    }

    public void setUser(UpdateUserInfoRequest user) {
        this.user = user;
    }
}
