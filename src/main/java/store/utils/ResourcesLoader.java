package store.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import store.constant.Constants;
import store.constant.ErrorMessage;
import store.model.Product;

public class ResourcesLoader {
    // 파일을 읽어와 ProductLoaderDTO 객체 리스트로 반환
    public static List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(Constants.PRODUCTS_FILE_PATH))) {
            br.readLine();
            readLinesAndAddToProducts(br, products);
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    ErrorMessage.DEFAULT_HEADER_MESSAGE.getMessage() + ErrorMessage.FAILED_LOAD_FILE.getMessage());
        }
        return products;
    }

    private static void readLinesAndAddToProducts(BufferedReader br, List<Product> products)
            throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            Product product = parseProductsLine(line);
            if (product != null) {
                products.add(product);
            }
        }
    }

    private static Product parseProductsLine(String line) {
        String[] values = splitLine(line);
        if (values.length == 4) {
            String name = values[0];
            int price = parseValues(values[1]);
            int quantity = parseValues(values[2]);
            String promotion = parseNullValue(values[3]);
            return new Product(name, price, quantity, promotion);
        }
        return null; // 데이터가 4개가 아닐 경우 null 반환
    }

    private static String[] splitLine(String line) {
        return line.split(Constants.DELIMITER);
    }

    private static int parseValues(String string) {
        return Integer.parseInt(string);
    }

    private static String parseNullValue(String value) {
        if (Constants.NULL.equals(value)) {
            return null;
        }
        return value;
    }

    public static List<PromotionsLoaderDTO> loadPromotions() {
        List<PromotionsLoaderDTO> promotions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(Constants.PROMOTIONS_FILE_PATH))) {
            br.readLine();
            readLinesAndAddToPromotions(br, promotions);
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    ErrorMessage.DEFAULT_HEADER_MESSAGE.getMessage() + ErrorMessage.FAILED_LOAD_FILE.getMessage());
        }
        return promotions;
    }

    private static void readLinesAndAddToPromotions(BufferedReader br, List<PromotionsLoaderDTO> promotions)
            throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            PromotionsLoaderDTO dto = parsePromotionsLine(line);
            if (dto != null) {
                promotions.add(dto);
            }
        }
    }

    private static PromotionsLoaderDTO parsePromotionsLine(String line) {
        String[] values = splitLine(line);
        if (values.length == 5) {
            String PromotionName = values[0];
            int buyQuantity = parseValues(values[1]);
            int freeQuantity = parseValues(values[2]);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(values[3], formatter);
            LocalDate endDate = LocalDate.parse(values[4], formatter);
            return new PromotionsLoaderDTO(PromotionName, buyQuantity, freeQuantity, startDate, endDate);
        }
        return null; // 데이터가 4개가 아닐 경우 null 반환
    }
    //name,buy,get,start_date,end_date
}
