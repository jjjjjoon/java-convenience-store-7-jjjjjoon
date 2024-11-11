package store.dto;


public class ProductsLoaderDTO {
    private final String name;
    private final Integer price;
    private Integer quantity;
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
