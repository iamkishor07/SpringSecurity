package com.example.SpringSecurity.service;

import com.example.SpringSecurity.dao.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);

    Customer getCustomerById(long customerid);

    List<Customer> getAllCustomers();

    void deleteCustomer(long customerid);
}
