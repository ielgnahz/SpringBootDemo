package com.example.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

/**
 * ①CommonOAuth2Provider存放google、facebook、github、okta默认配置
 * ②springsecurity中配置的话ctrl+H在scope中搜索security.basic.authorize-mode即可
 * ③index.html中的登录按钮的hreff由OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI +“/ {registrationId}”得来
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login.html", "/login/**", "/test/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/oauth2/authorization/github").permitAll()
                .and().rememberMe()
                .and().oauth2Login()
                .and().csrf().disable().oauth2Login();
    }

    @Bean
    public PrincipalExtractor principalExtractor(){
        return map -> {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            return new User("username", "null", true, true, true, false, grantedAuthorities);
        };
    }
}
