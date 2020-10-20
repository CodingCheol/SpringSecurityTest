package com.spring.security.config;

import com.spring.security.Service.UserAgentDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityBasicConfig extends WebSecurityConfigurerAdapter {

    private final UserAgentDetailService service;
    private final PasswordEncoder encoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //사실 상 Service Bean 으로 등록되어 있는 Service 를 넣는거 자체가 의미가 없음.
        //Service Bean 으로 등록되어 지지 않았을 때를 대비하여 예행연습이라고 생각하고 해보자.
        auth.userDetailsService(service)
            .passwordEncoder(encoder);  //Spring 기본적으로 BCryptPasswordEncoder 를 사용하겠지만 연습이니깐..
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/","/basic/account","/basic/account/**").permitAll()
                .mvcMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .and()
            .httpBasic();
    }
}
