package com.spring.security.Controller;

import com.spring.security.Entity.UserAgentEntity;
import com.spring.security.Repository.UserAgentRepository;
import com.spring.security.Service.UserAgentDetailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
@SpringBootTest
@AutoConfigureMockMvc
public class FormLoginTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    UserAgentDetailService service;

    @Autowired
    UserAgentRepository repository;


    @Test
    void formLoginTest() throws Exception {

        UserAgentEntity entity = new UserAgentEntity();
        entity.setUserId("cordingCheol");
        entity.setUserName("cordingCheol");
        entity.setUserPassword("1234");
        entity.setRoll("USER");

        //given  (Service 를 받아서 처리 시에는 formLogin 에서 userDetailService null error 발생)
        UserAgentEntity userAgentEntity = service.createUserAgent(entity);

        //ID, Password로 로그인 체크
        mvc.perform(formLogin().user("cordingCheol").password("1234"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(authenticated());
    }
    @Test
    void formLoginERRORTest() throws Exception {

        UserAgentEntity entity = new UserAgentEntity();
        entity.setUserId("cordingCheol");
        entity.setUserName("cordingCheol");
        entity.setUserPassword("1234");

        //given  (Service 를 받아서 처리 시에는 formLogin 에서 userDetailService null error 발생)
        UserAgentEntity userAgentEntity = service.createUserAgent(entity);

        //ID, Password로 로그인 체크
        mvc.perform(formLogin().user("cordingCheol").password("1234"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(unauthenticated());
    }
}
