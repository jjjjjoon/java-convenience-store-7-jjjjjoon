package store.model;

import store.constant.ErrorMessage;
import store.dto.ProductsLoaderDTO;

public class Product {
    public final String name;
    public final int price;
    public int quantity;
    public final String promotion;

    public Product(ProductsLoaderDTO dto) {
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.quantity = dto.getQuantity();  // promotion이 null일 때만 기본 재고로 설정
        this.promotion = dto.getPromotion();
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
    public void sell(int amount) {
        sellException(amount);
        if (this.quantity >= amount) {
            this.quantity -= amount;
        }
    }

    private void sellException(int amount) {
        if (this.quantity < amount) {
            throw new IllegalArgumentException(ErrorMessage.TOO_MANY_QUANTITY.getMessage());
        }
    }
}
