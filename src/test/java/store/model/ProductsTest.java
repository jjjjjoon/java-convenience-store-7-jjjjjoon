package store.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ProductsTest {

    private Products products;

    @BeforeEach
    void setUp() {
        products = new Products();
    }

    @Nested
    @DisplayName("loadProducts 메소드 테스트")
    class LoadProductsTest {

        @Test
        @DisplayName("제품 목록이 비어 있지 않아야 함")
        void shouldLoadProductsSuccessfully() {
            assertThat(products.getAllProducts()).isNotEmpty();
        }
    }

    @Nested
    @DisplayName("findProductByName 메소드 테스트")
    class FindProductByNameTest {

        @ParameterizedTest(name = "제품명 {0}으로 제품 찾기")
        @CsvSource({"콜라"})
        @DisplayName("정상적으로 제품 찾기")
        void shouldFindProductByName(String productName) {
            Optional<Product> product = products.findProductByName(productName);

            assertThat(product).isPresent();
            assertThat(product.get().getName()).isEqualTo(productName);
        }

        @Test
        @DisplayName("존재하지 않는 제품에 대한 Optional.empty 반환")
        void shouldReturnEmptyOptionalForNonExistingProduct() {
            Optional<Product> product = products.findProductByName("없는제품");

            assertThat(product).isEmpty();
        }
    }
}

