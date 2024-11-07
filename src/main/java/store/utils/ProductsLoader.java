package store.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import store.constant.Constants;
import store.dto.ProductsLoaderDTO;

public class ProductsLoader {
    // 파일을 읽어와 ProductLoaderDTO 객체 리스트로 반환
    public static List<ProductsLoaderDTO> loadProducts() {
        List<ProductsLoaderDTO> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(Constants.FILE_PATH))) {
            readLinesAndAddToProducts(br, products);
        } catch (IOException e) {
//            System.err.println("Error reading products file: " + e.getMessage());
            throw new IllegalArgumentException("데이터 로딩 실패했어여!!!!");
        }
        return products;
    }

    private static void readLinesAndAddToProducts(BufferedReader br, List<ProductsLoaderDTO> products) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            ProductsLoaderDTO dto = parseProductsLine(line);
            if (dto != null) {
                products.add(dto);
            }
        }
    }

    private static ProductsLoaderDTO parseProductsLine(String line) {
        String[] values = splitProductsLine(line);
        if (values.length == 4) {
            String name = values[0];
            int price = Integer.parseInt(values[1]);
            int quantity = Integer.parseInt(values[2]);
            String promotion = parseNullValue(values[3]);
            return new ProductsLoaderDTO(name, price, quantity, promotion);
        }
        return null; // 데이터가 4개가 아닐 경우 null 반환
    }

    private static String[] splitProductsLine(String line) {
        return line.split(",");
    }

    private static String parseNullValue(String value) {
        if ("null".equals(value)) {
            return null;
        }
        return value;
    }

}
