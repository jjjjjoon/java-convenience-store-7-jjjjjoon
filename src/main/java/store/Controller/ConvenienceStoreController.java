package store.Controller;

import store.constant.ErrorMessage;
import store.dto.DiscountInfoDTO;
import store.dto.ProductDisplayDTO;
import store.model.Product;
import store.model.Products;
import store.model.PromotionProduct;
import store.utils.InputPurchaseListHandler;
import store.utils.InputPurchaseListValidator;
import store.utils.InputSupplier;
import store.utils.ResourcesLoader;
import store.view.InputView;
import store.view.OutputView;
import store.service.MembershipDiscount;

import java.util.List;

public class ConvenienceStoreController {

    private final Products products;
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final InputPurchaseListHandler purchaseListHandler = new InputPurchaseListHandler();
    private final InputPurchaseListValidator validator = new InputPurchaseListValidator();
    private final MembershipDiscount membershipDiscount = new MembershipDiscount();

    public ConvenienceStoreController() {
        List productsData = ResourcesLoader.loadProducts();
        this.products = new Products(productsData);
    }

    public void run() {
        outputView.printWelcomeMessage();
        outputView.printCurrentProducts(products.getProductDisplayList());

        retryUntilValid(() -> {
            List<String[]> parsedItems = getParsedItems();
            processPurchase(parsedItems);
            return null;
        });
    }

    private <T> T retryUntilValid(InputSupplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(ErrorMessage.DEFAULT_HEADER_MESSAGE.getMessage() + e.getMessage());
            }
        }
    }

    private List<String[]> getParsedItems() {
        return retryUntilValid(() -> {
            String userInput = inputView.getNameAndQuantity();
            validator.validate(userInput);
            return purchaseListHandler.parseInput(userInput);
        });
    }

    private void processPurchase(List<String[]> parsedItems) {
        parsedItems.forEach(this::processItem);
        applyMembershipDiscount(parsedItems);
        printReceipt(parsedItems);
        handleRepeatPurchase(parsedItems);
    }

    private void processItem(String[] item) {
        String productName = item[0];
        Integer quantity = Integer.parseInt(item[1]);

        Product product = products.findProductByName(productName)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_PRODUCT_NAME.getMessage()));

        if (product instanceof PromotionProduct) {
            handlePromotionProduct((PromotionProduct) product, quantity);
        } else {
            product.sell(quantity);
        }
    }

    private void handlePromotionProduct(PromotionProduct promotionProduct, Integer quantity) {
        Integer promoStockUsed = promotionProduct.calculatePromotionDiscount(quantity);
        Integer remainingQuantity = quantity - promoStockUsed;

        handlePromotionQuantity(promotionProduct, remainingQuantity, quantity, promoStockUsed);
    }

    private void handlePromotionQuantity(PromotionProduct promotionProduct, Integer remainingQuantity, Integer quantity, Integer promoStockUsed) {
        if (isPartialPromotion(promotionProduct, remainingQuantity)) {
            quantity += getAdditionalPromotionQuantity(promotionProduct, remainingQuantity);
        }

        if (remainingQuantity > promotionProduct.getPromotionStock()) {
            handleExceedPromotionStock(promotionProduct, remainingQuantity, promoStockUsed);
        } else {
            promotionProduct.sell(promoStockUsed);
        }
    }

    private boolean isPartialPromotion(PromotionProduct promotionProduct, Integer remainingQuantity) {
        return remainingQuantity > 0 && remainingQuantity < promotionProduct.getBuyQuantity();
    }

    private int getAdditionalPromotionQuantity(PromotionProduct promotionProduct, Integer remainingQuantity) {
        int neededQuantity = promotionProduct.getBuyQuantity() - remainingQuantity;
        String addPromoResponse = inputView.getAdditionalPurchaseResponse(neededQuantity, promotionProduct.getName());
        return addPromoResponse.equalsIgnoreCase("Y") ? neededQuantity : 0;
    }

    private void handleExceedPromotionStock(PromotionProduct promotionProduct, Integer remainingQuantity, Integer promoStockUsed) {
        int nonPromoQuantity = remainingQuantity - promotionProduct.getPromotionStock();
        String exceedPromoResponse = inputView.getExceedPromotionPurchaseResponse(promotionProduct.getName(), nonPromoQuantity);
        if (exceedPromoResponse.equalsIgnoreCase("Y")) {
            promotionProduct.sell(promoStockUsed);
            promotionProduct.sell(nonPromoQuantity);
        } else {
            throw new IllegalArgumentException(ErrorMessage.CANCEL_PURCHASE.getMessage());
        }
    }

    private void applyMembershipDiscount(List<String[]> parsedItems) {
        Integer totalAmount = products.calculateTotalAmount(parsedItems);
        DiscountInfoDTO discountInfo = new DiscountInfoDTO(totalAmount);
        String membershipResponse = inputView.getMembershipDiscountInput();
        if (membershipResponse.equalsIgnoreCase("Y")) {
            Integer membershipDiscountAmount = membershipDiscount.calculateDiscount(totalAmount);
            discountInfo.applyMembershipDiscount(membershipDiscountAmount);
        }
    }

    private void printReceipt(List<String[]> parsedItems) {
        List<ProductDisplayDTO> displayItems = products.convertToDisplayDTO(parsedItems);
        DiscountInfoDTO discountInfo = new DiscountInfoDTO(products.calculateTotalAmount(parsedItems));
        Integer finalAmount = discountInfo.getFinalAmount();
        outputView.printReceipt(displayItems, discountInfo.getTotalAmount(), discountInfo.getEventDiscountAmount(),
                discountInfo.getMembershipDiscountAmount(), finalAmount);
    }

    private void handleRepeatPurchase(List<String[]> parsedItems) {
        String repeatPurchaseResponse = inputView.getRepeatPurchaseResponse();
        if (repeatPurchaseResponse.equalsIgnoreCase("N")) {
            outputView.printGoodbyeMessage();
            return;
        }
        outputView.printCurrentProducts(products.getProductDisplayList());
        List<String[]> newParsedItems = getParsedItems();
        processPurchase(newParsedItems);
    }
}
