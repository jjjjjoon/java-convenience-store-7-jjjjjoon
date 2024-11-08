package store.model;

import store.constant.ErrorMessage;
import store.dto.ProductDisplayDTO;
import store.dto.ProductsLoaderDTO;
import store.utils.ProductsLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Products {
    private final List<Product> products;

    public Products() {
        this.products = new ArrayList<>();
        loadProducts();
    }

    // ProductLoader를 통해 데이터를 로드하여 Product 리스트를 초기화
    private void loadProducts() {
        List<ProductsLoaderDTO> productList = ProductsLoader.loadProducts();
        for (ProductsLoaderDTO dto : productList) {
            products.add(new Product(dto));
        }
    }

    // 특정 제품을 이름으로 찾는 메서드
    public Optional<Product> findProductByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    // 전체 제품 목록 반환
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public List<ProductDisplayDTO> getProductDisplayList() {
        List<ProductDisplayDTO> displayList = new ArrayList<>();
        for (Product product : products) {
            displayList.add(new ProductDisplayDTO(
                    product.getName(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getPromotion()
            ));
        }
        return displayList;
    }

    public void decreaseInventory(List<String[]> purchasedItems) {
        for (String[] item : purchasedItems) {
            String name = item[0];
            int quantity = Integer.parseInt(item[1]);

            findProductByName(name)
                    .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_PRODUCT_NAME.getMessage()))
                    .sell(quantity); // findProductByName으로 찾고 sell 호출
        }
    }
}
