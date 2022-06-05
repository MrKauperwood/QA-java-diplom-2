package responses;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class UpdateUserInfoFailedResponse {

    private Boolean success;
    private String message;

    public UpdateUserInfoFailedResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
