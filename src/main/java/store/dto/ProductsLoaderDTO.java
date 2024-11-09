package store.dto;

import store.constant.ErrorMessage;

public class ProductsLoaderDTO {
    private final String name;
    private final int price;
    private int quantity; // 수량은 상태값으로 동적으로 관리
    private final String promotion;

    public ProductsLoaderDTO(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        return promotion;
    }

}
