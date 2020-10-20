package com.spring.security.Annotation;

import org.springframework.security.test.context.support.WithMockUser;

@WithMockUser(username = "cordingCheol",roles = "ADMIN")
public @interface WithCustomUser {
}
