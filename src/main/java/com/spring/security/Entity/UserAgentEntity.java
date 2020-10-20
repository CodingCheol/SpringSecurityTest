package com.spring.security.Entity;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Data
@Entity
public class UserAgentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(unique = true)
    private String userId;

    private String userName;

    private String userPassword;

    private String roll;

    public void passwordEncoding(PasswordEncoder passwordEncoder){
        this.userPassword = passwordEncoder.encode(this.userPassword);
    }
}
