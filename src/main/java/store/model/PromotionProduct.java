package store.model;

import store.dto.ProductsLoaderDTO;
import store.dto.PromotionsLoaderDTO;

import java.time.LocalDate;

public class PromotionProduct extends Product {
    private int promotionStock; // 프로모션 재고
    private boolean isPromotion; // 프로모션 여부 확인
    private final int buyQuantity; // 프로모션 조건: N개 구매 시
    private final int freeQuantity; // 프로모션 조건: 1개 무료 제공
    private final LocalDate startDate; // 프로모션 시작일
    private final LocalDate endDate; // 프로모션 종료일

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
        } else {
            this.promotionStock = 0; // 프로모션 재고는 0으로 설정
            this.buyQuantity = 0;
            this.freeQuantity = 0;
            this.startDate = null;
            this.endDate = null;
        }
    }

    // 현재 프로모션이 유효한지 확인하는 메서드
    private boolean isPromotionActive() {
        LocalDate today = LocalDate.now();
        return isPromotion && today.isAfter(startDate) && today.isBefore(endDate);
    }

    // 프로모션 상품 판매 메서드
    public int calculatePromotionDiscount(int requestedQuantity) {
        if (!isPromotionActive()) {
            return 0; // 프로모션이 유효하지 않은 경우 할인 없음
        }

        int totalPaidQuantity = 0;
        int totalFreeQuantity = 0;

        // 프로모션 재고와 기본 재고를 모두 고려한 할인 적용
        while (requestedQuantity >= buyQuantity && promotionStock >= buyQuantity) {
            totalPaidQuantity += buyQuantity;
            totalFreeQuantity += freeQuantity;
            promotionStock -= buyQuantity;
            requestedQuantity -= buyQuantity;
        }

        if (requestedQuantity > 0) {
            sell(requestedQuantity); // 남은 수량은 기본 재고에서 차감
            totalPaidQuantity += requestedQuantity;
        }

        return totalFreeQuantity; // 증정 상품 수량 반환
    }

    // 프로모션 할인을 포함한 결제 금액 계산 메서드
    public int calculateTotalPrice(int requestedQuantity) {
        int freeItems = calculatePromotionDiscount(requestedQuantity);
        int totalPrice = (requestedQuantity - freeItems) * getPrice();
        return totalPrice;
    }

    public int getPromotionStock() {
        return promotionStock;
    }
}
