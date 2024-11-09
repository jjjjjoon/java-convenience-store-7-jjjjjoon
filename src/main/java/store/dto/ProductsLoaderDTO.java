package store.dto;

import store.constant.ErrorMessage;

public class ProductsLoaderDTO {
    private final String name;
    private final Integer price;
    private Integer quantity; // 수량은 상태값으로 동적으로 관리
    private final String promotion;

    public ProductsLoaderDTO(String name, Integer price, Integer quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }
    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        return promotion;
    }

}
