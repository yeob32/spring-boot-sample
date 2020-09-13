package com.example.async.domain.account;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AccountApi {

    private final AccountService accountService;

    @PostMapping("/accounts/signUp")
    public Account signUp(@RequestBody AccountDto accountDto) {
        return accountService.signUp(accountDto);
    }

    @GetMapping(value = "/accounts")
    public List<Account> accounts() {
        return accountService.getAccounts();
    }
}
