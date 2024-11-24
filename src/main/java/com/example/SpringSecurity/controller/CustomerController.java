package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dao.Customer;
import com.example.SpringSecurity.service.CustomerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("api/v1/user")
public class CustomerController {
   private CustomerServiceImpl customerServiceimpl;

    public CustomerController(CustomerServiceImpl customerServiceimpl) {
        this.customerServiceimpl = customerServiceimpl;
    }


    @PostMapping("/signup")
    public ResponseEntity<Customer> SaveCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerServiceimpl.createCustomer(customer), HttpStatus.CREATED);
    }

    @GetMapping("customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable long id) {
        return new ResponseEntity<>(customerServiceimpl.getCustomerById(id), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerServiceimpl.getAllCustomers(), HttpStatus.OK);
    }
}
