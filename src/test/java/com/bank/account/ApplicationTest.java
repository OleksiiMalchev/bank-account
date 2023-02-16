package com.bank.account;

import com.bank.account.configuration.TestUnitConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestUnitConfig.class)
class ApplicationTest {
    @Test
    void contextLoads() {
    }
}