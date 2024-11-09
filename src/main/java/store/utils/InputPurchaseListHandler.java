package store.utils;

import java.util.ArrayList;
import java.util.List;
import store.constant.Constants;

public class InputPurchaseListHandler {

    private final InputPurchaseListValidator validator = new InputPurchaseListValidator();

    public List<String[]> parseInput(String input) {
        List<String[]> productList = new ArrayList<>();

        String[] items = input.split(Constants.INPUT_SPLIT_REGEX);
        for (String item : items) {
            validator.validate(item);

            String name = extractProductName(item);
            Integer quantity = extractQuantity(item);

            productList.add(new String[]{name, String.valueOf(quantity)});
        }

        return productList;
    }

    private String extractProductName(String item) {
        Integer hyphenIndex = item.indexOf(Constants.INPUT_DELIMITER);
        return item.substring(1, hyphenIndex).trim();
    }

    private Integer extractQuantity(String item) {
        Integer hyphenIndex = item.indexOf(Constants.INPUT_DELIMITER);
        Integer endBracketIndex = item.indexOf(Constants.CLOSE_BRACKET);
        return Integer.parseInt(item.substring(hyphenIndex + 1, endBracketIndex).trim());
    }
}
