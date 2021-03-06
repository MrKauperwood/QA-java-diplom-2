package responses;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class FailedResponse {

    private Boolean success;
    private String message;

    public FailedResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

}
