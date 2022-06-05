package responses;

import requests.GetUpdateRemoveUserInfoRequest;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class UpdateUserInfoResponse {

    private Boolean success;
    private GetUpdateRemoveUserInfoRequest user;

    public UpdateUserInfoResponse(Boolean success, GetUpdateRemoveUserInfoRequest user) {
        this.success = success;
        this.user = user;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public GetUpdateRemoveUserInfoRequest getUser() {
        return user;
    }

    public void setUser(GetUpdateRemoveUserInfoRequest user) {
        this.user = user;
    }
}
