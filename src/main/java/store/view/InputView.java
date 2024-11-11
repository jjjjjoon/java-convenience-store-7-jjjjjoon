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
        System.out.println("구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return Console.readLine();
    }

    public String getAdditionalPurchaseResponse(int neededQuantity, String productName) {
        System.out.printf("%s 상품을 %d개 더 구매하시면 추가 혜택을 받을 수 있습니다. 추가 구매하시겠습니까? (Y/N)%n", productName, neededQuantity);
        return Console.readLine();
    }

    public String getExceedPromotionPurchaseResponse(String productName, int nonPromoQuantity) {
        System.out.printf("%s 상품 중 %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n", productName, nonPromoQuantity);
        return Console.readLine();
    }
}
