package com.example.async.domain.couponUseHistory;

import com.example.async.domain.coupon.Coupon;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "coupon_use_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CouponUseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_use_history")
    private Long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    public CouponUseHistory createCouponUseHistory(Coupon coupon) {
        CouponUseHistory couponUseHistory = new CouponUseHistory(coupon);
        return couponUseHistory;
    }

    public CouponUseHistory(Coupon coupon) {
        this.coupon = coupon;
    }
}
