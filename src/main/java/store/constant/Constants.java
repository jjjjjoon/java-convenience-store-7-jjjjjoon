package store.constant;

public final class Constants {
    public static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";
    public static final String PROMOTIONS_FILE_PATH = "src/main/resources/promotions.md";
    public static final String DELIMITER = ",";
    public static final String NULL = "null";
    public static final String OPEN_BRACKET = "[";
    public static final String CLOSE_BRACKET = "]";
    public static final String INPUT_DELIMITER = "-";
    public static final String INPUT_SPLIT_REGEX = ",\\s*";
    public static final String VALIDATE_COMPILE_PATTERN = "\\[(.+?)-([0-9]+)\\]";
    public static final String EMPTY_NAME_PATTERN = "\\[ -[0-9]+\\]";
    public static final String BLANK_QUANTITY_PATTERN = "\\[[^\\]]+- ]";
    public static final String EMPTY_QUANTITY_PATTERN = "\\[[^\\]]+-\\]";
    public static final String INVALID_MULTI_PURCHASE_PATTERN = "][";
    public static final String VALID_MULTI_PURCHASE_PATTERN = "],[";
    public static final Double DISCOUNT_RATE = 0.3;
    public static final Integer MAX_DISCOUNT_AMOUNT = 8000;

    private Constants() {
    }
}
