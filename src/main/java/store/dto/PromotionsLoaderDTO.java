package store.dto;

import java.time.LocalDate;

public class PromotionsLoaderDTO {
    private final String promotionName;
    private final Integer buyQuantity;
    private final Integer freeQuantity;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public PromotionsLoaderDTO(String promotionName, Integer buyQuantity, Integer freeQuantity, LocalDate startDate, LocalDate endDate) {
        this.promotionName = promotionName;
        this.buyQuantity = buyQuantity;
        this.freeQuantity = freeQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getPromotionName() {
        return promotionName;
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
