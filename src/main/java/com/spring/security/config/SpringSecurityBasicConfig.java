package com.spring.security.config;

import com.spring.security.Service.UserAgentDetailService;
import com.spring.security.filter.UserAgentRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityBasicConfig extends WebSecurityConfigurerAdapter {

    private final UserAgentDetailService service;
    private final PasswordEncoder encoder;

    @Override
    public void configure(WebSecurity web) throws Exception {
        //static Resource 에 대한 FilterChain 제거
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //사실 상 Service Bean 으로 등록되어 있는 Service 를 넣는거 자체가 의미가 없음.
        //Service Bean 으로 등록되어 지지 않았을 때를 대비하여 예행연습이라고 생각하고 해보자.
        auth.userDetailsService(service)
            .passwordEncoder(encoder);  //Spring 기본적으로 BCryptPasswordEncoder 를 사용하겠지만 연습이니깐..
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //UserAgent Add
        http.addFilterAfter(new UserAgentRequestFilter(), AnonymousAuthenticationFilter.class);
        //Authentication Config
        http.authorizeRequests()
                .mvcMatchers("/","/basic/account","/basic/account/**").permitAll()
                .mvcMatchers("/admin").hasRole("ADMIN")
                .mvcMatchers("/user").hasRole("USER")
                .anyRequest().authenticated()
//                .accessDecisionManager(hierarchyDecisionManager())        //위치를 잘 기억해두자. // 어느 구역에서 어느 권한을 가지고 사용할 것인지에 대한 결정이다.
                .expressionHandler(hierarchyHandler())          //accessDecisionManager 을 건들지 않고 ExpressionHandler만 건드려도 변경이 가능하다.
                .and()
            .formLogin()
                .and()
            .httpBasic();
    }

    /*
    * 일반적으로 권한을 설정할때면 상위의 권한과 하위의 권한을 가질때가 있다.
    * 이런 계층형 권한 구조를 가질 때는 상위의 권한이 하위의 권한의 접속을 허락하여야 한다.
    * 이럴 떄 아래와 같이 새롭게 AccessDecisionManager 를 설정하여 주면된다.
    * */
    //AccessDecisionManager
    AccessDecisionManager hierarchyDecisionManager(){
        //role 등록
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        //계층형 구조를 설정하는 것이고, '>' 를 왼쪽이 상위 오른쪽이 하위 설정이 가능하다.
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        //DefaultWebSecurityExpressionHandler handler 등록
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy); // role

        //voter  등록
        WebExpressionVoter voter = new WebExpressionVoter();
        voter.setExpressionHandler(handler); //handler

        List<AccessDecisionVoter<?>> decisionVoters = Collections.singletonList(voter);
        return new AffirmativeBased(decisionVoters);
    }
    //ExpressionHandler
    DefaultWebSecurityExpressionHandler hierarchyHandler(){
        //role 등록
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        //계층형 구조를 설정하는 것이고, '>' 를 왼쪽이 상위 오른쪽이 하위 설정이 가능하다.
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        //DefaultWebSecurityExpressionHandler handler 등록
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy); // role
        return handler;
    }
}
