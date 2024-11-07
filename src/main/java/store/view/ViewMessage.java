package store.view;

public enum ViewMessage {
    INPUT_NAME_NUMBER("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    OUTPUT_WELCOME_MESSAGE("안녕하세요. 우아한 편의점입니다."),
    OUTPUT_OUR_PRODUCTS("현재 보유하고 있는 상품입니다.");

    private String message;

    ViewMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
