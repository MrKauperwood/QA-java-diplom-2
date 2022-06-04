package responses;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class CreateOrderResponse {

    private String name;
    private OrderInfoAfterCreation order;
    private Boolean success;

    public CreateOrderResponse(String name, OrderInfoAfterCreation order, Boolean success) {
        this.name = name;
        this.order = order;
        this.success = success;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrderInfoAfterCreation getOrder() {
        return order;
    }

    public void setOrder(OrderInfoAfterCreation order) {
        this.order = order;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
