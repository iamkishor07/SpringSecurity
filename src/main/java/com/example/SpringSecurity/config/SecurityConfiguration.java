package com.example.SpringSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration  {

    /*The below is the default Security Filter chain,
    If we provided sprin will consider this as default with custom configuration*/
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        /*When we are using spring Security , we need to either provide crsf token or
        * we just disable it inorder to work, by defult post,put apis will not work
        * but the get apis will work, if crsf disable all http methods will work*/

        http.csrf(crsf ->crsf.disable());
        http.authorizeHttpRequests((requests) ->requests.requestMatchers("/h2-console/**","/api/v1/Customer/signup/**").permitAll().
                anyRequest().authenticated());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    /*This UserDetailsService is responsible for loading the users
    * either from Inmemory or JDBC by UserDetailsManger with the User class
    * which stores the username,password,authorities,isauthenticated & so,on*/

    /*The below code is commented , since we implemented custom UserDetailsService class*/
/*
    @Bean
    public UserDetailsService userDetailsService()
    {
        //at the time of object creation
        UserDetails userDetails = User.withUsername("kishor").password("{noop}1234").authorities("read").build();
        return new InMemoryUserDetailsManager(userDetails);
    }
*/

    /*The below class constructor is to load a default password encoder
    * as we are using custom tables & custom Implementaion, we need to provide passwordEncoder*/
    @Bean
    public PasswordEncoder PasswordEncoder()
    {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }



    /*The below method to allow access to h2-console database
    * By default when we use spring Security, It blocks the h2-console URLS
    * as this webSecurity Customiser we are matching h2-consile pattern & allowing it*/
    @Bean
    WebSecurityCustomizer ignoringCustomizer()
    {
        return web -> web.ignoring().requestMatchers("/h2-console/**");
    }
}
