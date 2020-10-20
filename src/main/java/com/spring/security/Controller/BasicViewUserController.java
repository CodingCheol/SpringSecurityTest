package com.spring.security.Controller;

import com.spring.security.Entity.UserAgentEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BasicViewUserController {

    @RequestMapping(value = "/admin",method = RequestMethod.GET)
    public ModelAndView adminMain(){
        ModelAndView mov = new ModelAndView();
        mov.setViewName("AdminMain");

//        mov.addObject("userId",entity.getUserId());
//        mov.addObject("userName",entity.getUserName());
        mov.addObject("userId","TEST_ID");
        mov.addObject("userName","TEST_NAME");

        return mov;
    }

}
