package com.spring.security.Controller;

import com.spring.security.Annotation.WithCustomUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class BasicUserControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void default_anonymous() throws Exception {
        mvc.perform(get("/admin")
                .with(anonymous()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    @Test
    @WithAnonymousUser
    void default_anonymous_with_annotation() throws Exception {
        mvc.perform(get("/admin"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    @Test
    void default_admin() throws Exception {
        mvc.perform(get("/admin")
                .with(user("cordingCheol").roles("ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser(username = "cordingCheol", roles = "ADMIN")
    void default_admin_with_annotation() throws Exception {
        mvc.perform(get("/admin"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithCustomUser // 비슷한 유저로 테스트 할 경우 중복이 발생할 수 있으니 변경
    void default_admin_with_annotation_custom() throws Exception {
        mvc.perform(get("/admin"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
