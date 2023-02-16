package com.bank.account.repositories;

import com.bank.account.domain.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {
    @Override
    List<Customer> findAll();

//    @Query("Select customer from Customer customer left join fetch customer.account a where a.id = :id")
//    Customer reportCustomerById(@Param("id") String customerId);
}
