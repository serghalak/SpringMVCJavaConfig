package com.apress.prospring5.ch16.controller;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class HelloController {

    private MessageSource messageSource;

    public HelloController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/hello-world")
    public String sayHello(Model model) {

        //model.addAttribute("test", messageSource.getMessage("singer_save_success", null, Locale.ENGLISH));
        System.out.println(messageSource.getMessage("userform.title", null, Locale.ENGLISH));
        System.out.println(messageSource.getMessage("label_welcome", null, Locale.ENGLISH));
        return "hello_world";
    }
}
