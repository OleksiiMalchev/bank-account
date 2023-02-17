package com.bank.account.services.impl;

import com.bank.account.domain.dto.CustomerReqDTO;
import com.bank.account.domain.dto.CustomerRespDTO;
import com.bank.account.mapper.CustomerMapper;
import com.bank.account.repositories.CustomerRepository;
import com.bank.account.services.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerRespDTO> getCustomers() {
        return  customerRepository.findAll()
                .stream().map(customerMapper::customerRespDTO)
                .toList();
    }

    @Override
    public Optional<CustomerRespDTO> createCustomer(CustomerReqDTO customerReqDTO) {
        return customerMapper.reqDTOCustomer(customerReqDTO)
                .map(customerRepository::save)
                .map(customerMapper::customerRespDTO);
    }

    @Override
    @Transactional
    public Optional<CustomerRespDTO> updateCustomer(String id, CustomerReqDTO customerReqDTO) {
        return customerRepository.findById(id)
                .map(c->{
                    c.setActive(customerReqDTO.isActive());
                    c.setFirstName(customerReqDTO.getFirstName());
                    c.setLastName(customerReqDTO.getLastName());
                    c.setDateOfBirth(customerReqDTO.getDateOfBirth());
                    return c;
                })
                .map(customerMapper::customerRespDTO);
    }

    @Override
    public Optional<CustomerRespDTO> getCustomerById(String id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerRespDTO);
    }

    @Override
    public boolean deleteCustomer(String id) {
        if(customerRepository.existsById(id)){
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
