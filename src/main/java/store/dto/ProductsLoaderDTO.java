package store.dto;

public class ProductsLoaderDTO {
    private final String name;
    private final int price;
    private final int quantity;
    private final String promotion;

    public ProductsLoaderDTO(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public String getProductName() {
        return name;
    }

    public int getProductPrice() {
        return price;
    }

    public int getProductQuantity() {
        return quantity;
    }

    public String getProductPromotion() {
        return promotion;
    }
}
