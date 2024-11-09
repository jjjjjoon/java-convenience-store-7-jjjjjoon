package store.model;

import store.constant.ErrorMessage;

public class Product {
    private final String name;
    private final int price;
    private int quantity; // 수량은 상태값으로 동적으로 관리
    private final String promotion;

    public Product(String name, int price, int quantity, String promotion) {
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
