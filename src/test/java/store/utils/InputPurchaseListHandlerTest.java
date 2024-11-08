package store.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.util.List;

class InputPurchaseListHandlerTest {

    private final InputPurchaseListHandler handler = new InputPurchaseListHandler();

    @Nested
    @DisplayName("유효한 값 입력")
    class ValidInputTests {

        @Test
        @DisplayName("올바른 단일 상품 입력")
        void testSingleValidInput() {
            String input = "[사이다-2]";
            List<String[]> productList = handler.parseInput(input);

            assertThat(productList).hasSize(1);
            assertThat(productList.get(0)[0]).isEqualTo("사이다");
            assertThat(Integer.parseInt(productList.get(0)[1])).isEqualTo(2);
        }

        @ParameterizedTest
        @DisplayName("올바른 여러 상품 입력")
        @ValueSource(strings = {"[사이다-2], [콜라-3]", "[물-1], [맥주-5], [감자칩-1]"})
        void testMultipleValidInputs(String input) {
            List<String[]> productList = handler.parseInput(input);
            assertThat(productList).isNotEmpty();
            assertThat(productList.size()).isGreaterThan(1);
        }
    }

    @Nested
    @DisplayName("유효하지 않은 값 입력")
    class InvalidInputTests {

        @ParameterizedTest
        @DisplayName("잘못된 상품 형식에 대한 예외 메시지 확인")
        @CsvSource({
                "'[사이다2]', '형식은 [상품명-수량]이어야 합니다.'",
                "'[콜라:3]', '형식은 [상품명-수량]이어야 합니다.'",
                "'[음료- ]', '수량이 비어 있거나 공백입니다.'",
                "'[ -2]', '상품명이 비어 있거나 공백입니다.'",
                "'[맥주-a]', '형식은 [상품명-수량]이어야 합니다.'",
        })
        void testInvalidInputFormatsWithMessages(String input, String expectedMessage) {
            assertThatThrownBy(() -> handler.parseInput(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(expectedMessage);
        }

        @Test
        @DisplayName("대괄호 누락 시 예외 발생")
        void testMissingBrackets() {
            String input = "사이다-2, 콜라-3";
            assertThatThrownBy(() -> handler.parseInput(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("대괄호");
        }
    }
}
