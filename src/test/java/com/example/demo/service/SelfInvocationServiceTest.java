package com.example.demo.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

class SelfInvocationServiceTest {

    @Nested
    @DisplayName("일반적인 호출")
    @SpringBootTest
    @EnableTransactionManagement(mode = AdviceMode.PROXY)
    class Normal {
        @Autowired
        private SelfInvocationService selfInvocationService;

        @Test
        void caller() {
            System.out.println("SelfInvocationService Class : " + selfInvocationService.getClass());
            selfInvocationService.caller();
        }
    }

    @Nested
    @DisplayName("AspectJ 호출")
    @SpringBootTest
    @EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
    class AspectJ {
        @Autowired
        private SelfInvocationService selfInvocationService;

        @Test
        void caller() {
            System.out.println("SelfInvocationService Class : " + selfInvocationService.getClass());
            selfInvocationService.callerAspectJ();
        }
    }

    @Nested
    @DisplayName("AopContext로 Proxy 조회 호출")
    @SpringBootTest
    @EnableAspectJAutoProxy(exposeProxy = true)
    @EnableTransactionManagement(mode = AdviceMode.PROXY)
    class AopContext {
        @Autowired
        private SelfInvocationService selfInvocationService;

        @Test
        void caller() {
            System.out.println("SelfInvocationService Class : " + selfInvocationService.getClass());
            selfInvocationService.callerAopContext();
        }
    }

    @Nested
    @DisplayName("Self DI 호출")
    @SpringBootTest
    @EnableTransactionManagement(mode = AdviceMode.PROXY)
    @TestPropertySource(properties = "spring.main.allow-circular-references=true")
    class SelfDI {
        @Autowired
        private SelfInvocationService selfInvocationService;

        @Test
        void caller() {
            System.out.println("SelfInvocationService Class : " + selfInvocationService.getClass());
            selfInvocationService.callerSelfDI();
        }
    }

    @Nested
    @DisplayName("외부 레이어 분리")
    @SpringBootTest
    @EnableTransactionManagement(mode = AdviceMode.PROXY)
    class OuterService {
        @Autowired
        private SelfInvocationService selfInvocationService;

        @Test
        void caller() {
            System.out.println("SelfInvocationService Class : " + selfInvocationService.getClass());
            selfInvocationService.callerOuterService();
        }
    }

}
