package com.bank.account.services;

import com.bank.account.domain.dto.CustomerReqDTO;
import com.bank.account.domain.dto.CustomerRespDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    List<CustomerRespDTO> getCustomers();
    CustomerRespDTO createCustomer(CustomerReqDTO customerReqDTO);
    CustomerRespDTO updateCustomer(String id, CustomerReqDTO customerReqDTO);
    CustomerRespDTO getCustomerById(String id);
    boolean deleteCustomer(String id);
}
