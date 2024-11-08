package store.utils;

import java.util.ArrayList;
import java.util.List;
import store.constant.Constants;
import store.utils.InputPurchaseListValidator;

public class InputPurchaseListHandler {

    private final InputPurchaseListValidator validator = new InputPurchaseListValidator();

    public List<String[]> parseInput(String input) {
        List<String[]> productList = new ArrayList<>();

        String[] items = input.split(Constants.INPUT_SPLIT_REGEX);
        for (String item : items) {
            validator.validate(item);

            String name = extractProductName(item);
            int quantity = extractQuantity(item);

            productList.add(new String[]{name, String.valueOf(quantity)});
        }

        return productList;
    }

    private String extractProductName(String item) {
        int hyphenIndex = item.indexOf(Constants.INPUT_DELIMITER);
        return item.substring(1, hyphenIndex).trim();
    }

    private int extractQuantity(String item) {
        int hyphenIndex = item.indexOf(Constants.INPUT_DELIMITER);
        int endBracketIndex = item.indexOf(Constants.CLOSE_BRACKET);
        return Integer.parseInt(item.substring(hyphenIndex + 1, endBracketIndex).trim());
    }
}
