package com.bank.account.services;

import com.bank.account.domain.dto.AccountRespDTO;
import com.bank.account.domain.dto.CustomerReqDTO;
import com.bank.account.domain.dto.CustomerRespDTO;
import com.bank.account.domain.dto.DepositWithdrawalDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface CustomerService {
    List<CustomerRespDTO> getCustomers();
    Optional<CustomerRespDTO> createCustomer(CustomerReqDTO customerReqDTO);
    Optional<CustomerRespDTO> getCustomerById(String id);
    boolean deleteCustomer(String id);
    Optional<CustomerRespDTO> updateCustomer(String id, Map<Object,Object> fields);



}
