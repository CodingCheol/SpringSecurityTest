package com.spring.security.Controller;

import com.spring.security.Entity.UserAgentEntity;
import com.spring.security.Service.UserAgentDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BasicRestUserController {

    @Autowired
    private UserAgentDetailService service;

    @RequestMapping(value = "/basic/account",method = RequestMethod.POST)
    public UserAgentEntity createAccount(@RequestBody UserAgentEntity entity){

        return service.createUserAgent(entity);
    }

    @RequestMapping(value = "/basic/account/{id}/{name}/{password}/{role}",method = RequestMethod.GET)
    public UserAgentEntity createAccount(@PathVariable(value = "id") String id,
                                         @PathVariable(value = "name") String name,
                                         @PathVariable(value = "password") String password,
                                         @PathVariable(value = "role") String role){

        UserAgentEntity entity = new UserAgentEntity();
        entity.setUserId(id);
        entity.setUserName(name);
        entity.setUserPassword(password);
        entity.setRoll(role);
        return service.createUserAgent(entity);
    }

}
