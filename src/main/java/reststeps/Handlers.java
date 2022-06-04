package reststeps;

/**
 * Author: Alexey Bondarenko
 * Date: 04.06.2022
 */
public class Handlers {

    public static final String HANDLER_POST_REGISTER_NEW_USER = "/api/auth/register";
    public static final String HANDLER_POST_LOGIN = "/api/auth/login";
    public static final String HANDLER_POST_CREATE_ORDER = "/api/orders";

    public static final String HANDLER_GET_USER_INFO = "/api/auth/user";
    public static final String HANDLER_GET_USER_ORDERS = HANDLER_POST_CREATE_ORDER;

    public static final String HANDLER_PATCH_USER_INFO = HANDLER_GET_USER_INFO;
}
