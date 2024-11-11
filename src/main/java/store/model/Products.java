package store.model;

import store.constant.ErrorMessage;
import store.dto.ProductDisplayDTO;
import store.dto.ProductsLoaderDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.service.MembershipDiscount;
import store.view.InputView;

public class Products {
    private final List<Product> product;

    public Products(List<ProductsLoaderDTO> dtos) {
        this.product = new ArrayList<>();
        for (ProductsLoaderDTO dto : dtos) {
            this.product.add(new Product(dto));
        }
    }

    public Optional<Product> findProductByName(String name) {
        return product.stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(product);
    }

    public List<ProductDisplayDTO> getProductDisplayList() {
        List<ProductDisplayDTO> displayList = new ArrayList<>();
        for (Product product : product) {
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
            Integer quantity = Integer.parseInt(item[1]);

            findProductByName(name)
                    .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_PRODUCT_NAME.getMessage()))
                    .sell(quantity);
        }
    }

    public Integer applyMembershipDiscount(Integer amount) {
        String input = InputView.getMembershipDiscountInput();
        if (input.equalsIgnoreCase("Y")) {
            MembershipDiscount discountCalculator = new MembershipDiscount();
            Integer discount = discountCalculator.calculateDiscount(amount);
            return amount - discount;
        }
        return amount;
    }

    public Integer calculateTotalAmount(List<String[]> purchasedItems) {
        Integer totalAmount = 0;
        for (String[] item : purchasedItems) {
            String name = item[0];
            Integer quantity = Integer.parseInt(item[1]);
            Product product = findProductByName(name)
                    .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_PRODUCT_NAME.getMessage()));

            totalAmount += product.getPrice() * quantity;
        }
        return totalAmount;
    }


    public List<ProductDisplayDTO> convertToDisplayDTO(List<String[]> parsedItems) {
        List<ProductDisplayDTO> displayItems = new ArrayList<>();
        for (String[] item : parsedItems) {
            String productName = item[0];
            Integer quantity = Integer.parseInt(item[1]);

            Product product = findProductByName(productName)
                    .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_PRODUCT_NAME.getMessage()));

            displayItems.add(new ProductDisplayDTO(
                    product.getName(),
                    product.getPrice(),
                    quantity,
                    product.getPromotion()
            ));
        }
        return displayItems;
    }
}
