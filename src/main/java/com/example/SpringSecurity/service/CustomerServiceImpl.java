package com.example.SpringSecurity.service;

import com.example.SpringSecurity.dao.Customer;
import com.example.SpringSecurity.repositry.CustomerRepositry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepositry customerRepositry;
    private PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepositry customerRepositry,PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.customerRepositry = customerRepositry;
    }


    @Override
    public Customer createCustomer(Customer customer) {
        customer.setPwd(passwordEncoder.encode(customer.getPwd()));
         return customerRepositry.save(customer);
    }

    @Override
    public Customer getCustomerById(long customerid) {
        return customerRepositry.findById(customerid).orElse(null);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepositry.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public void deleteCustomer(long customerid) {
        if(null  != customerRepositry.findById(customerid).orElse(null) )
        {
            customerRepositry.deleteById(customerid);
        }
    }
}
