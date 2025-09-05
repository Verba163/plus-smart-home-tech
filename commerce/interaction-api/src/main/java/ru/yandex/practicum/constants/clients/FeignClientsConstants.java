package ru.yandex.practicum.constants.clients;

public interface FeignClientsConstants {
    public static final String BASE_PATH = "/api/v1/shopping-cart";
    public static final String REMOVE_ITEMS = "/api/v1/shopping-cart/remove";
    public static final String CHANGE_QUANTITY = "/api/v1/shopping-cart/change-quantity";

    public static final String BASE_PATH_CART = "/api/v1/shopping-store";
    public static final String REMOVE_PRODUCT = "/api/v1/shopping-store/removeProductFromStore";
    public static final String QUANTITY_STATE = "/api/v1/shopping-store/quantityState";

    public static final String BASE_PATH_WAREHOUSE = "/api/v1/warehouse";
    public static final String CHECK_PRODUCT_QUANTITY = "/api/v1/warehouse/check";
    public static final String ADD_PRODUCT_TO_WAREHOUSE = "/api/v1/warehouse/add";
    public static final String ADDRESS = "/api/v1/warehouse/address";
    public static final String RESERVATION = "/api/v1/warehouse/reservation";
    public static final String ASSEMBLY_PATH_WAREHOUSE = "/api/v1/warehouse/assembly";
    public static final String SHIPPED_PATH = "/api/v1/warehouse/shipped";
    public static final String RETURN_PATH_WAREHOUSE = "/api/v1/warehouse/return";

    public static final String BASE_PATH_DELIVERY = "/api/v1/delivery";
    public static final String SUCCESSFUL_PATH = BASE_PATH_DELIVERY + "/successful";
    public static final String PICKED_PATH = BASE_PATH_DELIVERY + "/picked";
    public static final String FAILED_PATH = BASE_PATH_DELIVERY + "/failed";
    public static final String COST_PATH = BASE_PATH_DELIVERY + "/cost";

    public static final String BASE_PATH_ORDER = "/api/v1/order";
    public static final String RETURN_PATH = BASE_PATH_ORDER + "/return";
    public static final String PAYMENT_PATH = BASE_PATH_ORDER + "/payment";
    public static final String PAYMENT_SUCCESS_PATH = PAYMENT_PATH + "/success";
    public static final String PAYMENT_FAILED_PATH = PAYMENT_PATH + "/failed";
    public static final String DELIVERY_PATH = BASE_PATH_ORDER + "/delivery";
    public static final String DELIVERY_SUCCESS_PATH = DELIVERY_PATH + "/success";
    public static final String DELIVERY_FAILED_PATH = DELIVERY_PATH + "/failed";
    public static final String COMPLETED_PATH = BASE_PATH_ORDER + "/completed";
    public static final String CALCULATE_TOTAL_PATH = BASE_PATH_ORDER + "/calculate/total";
    public static final String CALCULATE_DELIVERY_PATH = BASE_PATH_ORDER + "/calculate/delivery";
    public static final String ASSEMBLY_PATH = BASE_PATH_ORDER + "/assembly";
    public static final String ASSEMBLY_SUCCESS_PATH = ASSEMBLY_PATH + "/success";
    public static final String ASSEMBLY_FAILED_PATH = ASSEMBLY_PATH + "/failed";

    public static final String BASE_PATH_PAYMENT = "/api/v1/payment";
    public static final String TOTAL_COST_PATH = BASE_PATH_PAYMENT + "/totalCost";
    public static final String REFUND_PATH = BASE_PATH_PAYMENT + "/refund";
    public static final String PRODUCT_COST_PATH = BASE_PATH_PAYMENT + "/productCost";
    public static final String FAILED_PATH_PAYMENT = BASE_PATH_PAYMENT + "/failed";
    public static final String SUCCESS_PATH_PAYMENT = BASE_PATH_PAYMENT + "/success";
}
