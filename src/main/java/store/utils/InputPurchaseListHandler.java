package store.utils;

import java.util.ArrayList;
import java.util.List;
import store.constant.Constants;
/*
if문 안에 예외처리 수정 ㅇ
replace및 구분자 상수화 ㅇ
input~~handler와 상호 작용할 수 있는 validator생성 ㅇ
상품과 수량 구매에 대한 비즈니스 로직 생성
팔리고 난 후 모델 상태 관리
Retry 클래스 생성 후 예외 처리 하고나서도 다시 재시작할 수 있게 -> retry구현 완. controller에서 적용
 */
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
