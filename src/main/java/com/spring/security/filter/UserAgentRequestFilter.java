package com.spring.security.filter;

import com.spring.security.Entity.UserAgentEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
public class UserAgentRequestFilter extends GenericFilter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAgentEntity entity = new UserAgentEntity();
            if( !AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())){
            //detail 을 사용하고 싶다면 preAuthentication 을 사용하면된다.
            User user = (User) authentication.getPrincipal();
            entity.setUserId(user.getUsername().split("\\|")[0]);
            entity.setUserName(user.getUsername().split("\\|")[1]);
        }

        log.debug("User Entity ID : {}",entity.getId());

        servletRequest.setAttribute("user", entity);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
