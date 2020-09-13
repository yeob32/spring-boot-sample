package com.example.async.domain.account;

import com.example.async.domain.coupon.Coupon;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Coupon> coupons = new ArrayList<>();

    public static Account createAccount(String name) {
        Account account = new Account();
        account.setName(name);

        return account;
    }

    private void setName(String name) {
        this.name = name;
    }
}
