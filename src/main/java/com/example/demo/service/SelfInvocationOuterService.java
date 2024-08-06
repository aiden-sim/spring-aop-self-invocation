package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Slf4j
@Service
public class SelfInvocationOuterService {
    @Transactional
    public void callee() {
        try {
            log.info("callee transaction Status :" + TransactionAspectSupport.currentTransactionStatus());
        } catch (RuntimeException e) {
            log.error("Transaction 생성 실패..");
        }
    }
}
