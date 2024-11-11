package store.constant;

public enum Message {
    INPUT_NAME_NUMBER("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    OUTPUT_WELCOME_MESSAGE("안녕하세요. 우아한 편의점입니다."),
    OUTPUT_OUR_PRODUCTS("현재 보유하고 있는 상품입니다."),
    INPUT_MEMBERSHIP_DISCOUNT("멤버십 할인을 받으시겠습니까? (Y/N)"),
    REPEAT_PURCHASE_RESPONSE("구매하고 싶은 다른 상품이 있나요? (Y/N)"),
    ADDITIONAL_PURCHASE_RESPONSE("%s 상품을 %d개 더 구매하시면 추가 혜택을 받을 수 있습니다. 추가 구매하시겠습니까? (Y/N)%n"),
    EXCEED_PROMOTION_PURCHASE_RESPONSE("%s 상품 중 %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n"),
    WOOWA_CONVENIENCE_STORE("\"===========우아한 편의점=============\""),
    RECEIPT_HEADER("\"상품명\\t\\t수량\\t금액\""),
    RECEIPT_BODY("%s\t\t%d\t%s원%n"),
    RECEIPT_PROMOTION_HEADER("===========증정============="),
    RECEIPT_DIVIDE_LINE("=============================="),
    RECEIPT_TOTAL_AMOUNT("총구매액\t\t%d\t%s원%n"),
    RECEIPT_EVENT_DISCOUNT("행사할인\t\t\t-%s원%n"),
    RECEIPT_MEMBERSHIP_DISCOUNT("멤버십할인\t\t\t-%s원%n"),
    RECEIPT_FINAL_AMOUNT("내실돈\t\t\t%s원%n"),
    RECEIPT_GIFT_BODY("%s\t\t%d개\n"),
    GOODBYE_MESSAGE("감사합니다. 다음에 또 방문해 주세요!"),
    PRINT_NO_QUANTITY_LIST("- %s %s원 재고 없음"),
    DEFAULT_PRINT_PRODUCT_LIST("- %s %s원 %s개"),
    SPACE(" ");


    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
