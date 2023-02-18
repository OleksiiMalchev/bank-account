package com.bank.account.repositories;

import com.bank.account.domain.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction,String> {
}
