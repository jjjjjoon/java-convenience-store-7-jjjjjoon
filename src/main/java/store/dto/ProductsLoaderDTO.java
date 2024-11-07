package store.dto;

public class ProductsLoaderDTO {
    private final String productName;
    private final int productPrice;
    private final int productQuantity;
    private final String productPromotion;

    public ProductLoaderDTO(String productName, int productPrice, int productQuantity, String productPromotion) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productPromotion = productPromotion;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public String getProductPromotion() {
        return productPromotion;
    }
}
