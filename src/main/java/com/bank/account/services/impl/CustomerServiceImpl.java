package com.bank.account.services.impl;

import com.bank.account.domain.Customer;
import com.bank.account.domain.dto.CustomerReqDTO;
import com.bank.account.domain.dto.CustomerRespDTO;
import com.bank.account.mapper.CustomerMapper;
import com.bank.account.repositories.CustomerRepository;
import com.bank.account.services.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.el.util.ReflectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;


    @Override
    public List<CustomerRespDTO> getCustomers() {
        return customerRepository.findAll()
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
    public Optional<CustomerRespDTO> updateCustomer(String customerId, CustomerReqDTO customerReqDTO) {
        return customerRepository.findById(customerId)
                .map(c -> {
                    c.setActive(customerReqDTO.getActive());
                    c.setFirstName(customerReqDTO.getFirstName());
                    c.setLastName(customerReqDTO.getLastName());
                    c.setDateOfBirth(customerReqDTO.getDateOfBirth());
                    return c;
                })
                .map(customerMapper::customerRespDTO); //как сравнить два обїекта (какие поля);
    }


    @Override
    public Optional<CustomerRespDTO> getCustomerById(String id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerRespDTO);
    }

    @Transactional
    @Override
    public boolean deleteCustomer(String id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<CustomerRespDTO> update(String customerId, Map<Object, Object> fields) {
        Optional<Customer> customerInBase = customerRepository.findById(customerId);
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Customer.class, (String) key);
            field.setAccessible(true);
            if (field.getAnnotatedType().getType().equals(LocalDate.class)) {
                ReflectionUtils.setField(field, customerInBase.get(), LocalDate.parse(value.toString()));
            } else {
                ReflectionUtils.setField(field, customerInBase.get(), value);
            }
        });

        return customerInBase.map(customerRepository::save)
                .map(customerMapper::customerRespDTO);
    }
}
