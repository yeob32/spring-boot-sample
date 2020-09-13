package com.example.async.loader;

import com.example.async.domain.account.Account;
import com.example.async.domain.account.AccountRepository;
import com.example.async.domain.coupon.Coupon;
import com.example.async.domain.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {

    private final AccountRepository accountRepository;
    private final CouponRepository couponRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = Account.createAccount("yeob32");
        accountRepository.save(account);
        Account account2 = Account.createAccount("yeob33");
        accountRepository.save(account2);
        Coupon coupon = Coupon.createCoupon("coupon1", 10000, account);
        couponRepository.save(coupon);
        Coupon coupon2 = Coupon.createCoupon("coupon2", 10000, account2);
        couponRepository.save(coupon2);
    }
}
