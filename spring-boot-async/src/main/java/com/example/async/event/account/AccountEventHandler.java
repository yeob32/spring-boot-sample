package com.example.async.event.account;

import com.example.async.infra.EmailSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class AccountEventHandler {

    private final EmailSendService emailSendService;

    @TransactionalEventListener // 해당 트랜잭션이 Commit된 이후에 리스너가 동작
//    @EventListener
    public void handleAccountEvent(AccountEvent accountEvent) {
        emailSendService.sendSignUpEmail(accountEvent.getAccount());
    }
}
