package com.spring.security.Repository;

import com.spring.security.Entity.UserAgentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAgentRepository extends JpaRepository<UserAgentEntity,Integer> {
    UserAgentEntity findByUserId(String userName);
}
