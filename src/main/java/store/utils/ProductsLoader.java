package store.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import store.constant.Constants;

public class ProductsLoader {
    // 파일을 읽어와 ProductLoaderDTO 객체 리스트로 반환
    public static List<ProductLoaderDTO> loadProducts() {
        List<ProductLoaderDTO> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(Constants.FILE_PATH))) {
            readLinesAndAddToProducts(br, products);
        } catch (IOException e) {
            System.err.println("Error reading products file: " + e.getMessage());
        }
        return products;
    }

    private static void readLinesAndAddToProducts(BufferedReader br, List<ProductLoaderDTO> products) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            ProductLoaderDTO dto = parseLineToDTO(line);
            if (dto != null) {
                products.add(dto);
            }
        }
    }

    private static ProductLoaderDTO parseLineToDTO(String line) {
        String[] values = line.split(",");
        if (values.length == 4) {
            String name = values[0];
            int price = Integer.parseInt(values[1]);
            int quantity = Integer.parseInt(values[2]);
            String promotion = values[3].equals("null") ? null : values[3];
            return new ProductLoaderDTO(name, price, quantity, promotion);
        }
        return null; // 데이터가 4개가 아닐 경우 null 반환
    }
}
