package com.bank.account.repositories;

import com.bank.account.domain.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {
    @Override
    List<Customer> findAll();

    @Query("SELECT customer from Customer customer left join fetch  customer.accounts where customer.id = :id")
    Optional<Customer> reportCustomerById(@Param("id") String customerId);

    @Query("SELECT customer from Customer customer left join fetch  customer.accounts ") // add left join customer.accounts.transaction
    List<Customer> reportCustomer();
}
