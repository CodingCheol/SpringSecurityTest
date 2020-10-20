package com.spring.security.Service;

import com.spring.security.Entity.UserAgentEntity;
import com.spring.security.Repository.UserAgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class UserAgentDetailService implements UserDetailsService {

    private final UserAgentRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserAgentEntity entity = repository.findByUserId(userId);

        if( ObjectUtils.isEmpty(entity)){
            throw new UsernameNotFoundException(String.format("userId : %s",userId));
        }

        return User.builder()
                .username(entity.getUserName())
                .password(entity.getUserPassword())
                .roles(entity.getRoll())
                .build();
    }

    public UserAgentEntity createUserAgent(UserAgentEntity entity){
        entity.passwordEncoding(encoder);
        return repository.save(entity);
    }
}
