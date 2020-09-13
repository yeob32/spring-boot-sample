package com.example.async.domain.coupon;

import com.example.async.domain.account.Account;
import com.example.async.domain.account.AccountRepository;
import com.example.async.domain.couponUseHistory.CouponUseHistoryService;
import com.example.async.event.coupon.CouponEvent;
import com.example.async.exception.NotFoundAccountException;
import com.example.async.exception.NotFoundCouponException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final AccountRepository accountRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CouponUseHistoryService couponUseHistoryService;

    @Transactional(readOnly = true)
    public List<Coupon> coupons() {
        List<Coupon> coupons = couponRepository.findAll();
        return new ArrayList<>(coupons);
    }

    @Transactional
    public Coupon use(long couponId, long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(NotFoundAccountException::new);
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(NotFoundCouponException::new);

        coupon.use(account);

        applicationEventPublisher.publishEvent(new CouponEvent(this, coupon));

        try {
            test();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return coupon;
    }

    @Transactional
    public void test() {
        if (true) {
            throw new RuntimeException();
        }
    }
}
