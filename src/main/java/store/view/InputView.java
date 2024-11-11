package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.constant.Message;

public class InputView {

    public String getNameAndQuantity() {
        System.out.println(Message.INPUT_NAME_NUMBER.getMessage());
        return Console.readLine();
    }

    public static String getMembershipDiscountInput() {
        System.out.println(Message.INPUT_MEMBERSHIP_DISCOUNT.getMessage());
        String input = Console.readLine(); // 사용자 입력
        return input;
    }

    public String getRepeatPurchaseResponse() {
        System.out.println(Message.REPEAT_PURCHASE_RESPONSE.getMessage());
        return Console.readLine();
    }

    public String getAdditionalPurchaseResponse(int neededQuantity, String productName) {
        System.out.printf(Message.ADDITIONAL_PURCHASE_RESPONSE.getMessage(), productName, neededQuantity);
        return Console.readLine();
    }

    public String getExceedPromotionPurchaseResponse(String productName, int nonPromoQuantity) {
        System.out.printf(Message.EXCEED_PROMOTION_PURCHASE_RESPONSE.getMessage(), productName, nonPromoQuantity);
        return Console.readLine();
    }
}
