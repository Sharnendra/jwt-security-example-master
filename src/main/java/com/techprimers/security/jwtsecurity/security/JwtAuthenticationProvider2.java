package com.techprimers.security.jwtsecurity.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.techprimers.security.jwtsecurity.model.JwtAuthenticationToken;
import com.techprimers.security.jwtsecurity.model.JwtUser2;
import com.techprimers.security.jwtsecurity.model.JwtUserDetails;

@Component
public class JwtAuthenticationProvider2 extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private JwtValidator validator;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) usernamePasswordAuthenticationToken;
        String token = jwtAuthenticationToken.getToken();

        JwtUser2 jwtUser2 = validator.validate2(token);

        if (jwtUser2 == null) {
            throw new RuntimeException("JWT Token is incorrect");
        }

        System.err.println("Before error "+jwtUser2.getRole_new()+" d "+jwtUser2.getUserName_new()+" a "+jwtUser2.getId_new());
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(jwtUser2.getRole_new());
        return new JwtUserDetails(jwtUser2.getUserName_new(), jwtUser2.getId_new(),
                token,
                grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (JwtAuthenticationToken.class.isAssignableFrom(aClass));
    }
}
