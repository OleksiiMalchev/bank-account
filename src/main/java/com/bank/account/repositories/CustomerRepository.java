package com.bank.account.repositories;

import com.bank.account.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,String> {
    @Override
    List<Customer> findAll();
}
