package responses;

import java.util.List;

/**
 * Author: Alexey Bondarenko
 * Date: 05.06.2022
 */
public class GetAllOrdersResponse {

    private Boolean success;
    private List<OrderInfo> orders;
    private Integer total;
    private Integer totalToday;

    public GetAllOrdersResponse(Boolean success, List<OrderInfo> orders, Integer total, Integer totalToday) {
        this.success = success;
        this.orders = orders;
        this.total = total;
        this.totalToday = totalToday;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<OrderInfo> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderInfo> orders) {
        this.orders = orders;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalToday() {
        return totalToday;
    }

    public void setTotalToday(Integer totalToday) {
        this.totalToday = totalToday;
    }
}
