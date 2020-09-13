package com.example.async.event.account;

import com.example.async.domain.account.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AccountEvent {

    private final Account account;
}
