package store.service;

import store.constant.Constants;

public class MembershipDiscount {

    public Integer calculateDiscount(Integer amount) {
        Integer discount = (int) (amount * Constants.DISCOUNT_RATE);
        return Math.min(discount, Constants.MAX_DISCOUNT_AMOUNT);
    }
}
