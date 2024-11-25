package com.example.SpringSecurity.config;

import com.example.SpringSecurity.dao.Customer;
import com.example.SpringSecurity.repositry.CustomerRepositry;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private CustomerRepositry customerRepositry;

    public CustomUserDetailsService(CustomerRepositry customerRepositry) {
        this.customerRepositry = customerRepositry;
    }

    /*The below loadUserByUsername of UserDetailsService interface.
    * Which tells to spring framework while invoking the DAO Authentication Provider to get the
    * load user details. spring considers the custom implementation of userDetailsService instead
    * of Inmemory , Jdbc etc...*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepositry.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Username not found " + username));

        return new User(customer.getEmail(),customer.getPwd(), List.of(new SimpleGrantedAuthority(customer.getRoles())) );
    }


}
