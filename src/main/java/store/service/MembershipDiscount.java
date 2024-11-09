package store.service;

import store.constant.Constants;

public class MembershipDiscount {

    public Integer calculateDiscount(Integer amount) {
        Integer discount = (int) (amount * Constants.DISCOUNT_RATE);  // 30% 할인 계산
        return Math.min(discount, Constants.MAX_DISCOUNT_AMOUNT);  // 최대 할인 한도 적용
    }
}
