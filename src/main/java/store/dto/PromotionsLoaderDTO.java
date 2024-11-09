package store.dto;

import java.time.LocalDate;

public class PromotionsLoaderDTO {
    private final String promotionName;
    private final int buyQuantity;
    private final int freeQuantity;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public PromotionsLoaderDTO(String promotionName, int buyQuantity, int freeQuantity, LocalDate startDate, LocalDate endDate) {
        this.promotionName = promotionName;
        this.buyQuantity = buyQuantity;
        this.freeQuantity = freeQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public int getFreeQuantity() {
        return freeQuantity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
