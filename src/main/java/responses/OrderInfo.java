package responses;

import java.util.List;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class OrderInfo {

    private List<String> ingredients;
    private String _id;
    private String status;
    private Integer number;
    private String createdAt;
    private String updatedAt;

    public OrderInfo(List<String> ingredients, String _id, String status, Integer number, String createdAt, String updatedAt) {
        this.ingredients = ingredients;
        this._id = _id;
        this.status = status;
        this.number = number;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
