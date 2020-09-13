package com.example.async.domain.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class CouponApi {

    private final CouponService couponService;

    @GetMapping(value = "/coupon/coupons")
    public List<CouponDto> coupons() {
        return couponService.coupons().stream().map(CouponDto::new).collect(Collectors.toList());
    }

    @PostMapping(value = "/coupon/use")
    public CouponDto useCoupon(@RequestParam(value = "couponId") long couponId, @RequestParam("accountId") long accountId) {
        return new CouponDto(couponService.use(couponId, accountId));
    }
}