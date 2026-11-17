package com.example.demo;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String Hello(String name) {
        System.out.println("Hello Controller....Hello"+name);
        return "Hello "+name+" ~ ";

    }

    @RequestMapping("/request")
    public String request(HttpServletRequest request ) {
        //1.请求参数
        String name =  request.getParameter("name");
        String age =  request.getParameter("age");
        System.out.println("姓名"+name);
        System.out.println("年龄"+age);
        //2.请求路径
        String url =  request.getRequestURL().toString();
        String uri =  request.getRequestURI().toString();
        System.out.println("URL"+url);
        System.out.println("URI"+uri);
        //3.请求方式
        String method =  request.getMethod();
        System.out.println("method:"+method);
        //4.请求头
        String useragent =  request.getHeader("User-Agent");
        System.out.println("useragent:"+useragent);
        //5.请求查询字符
        String queryString = request.getQueryString();
        System.out.println("queryString:"+queryString);

        return "<h1>Hello ~ <h1>";
    }

}


