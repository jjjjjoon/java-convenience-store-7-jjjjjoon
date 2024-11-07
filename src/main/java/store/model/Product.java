package store.model;

import store.dto.ProductsLoaderDTO;
import store.constant.ErrorMessage;

public class Product {
    private final String name;
    private final int price;
    private int quantity; // 수량은 상태값으로 동적으로 관리
    private final String promotion;

    public Product(ProductsLoaderDTO dto) {
        this.name = dto.getProductName();
        this.price = dto.getProductPrice();
        this.quantity = dto.getProductQuantity();
        this.promotion = dto.getProductPromotion();
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

    // 수량 감소 메서드
    public void sell(int quantity) {
        sellException(quantity);
        if (this.quantity >= quantity) {
            this.quantity -= quantity;
        }
    }

    private void sellException(int quantity) {
        if (this.quantity < quantity) {
            throw new IllegalArgumentException(
                    ErrorMessage.DEFAULT_HEADER_MESSAGE.getMessage() + ErrorMessage.TOO_MANY_QUANTITY.getMessage());
        }
    }
}
