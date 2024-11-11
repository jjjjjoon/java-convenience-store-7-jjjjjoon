package store.view;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import store.constant.Constants;
import store.constant.Message;
import store.dto.ProductDisplayDTO;

public class OutputView {

    public void printWelcomeMessage() {
        System.out.println(Message.OUTPUT_WELCOME_MESSAGE.getMessage());
        System.out.println(Message.OUTPUT_OUR_PRODUCTS.getMessage());
    }

//    public void printCurrentProducts(List<ProductDisplayDTO> products) {
//        for (ProductDisplayDTO product : products) {
//            String productInfo = formatProductInfo(product);
//            System.out.println(productInfo);
//        }
//    }

    public void printCurrentProducts(List<ProductDisplayDTO> products) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.KOREA);

        System.out.println("안녕하세요. 우아한 편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
        for (ProductDisplayDTO product : products) {
            String priceFormatted = formatter.format(product.getPrice()); // 가격에 쉼표 적용

            // 재고가 없는 경우 "재고 없음" 메시지 출력
            if (product.getQuantity() == 0) {
                System.out.printf("- %s %s원 재고 없음", product.getName(), priceFormatted);
            } else {
                String quantityFormatted = formatter.format(product.getQuantity()); // 수량에 쉼표 적용
                System.out.printf("- %s %s원 %s개", product.getName(), priceFormatted, quantityFormatted);
            }

            if (product.getPromotion() != null) {
                System.out.print(" " + product.getPromotion());
            }
            System.out.println();
        }

    }

    private String formatProductInfo(ProductDisplayDTO product) {

        String name = product.getName();
        Integer price = product.getPrice();
        Integer quantity = product.getQuantity();
        String promotion = product.getPromotion();

        if (quantity == 0) {
            return String.format(Constants.NO_QUANTITY, name, price);
        }
        if (!Objects.equals(promotion, null)) {
            return String.format(Constants.HAVE_PROMOTION, name, price, quantity, promotion);
        }
        return String.format(Constants.NO_PROMOTION, name, price, quantity);
    }

    // OutputView.java
    public void printReceipt(List<ProductDisplayDTO> items, Integer totalAmount, Integer eventDiscount, Integer membershipDiscount, Integer finalAmount) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.KOREA);

        System.out.println("===========W 편의점=============");
        System.out.println("상품명\t\t수량\t금액");
        for (ProductDisplayDTO item : items) {
            String itemTotalPrice = formatter.format(item.getPrice() * item.getQuantity());
            System.out.printf("%s\t\t%d\t%s원%n", item.getName(), item.getQuantity(), itemTotalPrice);
        }
        System.out.println("===========증정=============");
        // 증정 상품을 출력하는 코드 추가 가능 (필요 시)

        System.out.println("==============================");
        System.out.printf("총구매액\t\t%d\t%s원%n", items.size(), formatter.format(totalAmount));
        System.out.printf("행사할인\t\t\t-%s원%n", formatter.format(eventDiscount));
        System.out.printf("멤버십할인\t\t\t-%s원%n", formatter.format(membershipDiscount));
        System.out.printf("내실돈\t\t\t%s원%n", formatter.format(finalAmount));
    }

    public void printGoodbyeMessage() {
        System.out.println("감사합니다. 다음에 또 방문해 주세요!");
    }
}
