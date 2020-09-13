package com.example.async.infra;

import com.example.async.domain.account.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailSendService {

    public void sendSignUpEmail(Account account) {
        log.info(" >>>>>>>>>>>>>>>>>> complete send email : {}", account);
    }
}
