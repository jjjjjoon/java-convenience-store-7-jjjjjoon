package store.dto;

public class DiscountInfoDTO {
    private Integer totalAmount;
    private Integer membershipDiscountAmount;
    private Integer eventDiscountAmount;

    public DiscountInfoDTO(Integer totalAmount) {
        this.totalAmount = totalAmount;
        this.membershipDiscountAmount = 0;
        this.eventDiscountAmount = 0;
    }

    public void applyMembershipDiscount(Integer discountAmount) {
        this.membershipDiscountAmount = discountAmount;
    }

    public void applyEventDiscount(Integer discountAmount) {
        this.eventDiscountAmount = discountAmount;
    }

    public Integer getFinalAmount() {
        return totalAmount - membershipDiscountAmount - eventDiscountAmount;
    }

    public Integer getMembershipDiscountAmount() {
        return membershipDiscountAmount;
    }

    public Integer getEventDiscountAmount() {
        return eventDiscountAmount;
    }
}
