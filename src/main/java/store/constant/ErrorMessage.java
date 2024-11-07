package store.constant;

public enum ErrorMessage {

    DEFAULT_HEADER_MESSAGE("[Error] "),
    FAILED_LOAD_FILE("데이터 로딩에 실패했습니다."),
    TOO_MANY_QUANTITY("수량이 모자랍니다.");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
