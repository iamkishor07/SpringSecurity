package com.example.SpringSecurity.config;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/*The below authentication provider will be executed in case of profile not equal to Prod
* It is customized Spring configu upon the profiles based*/
@Profile("!prod")
@Component
public class CustomAuthenticaonProvider implements AuthenticationProvider {

    private final CustomUserDetailsService customUserDetailsService;

    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticaonProvider(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //get the username & passoword from the Authentication object
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();

        //Load user by the userDetailservice class
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        //Check if the exists password & user given password same or not
        if(passwordEncoder.matches(pwd,userDetails.getPassword()))
        {
            return new UsernamePasswordAuthenticationToken(username,pwd,userDetails.getAuthorities());
        }
        throw new BadCredentialsException("Invalid password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //We are also telling spring to consider ours also supports usernamepasswordAuthentication
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
