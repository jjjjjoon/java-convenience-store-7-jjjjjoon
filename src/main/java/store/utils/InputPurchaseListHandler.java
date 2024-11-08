package store.utils;

import java.util.ArrayList;
import java.util.List;
import store.constant.Constants;
import store.constant.ErrorMessage;

/*
if문 안에 예외처리 수정 ㅇ
replace및 구분자 상수화d
input~~handler와 상호 작용할 수 있는 validator생성
상품과 수량 구매에 대한 비즈니스 로직 생성
팔리고 난 후 모델 상태 관리
Retry 클래스 생성 후 예외 처리 하고나서도 다시 재시작할 수 있게
 */
public class InputPurchaseListHandler {
    public List<String> parsePurchaseItem(String input) {
        // 입력 예시: "[감자칩-1]"
        input = input.replace(Constants.OPEN_BRACKET, Constants.REPLACE_BLANK)
                .replace(Constants.CLOSE_BRACKET, Constants.REPLACE_BLANK); // 대괄호 제거
        String[] parts = input.split(Constants.INPUT_DELIMITER); // '-'를 기준으로 문자열 분리

        if (parts.length != 2) {
            throw new IllegalArgumentException(
                    ErrorMessage.DEFAULT_HEADER_MESSAGE.getMessage() + ErrorMessage.INVALID_INPUT_TYPE.getMessage());
        }

        List<String> result = new ArrayList<>();
        result.add(parts[0].trim()); // 상품명 추가
        result.add(parts[1].trim()); // 수량 추가

        return result; // ["name", "quantity"] 형태의 ArrayList 반환
    }
}
