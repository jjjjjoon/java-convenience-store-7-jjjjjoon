package store.model;

import store.dto.ProductsLoaderDTO;
import store.dto.PromotionsLoaderDTO;

import java.time.LocalDate;

public class PromotionProduct extends Product {
    private Integer promotionStock; // 프로모션 재고
    private Boolean isPromotion; // 프로모션 여부 확인
    private Integer buyQuantity; // 프로모션 조건: N개 구매 시
    private Integer freeQuantity; // 프로모션 조건: 1개 무료 제공
    private LocalDate startDate; // 프로모션 시작일
    private LocalDate endDate; // 프로모션 종료일

    // PromotionProduct 생성자
    public PromotionProduct(ProductsLoaderDTO productDto, PromotionsLoaderDTO promoDto) {
        super(productDto);

        // promotion 값이 "null"이 아니면 프로모션 상품으로 간주
        this.isPromotion = !"null".equals(productDto.getPromotion());
        if (isPromotion) {
            this.promotionStock = productDto.getQuantity(); // 프로모션 상품 재고
            this.quantity = 0; // 기본 재고는 0으로 설정
            this.buyQuantity = promoDto.getBuyQuantity();
            this.freeQuantity = promoDto.getFreeQuantity();
            this.startDate = promoDto.getStartDate();
            this.endDate = promoDto.getEndDate();
        }
    }

    // 현재 프로모션이 유효한지 확인하는 메서드
    private boolean isPromotionActive() {
        LocalDate today = LocalDate.now();
        return isPromotion && today.isAfter(startDate) && today.isBefore(endDate);
    }

    // 프로모션 상품 판매 메서드
    public Integer calculatePromotionDiscount(Integer requestedQuantity) {
        if (!isPromotionActive()) {
            return 0; // 프로모션이 유효하지 않은 경우 할인 없음
        }

        Integer totalFreeQuantity = applyPromotionDiscount(requestedQuantity);
        Integer remainingQuantity = requestedQuantity % buyQuantity;

        if (remainingQuantity > 0) {
            sellRemainingQuantity(remainingQuantity); // 남은 수량은 기본 재고에서 차감
        }

        return totalFreeQuantity;
    }

    // 프로모션 할인 계산 로직을 분리
    private Integer applyPromotionDiscount(Integer requestedQuantity) {
        Integer totalPaidQuantity = 0;
        Integer totalFreeQuantity = 0;

        while (requestedQuantity >= buyQuantity && promotionStock >= buyQuantity) {
            totalPaidQuantity += buyQuantity;
            totalFreeQuantity += freeQuantity;
            promotionStock -= buyQuantity;
            requestedQuantity -= buyQuantity;
        }
        return totalFreeQuantity;
    }

    // 기본 재고에서 남은 수량 판매 처리
    private void sellRemainingQuantity(Integer remainingQuantity) {
        sell(remainingQuantity); // 기본 재고에서 남은 수량 차감
    }


    // 프로모션 할인을 포함한 결제 금액 계산 메서드
    public Integer calculateTotalPrice(Integer requestedQuantity) {
        Integer freeItems = calculatePromotionDiscount(requestedQuantity);
        Integer totalPrice = (requestedQuantity - freeItems) * getPrice();
        return totalPrice;
    }

    public Integer getPromotionStock() {
        return promotionStock;
    }

}
