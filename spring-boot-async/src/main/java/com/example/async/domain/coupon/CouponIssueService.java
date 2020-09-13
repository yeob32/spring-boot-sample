package com.example.async.domain.coupon;

import com.example.async.domain.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponIssueService {

    private final CouponRepository couponRepository;

    @Transactional
    public void issueSignUpCoupon(Account account) {
        Coupon coupon = Coupon.createCoupon("가입 쿠폰", 1000, account);
        couponRepository.save(coupon);

        throw new RuntimeException("issueSignUpCoupon Exception !!");
    }
}
