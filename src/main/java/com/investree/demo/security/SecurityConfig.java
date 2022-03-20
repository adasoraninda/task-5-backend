package com.investree.demo.security;

import com.investree.demo.jwt.JwtConfig;
import com.investree.demo.jwt.JwtTokenVerifier;
import com.investree.demo.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.investree.demo.view.UsersService;
import javax.crypto.SecretKey;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UsersService usersService;
  private final PasswordEncoder passwordEncoder;
  private final SecretKey secretKey;
  private final JwtConfig jwtConfig;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(usersService).passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilter(
            new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(),
                jwtConfig,
                secretKey)
        )
        .addFilterAfter(
            new JwtTokenVerifier(
                secretKey,
                jwtConfig
            ),
            JwtUsernameAndPasswordAuthenticationFilter.class
        )
        .authorizeRequests()
        .antMatchers(HttpMethod.GET).permitAll()
        .anyRequest()
        .authenticated();
  }

}
