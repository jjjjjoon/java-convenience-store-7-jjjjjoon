package store.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.constant.Constants;
import store.constant.ErrorMessage;

public class InputPurchaseListValidator {

    private static final Pattern PATTERN = Pattern.compile(Constants.VALIDATE_COMPILE_PATTERN);

    public void validate(String input) {
        checkBracketFormat(input);
        checkQuantity(input);
        checkProductNameAndQuantity(input);
        checkFormat(input);
        checkMultipleItemSeparator(input);
    }

    private void checkBracketFormat(String input) {
        if (!input.startsWith(Constants.OPEN_BRACKET) || !input.endsWith(Constants.CLOSE_BRACKET)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_BRACKET.getMessage());
        }
    }

    private void checkFormat(String input) {
        Matcher matcher = PATTERN.matcher(input);
        if (!matcher.find()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT_TYPE.getMessage());
        }
    }

    private void checkProductNameAndQuantity(String input) {
        String[] items = input.split(Constants.DELIMITER);
        for (String item : items) {
            if (item.matches(Constants.EMPTY_NAME_PATTERN)) {
                throw new IllegalArgumentException(ErrorMessage.INVALID_BLANK_NAME.getMessage());
            }
            if (item.matches(Constants.BLANK_QUANTITY_PATTERN) || item.matches(Constants.EMPTY_QUANTITY_PATTERN)) {
                throw new IllegalArgumentException(ErrorMessage.INVALID_BLANK_QUANTITY.getMessage());
            }
        }
    }

    private void checkQuantity(String input) {
        Matcher matcher = PATTERN.matcher(input);
        while (matcher.find()) {
            String quantityStr = matcher.group(2);
            try {
                Integer quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    throw new IllegalArgumentException(ErrorMessage.INVALID_POSITIVE_NUMBER.getMessage());
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(ErrorMessage.INVALID_NUMBER_TYPE.getMessage());
            }
        }
    }

    private void checkMultipleItemSeparator(String input) {
        if (input.contains(Constants.INVALID_MULTI_PURCHASE_PATTERN) && !input.contains(Constants.VALID_MULTI_PURCHASE_PATTERN)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MULTI_PURCHASE_DELIMITER.getMessage());
        }
    }
}
