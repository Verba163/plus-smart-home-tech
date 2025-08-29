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
}
