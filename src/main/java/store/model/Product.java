package store.model;

import store.constant.ErrorMessage;
import store.dto.ProductsLoaderDTO;

public class Product {
    public final String name;
    public final Integer price;
    public Integer quantity;
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

    public Integer getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        return promotion;
    }

    // 수량 감소 메서드
    public void sell(Integer amount) {
        sellException(amount);
        if (this.quantity >= amount) {
            this.quantity -= amount;
        }
    }

    private void sellException(Integer amount) {
        if (this.quantity < amount) {
            throw new IllegalArgumentException(ErrorMessage.TOO_MANY_QUANTITY.getMessage());
        }
    }
}
