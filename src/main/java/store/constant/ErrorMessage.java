package store.constant;

public enum ErrorMessage {

    DEFAULT_HEADER_MESSAGE("[Error] "),
    FAILED_LOAD_FILE("데이터 로딩에 실패했습니다."),
    TOO_MANY_QUANTITY("수량이 모자랍니다."),
    INVALID_INPUT_TYPE("형식은 [상품명-수량]이어야 합니다."),
    INVALID_BRACKET("입력에 대괄호가 올바르게 포함되지 않았습니다."),
    INVALID_BLANK_NAME("상품명이 비어 있거나 공백입니다."),
    INVALID_BLANK_QUANTITY("수량이 비어 있거나 공백입니다."),
    INVALID_POSITIVE_NUMBER("수량은 양의 정수여야 합니다."),
    INVALID_NUMBER_TYPE("수량이 유효한 숫자가 아닙니다."),
    INVALID_MULTI_PURCHASE_DELIMITER("복수 항목은 콤마(,)로 구분되어야 합니다."),
    INVALID_PRODUCT_NAME("존재하지 않는 상품입니다.");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
