package responses;

import java.util.List;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class GetUserOrdersResponse {

    private Boolean success;
    private List<OrderInfo> orders;
    private Integer total;
    private Integer totalToday;

    public GetUserOrdersResponse(Boolean success, List<OrderInfo> orders, Integer total, Integer totalToday) {
        this.success = success;
        this.orders = orders;
        this.total = total;
        this.totalToday = totalToday;
    }

    public Boolean getSuccess() {
        return success;
    }

    public List<OrderInfo> getOrders() {
        return orders;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getTotalToday() {
        return totalToday;
    }

}
