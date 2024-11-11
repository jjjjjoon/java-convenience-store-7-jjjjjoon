package store.view;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import store.constant.Message;
import store.dto.ProductDisplayDTO;

public class OutputView {

    private final NumberFormat formatter = NumberFormat.getInstance(Locale.KOREA);

    public void printWelcomeMessage() {
        System.out.println(Message.OUTPUT_WELCOME_MESSAGE.getMessage());
        System.out.println(Message.OUTPUT_OUR_PRODUCTS.getMessage());
        System.out.println();
    }

    public void printCurrentProducts(List<ProductDisplayDTO> products) {
        products.forEach(this::printProduct);
        System.out.println();
    }

    private void printProduct(ProductDisplayDTO product) {
        String priceFormatted = formatter.format(product.getPrice());

        if (product.getQuantity() == 0) {
            printNoQuantityProduct(product, priceFormatted);
            return;
        }

        printProductWithQuantity(product, priceFormatted);
    }

    private void printNoQuantityProduct(ProductDisplayDTO product, String priceFormatted) {
        System.out.printf(Message.PRINT_NO_QUANTITY_LIST.getMessage(), product.getName(), priceFormatted);
    }

    private void printProductWithQuantity(ProductDisplayDTO product, String priceFormatted) {
        String quantityFormatted = formatter.format(product.getQuantity());
        System.out.printf(Message.DEFAULT_PRINT_PRODUCT_LIST.getMessage(), product.getName(), priceFormatted, quantityFormatted);

        if (product.getPromotion() != null) {
            printPromotion(product);
        }
        System.out.println();
    }

    private void printPromotion(ProductDisplayDTO product) {
        System.out.print(Message.SPACE.getMessage() + product.getPromotion());
    }

    public void printReceipt(List<ProductDisplayDTO> items, Integer totalAmount, Integer eventDiscount, Integer membershipDiscount, Integer finalAmount) {
        System.out.println(Message.WOOWA_CONVENIENCE_STORE.getMessage());
        System.out.println(Message.RECEIPT_HEADER.getMessage());

        items.forEach(this::printReceiptItem);

        System.out.println(Message.RECEIPT_PROMOTION_HEADER.getMessage());
        System.out.println(Message.RECEIPT_DIVIDE_LINE.getMessage());

        printReceiptSummary(items.size(), totalAmount, eventDiscount, membershipDiscount, finalAmount);
    }

    private void printReceiptItem(ProductDisplayDTO item) {
        String itemTotalPrice = formatter.format(item.getPrice() * item.getQuantity());
        System.out.printf(Message.RECEIPT_BODY.getMessage(), item.getName(), item.getQuantity(), itemTotalPrice);
    }

    private void printReceiptSummary(int itemCount, Integer totalAmount, Integer eventDiscount, Integer membershipDiscount, Integer finalAmount) {
        System.out.printf(Message.RECEIPT_TOTAL_AMOUNT.getMessage(), itemCount, formatter.format(totalAmount));
        System.out.printf(Message.RECEIPT_EVENT_DISCOUNT.getMessage(), formatter.format(eventDiscount));
        System.out.printf(Message.RECEIPT_MEMBERSHIP_DISCOUNT.getMessage(), formatter.format(membershipDiscount));
        System.out.printf(Message.RECEIPT_FINAL_AMOUNT.getMessage(), formatter.format(finalAmount));
    }

    public void printGoodbyeMessage() {
        System.out.println(Message.GOODBYE_MESSAGE.getMessage());
    }
}