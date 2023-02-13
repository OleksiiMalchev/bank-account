package com.bank.account.repositories;

import com.bank.account.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,String> {
}
