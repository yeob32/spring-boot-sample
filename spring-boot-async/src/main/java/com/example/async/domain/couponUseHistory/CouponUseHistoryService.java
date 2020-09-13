package com.example.async.domain.couponUseHistory;

import com.example.async.domain.coupon.Coupon;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponUseHistoryService {

    private final CouponUseHistoryRepository couponUseHistoryRepository;

    @Transactional
    public void save(Coupon coupon) {
        log.info("saveCouponUseHistory - currentTransactionName : {}", TransactionSynchronizationManager.getCurrentTransactionName());
        CouponUseHistory couponUseHistory = new CouponUseHistory(coupon);
        couponUseHistoryRepository.save(couponUseHistory);
//        throw new RuntimeException("사용 이력 저장 실패 !!!!!!!");
    }
}
