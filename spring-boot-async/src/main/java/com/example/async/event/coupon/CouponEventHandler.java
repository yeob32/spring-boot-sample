package com.example.async.event.coupon;

import com.example.async.domain.coupon.Coupon;
import com.example.async.domain.couponUseHistory.CouponUseHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CouponEventHandler {

    private final CouponUseHistoryService couponUseHistoryService;

    @EventListener
    public void handleCouponEvent(CouponEvent couponEvent) {
        Coupon coupon = couponEvent.getCoupon();
        couponUseHistoryService.save(coupon);
    }
}
