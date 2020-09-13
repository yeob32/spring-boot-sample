package com.example.async.domain.coupon;

import com.example.async.domain.account.Account;
import com.example.async.domain.couponUseHistory.CouponUseHistory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponDto {

    private Long id;
    private String name;
    private long discountAmount;
    private boolean used;
    private LocalDateTime expireDate;
    private LocalDateTime createdAt;
    private AccountDtoWithCoupon account;
    private CouponUseHistory couponUseHistory;

    public CouponDto(Coupon coupon) {
        this.id = coupon.getId();
        this.name = coupon.getName();
        this.discountAmount = coupon.getDiscountAmount();
        this.used = coupon.isUsed();
        this.expireDate = coupon.getExpireDate();
        this.createdAt = coupon.getCreatedAt();
        this.account = new AccountDtoWithCoupon(coupon.getAccount());
        this.couponUseHistory = coupon.getCouponUseHistory();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private static class AccountDtoWithCoupon {

        private Long id;
        private String name;

        public AccountDtoWithCoupon(Account account) {
            this.id = account.getId();
            this.name = account.getName();
        }
    }
}
