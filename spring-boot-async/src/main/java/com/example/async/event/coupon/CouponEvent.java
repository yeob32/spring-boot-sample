package com.example.async.event.coupon;

import com.example.async.domain.coupon.Coupon;
import org.springframework.context.ApplicationEvent;

public class CouponEvent extends ApplicationEvent {

    private final Coupon coupon;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public CouponEvent(Object source, Coupon coupon) {
        super(source);
        this.coupon = coupon;
    }

    public Coupon getCoupon() {
        return coupon;
    }
}
