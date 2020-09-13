package com.example.async.domain.coupon;

import com.example.async.domain.account.Account;
import com.example.async.domain.couponUseHistory.CouponUseHistory;
import com.example.async.exception.ExpiredCouponException;
import com.example.async.exception.InvalidAccountException;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "discount_amount")
    private long discountAmount;

    @Column(name = "used")
    private boolean used;

    @Column(name = "expire_date")
    private LocalDateTime expireDate;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToOne(mappedBy = "coupon")
    private CouponUseHistory couponUseHistory;

    @Builder
    public Coupon(String name, long discountAmount, Account account) {
        this.name = name;
        this.discountAmount = discountAmount;
        this.account = account;
        this.expireDate = LocalDateTime.now().plusDays(7);
    }

    public static Coupon createCoupon(String name, long discountAmount, Account account) {
        return Coupon.builder()
                .name(name)
                .discountAmount(discountAmount)
                .account(account)
                .build();
    }

    public void use(Account account) {
        validateAccount(account);
        validateExpired();

        this.used = true;
    }

    private void validateAccount(Account account) {
        if(!this.account.equals(account)) {
            throw new InvalidAccountException("invalid account : " + account.getId());
        }
    }

    private void validateExpired() {
        if(expireDate.isBefore(LocalDateTime.now())) {
            throw new ExpiredCouponException("expired coupon : " + name);
        }
    }
}
