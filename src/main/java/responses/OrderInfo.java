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

    public Integer getNumber() {
        return number;
    }

}
