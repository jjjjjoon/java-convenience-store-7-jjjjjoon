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
import store.dto.ProductsLoaderDTO;
import store.dto.PromotionsLoaderDTO;

public class ResourcesLoader {
    public static List<ProductsLoaderDTO> loadProducts() {
        List<ProductsLoaderDTO> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(Constants.PRODUCTS_FILE_PATH))) {
            br.readLine();
            readLinesAndAddToProducts(br, products);
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    ErrorMessage.DEFAULT_HEADER_MESSAGE.getMessage() + ErrorMessage.FAILED_LOAD_FILE.getMessage());
        }
        return products;
    }

    private static void readLinesAndAddToProducts(BufferedReader br, List<ProductsLoaderDTO> products)
            throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            ProductsLoaderDTO product = parseProductsLine(line);
            if (product != null) {
                products.add(product);
            }
        }
    }

    private static ProductsLoaderDTO parseProductsLine(String line) {
        String[] values = splitLine(line);
        if (values.length == 4) {
            String name = values[0];
            Integer price = parseValues(values[1]);
            Integer quantity = parseValues(values[2]);
            String promotion = parseNullValue(values[3]);
            return new ProductsLoaderDTO(name, price, quantity, promotion);
        }
        return null;
    }

    private static String[] splitLine(String line) {
        return line.split(Constants.DELIMITER);
    }

    private static Integer parseValues(String string) {
        return Integer.parseInt(string);
    }

    private static String parseNullValue(String value) {
        if (Constants.NULL.equals(value)) {
            return null;
        }
        return value;
    }

    public static List<PromotionsLoaderDTO> loadPromotions() {
        List<PromotionsLoaderDTO> promotionsLoaderDTO = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(Constants.PROMOTIONS_FILE_PATH))) {
            br.readLine();
            readLinesAndAddToPromotions(br, promotionsLoaderDTO);
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    ErrorMessage.DEFAULT_HEADER_MESSAGE.getMessage() + ErrorMessage.FAILED_LOAD_FILE.getMessage());
        }
        return promotionsLoaderDTO;
    }

    private static void readLinesAndAddToPromotions(BufferedReader br, List<PromotionsLoaderDTO> promotions)
            throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            PromotionsLoaderDTO promotionsLoaderDTO = parsePromotionsLine(line);
            if (promotionsLoaderDTO != null) {
                promotions.add(promotionsLoaderDTO);
            }
        }
    }

    private static PromotionsLoaderDTO parsePromotionsLine(String line) {
        String[] values = splitLine(line);
        if (values.length == 5) {
            String PromotionName = values[0];
            Integer buyQuantity = parseValues(values[1]);
            Integer freeQuantity = parseValues(values[2]);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(values[3], formatter);
            LocalDate endDate = LocalDate.parse(values[4], formatter);
            return new PromotionsLoaderDTO(PromotionName, buyQuantity, freeQuantity, startDate, endDate);
        }
        return null;
    }
}
