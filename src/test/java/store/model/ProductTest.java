package store.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductTest {

    @Nested
    @DisplayName("Product 초기화 테스트")
    class ProductInitializationTest {

        @ParameterizedTest(name = "{index}: {0} 이름으로 초기화")
        @CsvSource({"콜라, 1000, 10, 탄산2+1"})
        @DisplayName("정상적인 초기화 테스트")
        void shouldInitializeProduct(String name, int price, int quantity, String promotion) {
            Product product = new Product(name, price, quantity, promotion);

            assertThat(product.getName()).isEqualTo(name);
            assertThat(product.getPrice()).isEqualTo(price);
            assertThat(product.getQuantity()).isEqualTo(quantity);
            assertThat(product.getPromotion()).isEqualTo(promotion);
        }
    }

    @Nested
    @DisplayName("sell 메소드 테스트")
    class SellMethodTest {

        @Test
        @DisplayName("수량 감소 테스트")
        void shouldReduceQuantityWhenSelling() {
            Product product = new Product("콜라", 1000, 10, null);

            product.sell(3);

            assertThat(product.getQuantity()).isEqualTo(7);
        }

        @ParameterizedTest(name = "수량 부족으로 예외 발생: {0}")
        @CsvSource({"3", "5", "10"})
        @DisplayName("수량 부족 예외 테스트")
        void shouldThrowExceptionWhenInsufficientQuantity(int quantityToSell) {
            Product product = new Product("콜라", 1000, 2, null);

            assertThatThrownBy(() -> product.sell(quantityToSell))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("수량이 모자랍니다.");
        }
    }
}
