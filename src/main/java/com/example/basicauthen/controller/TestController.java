package com.example.basicauthen.controller;

import com.example.basicauthen.model.UserPrinciple;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Log4j2
public class TestController {
    @GetMapping("/test")
    public String test(@ModelAttribute UserPrinciple principle) {
        log.info("User login information" + principle);
        return "oke";
    }
}
