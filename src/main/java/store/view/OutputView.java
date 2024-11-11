package store.view;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import store.constant.Message;
import store.dto.ProductDisplayDTO;

public class OutputView {

    public void printWelcomeMessage() {
        System.out.println(Message.OUTPUT_WELCOME_MESSAGE.getMessage());
        System.out.println(Message.OUTPUT_OUR_PRODUCTS.getMessage());
        System.out.println();
    }

    public void printCurrentProducts(List<ProductDisplayDTO> products) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.KOREA);

        for (ProductDisplayDTO product : products) {
            String priceFormatted = formatter.format(product.getPrice());

            if (product.getQuantity() == 0) {
                System.out.printf(Message.PRINT_NO_QUANTITY_LIST.getMessage(), product.getName(), priceFormatted);
            } else {
                String quantityFormatted = formatter.format(product.getQuantity());
                System.out.printf(Message.DEFAULT_PRINT_PRODUCT_LIST.getMessage(), product.getName(), priceFormatted, quantityFormatted);
            }

            if (product.getPromotion() != null) {
                System.out.print(Message.SPACE.getMessage() + product.getPromotion());
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printReceipt(List<ProductDisplayDTO> items, Integer totalAmount, Integer eventDiscount, Integer membershipDiscount, Integer finalAmount) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.KOREA);

        System.out.println(Message.WOOWA_CONVENIENCE_STORE.getMessage());
        System.out.println(Message.RECEIPT_HEADER.getMessage());
        for (ProductDisplayDTO item : items) {
            String itemTotalPrice = formatter.format(item.getPrice() * item.getQuantity());
            System.out.printf(Message.RECEIPT_BODY.getMessage(), item.getName(), item.getQuantity(), itemTotalPrice);
        }
        System.out.println(Message.RECEIPT_PROMOTION_HEADER.getMessage());
        // 증정 상품을 출력하는 코드 추가 가능 (필요 시)

        System.out.println(Message.RECEIPT_DIVIDE_LINE.getMessage());
        System.out.printf(Message.RECEIPT_TOTAL_AMOUNT.getMessage(), items.size(), formatter.format(totalAmount));
        System.out.printf(Message.RECEIPT_EVENT_DISCOUNT.getMessage(), formatter.format(eventDiscount));
        System.out.printf(Message.RECEIPT_MEMBERSHIP_DISCOUNT.getMessage(), formatter.format(membershipDiscount));
        System.out.printf(Message.RECEIPT_FINAL_AMOUNT.getMessage(), formatter.format(finalAmount));
    }

    public void printGoodbyeMessage() {
        System.out.println(Message.GOODBYE_MESSAGE.getMessage());
    }
}
