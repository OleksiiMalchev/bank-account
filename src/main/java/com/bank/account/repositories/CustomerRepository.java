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

    @Query(value = "SELECT * FROM customer\n" +
            "INNER JOIN account \n" +
            "ON customer.id= account.customer_id\n" +
            "INNER JOIN transaction\n" +
            "ON account.id = transaction.account_id\n" +
            "where customer_id = :id", nativeQuery = true)
    Optional<Customer> reportCustomerById(@Param("id") String customerId);

    @Query(value = "SELECT * FROM customer\n" +
            "INNER JOIN account \n" +
            "ON customer.id= account.customer_id\n" +
            "INNER JOIN transaction\n" +
            "ON account.id = transaction.account_id", nativeQuery = true)
    List<Customer> reportCustomer();
}
