package com.example.async.domain.account;

import com.example.async.domain.coupon.CouponIssueService;
import com.example.async.event.account.AccountEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CouponIssueService couponIssueService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Account signUp(AccountDto accountDto) {
        Account account = accountDto.toEntity();

        eventPublisher.publishEvent(new AccountEvent(account));
        couponIssueService.issueSignUpCoupon(account);

        return account;
    }

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }
}