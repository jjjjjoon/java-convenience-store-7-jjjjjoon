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

    // 특정 제품을 이름으로 찾는 메서드
    public Optional<Product> findProductByName(String name) {
        return product.stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    // 전체 제품 목록 반환
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
                    .sell(quantity); // findProductByName으로 찾고 sell 호출
        }
    }

    public Integer applyMembershipDiscount(Integer amount) {
        String input = InputView.getMembershipDiscountInput();
        if (input.equalsIgnoreCase("Y")) {
            MembershipDiscount discountCalculator = new MembershipDiscount();
            Integer discount = discountCalculator.calculateDiscount(amount);
            return amount - discount;
        }
        return amount; // 할인 없이 그대로 반환
    }

    public Integer calculateTotalAmount(List<String[]> purchasedItems) {
        Integer totalAmount = 0;
        for (String[] item : purchasedItems) {
            String name = item[0];
            Integer quantity = Integer.parseInt(item[1]);
            Product product = findProductByName(name)
                    .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_PRODUCT_NAME.getMessage()));

            // 개별 상품의 총 금액을 누적하여 계산
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

            // ProductDisplayDTO 생성 및 리스트에 추가
            displayItems.add(new ProductDisplayDTO(
                    product.getName(),
                    product.getPrice(),
                    quantity,       // 요청한 구매 수량으로 설정
                    product.getPromotion()
            ));
        }
        return displayItems;
    }
}
