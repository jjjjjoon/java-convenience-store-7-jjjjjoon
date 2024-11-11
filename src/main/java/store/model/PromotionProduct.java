package store.model;

import store.dto.ProductsLoaderDTO;
import store.dto.PromotionsLoaderDTO;

import java.time.LocalDate;

public class PromotionProduct extends Product {
    private Integer promotionStock;
    private Boolean isPromotion;
    private Integer buyQuantity;
    private Integer freeQuantity;
    private LocalDate startDate;
    private LocalDate endDate;

    public PromotionProduct(ProductsLoaderDTO productDto, PromotionsLoaderDTO promoDto) {
        super(productDto);

        this.isPromotion = !"null".equals(productDto.getPromotion());
        if (isPromotion) {
            this.promotionStock = productDto.getQuantity();
            this.quantity = 0;
            this.buyQuantity = promoDto.getBuyQuantity();
            this.freeQuantity = promoDto.getFreeQuantity();
            this.startDate = promoDto.getStartDate();
            this.endDate = promoDto.getEndDate();
        }
    }

    private boolean isPromotionActive() {
        LocalDate today = LocalDate.now();
        return isPromotion && today.isAfter(startDate) && today.isBefore(endDate);
    }

    public Integer calculatePromotionDiscount(Integer requestedQuantity) {
        if (!isPromotionActive()) {
            return 0;
        }

        Integer totalFreeQuantity = applyPromotionDiscount(requestedQuantity);
        Integer remainingQuantity = requestedQuantity % buyQuantity;

        if (remainingQuantity > 0) {
            sellRemainingQuantity(remainingQuantity);
        }

        return totalFreeQuantity;
    }

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

    private void sellRemainingQuantity(Integer remainingQuantity) {
        sell(remainingQuantity);
    }

    public Integer calculateTotalPrice(Integer requestedQuantity) {
        Integer freeItems = calculatePromotionDiscount(requestedQuantity);
        Integer totalPrice = (requestedQuantity - freeItems) * getPrice();
        return totalPrice;
    }

    public Integer getPromotionStock() {
        return promotionStock;
    }

    public Integer getBuyQuantity() {
        return buyQuantity;
    }

    public Integer getFreeQuantity() {
        return freeQuantity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
