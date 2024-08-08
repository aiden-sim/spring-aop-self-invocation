package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class SelfInvocationService {
    private final SelfInvocationOuterService selfInvocationOuterService;

    @Setter
    @Autowired
    private SelfInvocationService selfInvocationService;


    @Transactional
    public void caller() {
        log.info("caller");
        callee();
    }

    public void callerAspectJ() {
        log.info("callerAspectJ");
        selfInvocationService.callee();
    }

    public void callerAopContext() {
        log.info("callerAopContext");
        ((SelfInvocationService) AopContext.currentProxy()).callee();
    }

    public void callerSelfDI() {
        log.info("callerSelfDI");
        selfInvocationService.callee();
    }

    public void callerOuterService() {
        log.info("callerOuterService");
        log.info("SelfInvocationOuterService Class : {}", selfInvocationOuterService.getClass());
        selfInvocationOuterService.callee();
    }

    @Transactional
    public void callee() {
        log.info("callee");
        try {
            log.info("callee transaction Status : {}", TransactionAspectSupport.currentTransactionStatus());
        } catch (RuntimeException e) {
            log.error("Transaction 생성 실패..");
        }
    }
}
