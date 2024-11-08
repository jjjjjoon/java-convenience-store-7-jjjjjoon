package store.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputPurchaseListValidatorTest {

    private final InputPurchaseListValidator validator = new InputPurchaseListValidator();

    @Nested
    @DisplayName("유효하지 않은 값 입력")
    class InvalidInputTests {

        @ParameterizedTest
        @DisplayName("잘못된 상품 형식에 대한 예외 메시지 확인")
        @CsvSource({
                "'사이다2', '입력에 대괄호가 올바르게 포함되지 않았습니다.'",
                "'콜라:3', '입력에 대괄호가 올바르게 포함되지 않았습니다.'",
                "'[물]', '형식은 [상품명-수량]이어야 합니다.'",
                "'[ -1]', '상품명이 비어 있거나 공백입니다.'",
                "'[맥주-a]', '형식은 [상품명-수량]이어야 합니다.'"
        })
        void testInvalidInputFormatsWithMessages(String input, String expectedMessage) {
            assertThatThrownBy(() -> validator.validate(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(expectedMessage);
        }
    }
}
