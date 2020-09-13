package com.example.async.domain.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountDto {

    private String name;

    public Account toEntity() {
        return Account.createAccount(name);
    }
}
