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
import java.util.Optional;

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
        // Step 1-3: 파일 로드 및 환영 메시지 출력, 재고 상태 출력
        outputView.printWelcomeMessage();
        outputView.printCurrentProducts(products.getProductDisplayList());

        // Step 4-10: 전체 로직을 재시도 가능하도록 묶기
        retryUntilValid(() -> {
            List<String[]> parsedItems = retryUntilValid(() -> {
                String userInput = inputView.getNameAndQuantity();
                validator.validate(userInput); // Step 5: 유효성 검증
                return purchaseListHandler.parseInput(userInput); // Step 6: 파싱된 데이터 반환
            });

            // Step 7-10: 구매 처리
            processPurchase(parsedItems);
            return null; // void 반환을 위한 처리
        });
    }

    // 재시도 로직 메소드
    private <T> T retryUntilValid(InputSupplier<T> supplier) {
        while (true) {
            try {
                return supplier.get(); // 유효한 입력값 반환
            } catch (IllegalArgumentException e) {
                System.out.println(ErrorMessage.DEFAULT_HEADER_MESSAGE.getMessage() + e.getMessage()); // 예외 발생 시 에러 메시지 출력
            }
        }
    }

    private void processPurchase(List<String[]> parsedItems) {
        while (true) {
            // Step 7: 상품명 확인 후 프로모션 재고 소진 우선 처리
            for (String[] item : parsedItems) {
                String productName = item[0];
                Integer quantity = Integer.parseInt(item[1]);

                Optional<Product> optionalProduct = products.findProductByName(productName);
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();

                    if (product instanceof PromotionProduct) {
                        PromotionProduct promotionProduct = (PromotionProduct) product;

                        // Promotion-specific handling (Step 7-1)
                        Integer promoStockUsed = promotionProduct.calculatePromotionDiscount(quantity);
                        Integer remainingQuantity = quantity - promoStockUsed;

                        // 7-1-a: 프로모션 혜택을 더 받을 수 있는 경우 안내
                        if (remainingQuantity > 0 && remainingQuantity < promotionProduct.getBuyQuantity()) {
                            int neededQuantity = promotionProduct.getBuyQuantity() - remainingQuantity;
                            String addPromoResponse = inputView.getAdditionalPurchaseResponse(neededQuantity, productName);
                            if (addPromoResponse.equalsIgnoreCase("Y")) {
                                quantity += neededQuantity; // 수량 조정
                            }
                        }

                        // Step 7-1-c: 프로모션 재고 초과 구매 여부 확인
                        int nonPromoQuantity = remainingQuantity - promotionProduct.getPromotionStock();
                        if (remainingQuantity > promotionProduct.getPromotionStock()) {
                            String exceedPromoResponse = inputView.getExceedPromotionPurchaseResponse(productName, nonPromoQuantity);
                            if (exceedPromoResponse.equalsIgnoreCase("Y")) {
                                promotionProduct.sell(promoStockUsed);
                                product.sell(nonPromoQuantity); // 남은 수량을 기본 재고에서 차감
                            } else {
                                return; // Step 7-1-c-j: "안산다" 선택 시 다시 입력 요청
                            }
                        } else {
                            promotionProduct.sell(promoStockUsed);
                        }
                    } else {
                        // Step 7-2: 기본 재고만 있는 상품 처리
                        product.sell(quantity);
                    }
                }
            }

            // Step 8: 멤버십 할인 적용 여부 확인
            Integer totalAmount = products.calculateTotalAmount(parsedItems);
            DiscountInfoDTO discountInfo = new DiscountInfoDTO(totalAmount);

            String membershipResponse = inputView.getMembershipDiscountInput();
            if (membershipResponse.equalsIgnoreCase("Y")) {
                Integer membershipDiscountAmount = membershipDiscount.calculateDiscount(totalAmount);
                discountInfo.applyMembershipDiscount(membershipDiscountAmount);
            }

            // Step 9: 영수증 출력
            List<ProductDisplayDTO> displayItems = products.convertToDisplayDTO(parsedItems);
            Integer finalAmount = discountInfo.getFinalAmount();
            outputView.printReceipt(displayItems, totalAmount, discountInfo.getEventDiscountAmount(), discountInfo.getMembershipDiscountAmount(), finalAmount);

            // Step 10: 추가 구매 여부 확인
            String repeatPurchaseResponse = inputView.getRepeatPurchaseResponse();
            if (repeatPurchaseResponse.equalsIgnoreCase("N")) {
                outputView.printGoodbyeMessage();
                return; // 추가 구매 원하지 않으면 종료
            } else {
                // Step 10-1: 추가 구매를 원하면 재시작
                outputView.printCurrentProducts(products.getProductDisplayList());
                parsedItems = retryUntilValid(() -> {
                    String userInput = inputView.getNameAndQuantity();
                    validator.validate(userInput); // 유효성 검증
                    return purchaseListHandler.parseInput(userInput);
                });
            }
        }
    }

}
