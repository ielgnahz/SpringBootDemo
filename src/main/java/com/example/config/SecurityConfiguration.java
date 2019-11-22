package com.example.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
        http.authorizeRequests().antMatchers("/login.html", "/login/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/oauth2/authorization/github").permitAll()
                .and().rememberMe()
                .and().oauth2Login()
                .and().csrf().disable().oauth2Login();
    }
}