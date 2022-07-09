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

    public List<OrderInfo> getOrders() {
        return orders;
    }

}
