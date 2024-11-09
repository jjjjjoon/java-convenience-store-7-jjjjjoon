package store.service;

import store.constant.Constants;

public class MembershipDiscount {// 최대 할인 한도

    public int calculateDiscount(int amount) {
        int discount = (int) (amount * Constants.DISCOUNT_RATE);  // 30% 할인 계산
        return Math.min(discount, Constants.MAX_DISCOUNT_AMOUNT);  // 최대 할인 한도 적용
    }
}
