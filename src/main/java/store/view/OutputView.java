package store.view;

import java.util.List;
import java.util.Objects;
import store.constant.Constants;
import store.constant.Message;
import store.dto.ProductDisplayDTO;

public class OutputView {
    public void printWelcomeMessage() {
        System.out.println(Message.OUTPUT_WELCOME_MESSAGE.getMessage());
    }

    public void printOurProducts() {
        System.out.println(Message.OUTPUT_OUR_PRODUCTS.getMessage());
    }

    public static void printCurrentProducts(List<ProductDisplayDTO> products) {
        for (ProductDisplayDTO product : products) {
            String productInfo = formatProductInfo(product);
            System.out.println(productInfo);
        }
    }

    private static String formatProductInfo(ProductDisplayDTO product) {

        String name = product.getName();
        int price = product.getPrice();
        int quantity = product.getQuantity();
        String promotion = product.getPromotion();

        if (quantity == 0) {
            return String.format(Constants.NO_QUANTITY, name, price);
        }
        if (!Objects.equals(promotion, null)) {
            return String.format(Constants.HAVE_PROMOTION, name, price, quantity, promotion);
        }
        return String.format(Constants.NO_PROMOTION, name, price, quantity);
    }
}
