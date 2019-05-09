package com.techprimers.security.jwtsecurity.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.techprimers.security.jwtsecurity.security.JwtAuthenticationEntryPoint;
import com.techprimers.security.jwtsecurity.security.JwtAuthenticationProvider;
import com.techprimers.security.jwtsecurity.security.JwtAuthenticationProvider2;
import com.techprimers.security.jwtsecurity.security.JwtAuthenticationTokenFilter;
import com.techprimers.security.jwtsecurity.security.JwtAuthenticationTokenFilter2;
import com.techprimers.security.jwtsecurity.security.JwtSuccessHandler;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private JwtAuthenticationProvider authenticationProvider;
    @Autowired
    private JwtAuthenticationProvider2 authenticationProvider2;
    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;
    
	/*
	 * @Bean public AuthenticationManager authenticationManager() { return new
	 * ProviderManager(Collections.singletonList(authenticationProvider)); }
	 */  

	/*
	 * @Bean public AuthenticationManager authenticationManager() {
	 * List<AuthenticationProvider> l1=new ArrayList<AuthenticationProvider>();
	 * l1.add(authenticationProvider); l1.add(authenticationProvider2); return new
	 * ProviderManager(l1); }
	 */

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilter() {
        JwtAuthenticationTokenFilter filter = new JwtAuthenticationTokenFilter();
        filter.setAuthenticationManager(new ProviderManager(Collections.singletonList(authenticationProvider)));
        return filter;
    }
    
    @Bean
    public JwtAuthenticationTokenFilter2 authenticationTokenFilter2() {
        JwtAuthenticationTokenFilter2 filter = new JwtAuthenticationTokenFilter2();
        filter.setAuthenticationManager(new ProviderManager(Collections.singletonList(authenticationProvider2)));
        filter.setAuthenticationSuccessHandler(new JwtSuccessHandler());
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("**/rest/**").authenticated()
                .antMatchers("**/hello2").authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(authenticationTokenFilter2(), UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();

    }
}
