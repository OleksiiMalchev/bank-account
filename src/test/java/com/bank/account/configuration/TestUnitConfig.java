package com.bank.account.configuration;

import com.bank.account.repositories.AccountRepository;
import com.bank.account.repositories.CustomerRepository;
import com.bank.account.repositories.TransactionRepository;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@ImportAutoConfiguration(exclude = {FlywayAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class,
        DataSourceAutoConfiguration.class})
public class TestUnitConfig {
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private TransactionRepository transactionRepository;

}
