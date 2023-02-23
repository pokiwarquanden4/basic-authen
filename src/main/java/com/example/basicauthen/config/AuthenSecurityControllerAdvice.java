package com.example.basicauthen.config;

import com.example.basicauthen.model.UserPrinciple;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@Log4j2
public class AuthenSecurityControllerAdvice {
    @ModelAttribute
    public UserPrinciple customPrincipal(Authentication a) {
        return (UserPrinciple)(a == null ? null : a.getPrincipal());
    }
}

