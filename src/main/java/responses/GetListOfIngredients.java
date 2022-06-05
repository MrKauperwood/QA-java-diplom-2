package responses;

import java.util.List;

/**
 * Author: Alexey Bondarenko
 * Date: 05.06.2022
 */
public class GetListOfIngredients {

    private Boolean success;
    private List<IngredientInfo> data;

    public GetListOfIngredients(Boolean success, List<IngredientInfo> data) {
        this.success = success;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<IngredientInfo> getData() {
        return data;
    }

    public void setData(List<IngredientInfo> data) {
        this.data = data;
    }
}
