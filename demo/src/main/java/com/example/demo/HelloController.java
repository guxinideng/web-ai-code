package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String Hello(String name) {
        System.out.println("Hello Controller....Hello"+name);
        return "Hello "+name+" ~ ";

    }

}
