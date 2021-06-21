package com.stu.yun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {


    @GetMapping("login")
    public String login() {
        return "login.html";
    }

    @GetMapping("register")
    public String register() {
        return "register.html";
    }

    @GetMapping("exit")
    public String exit(HttpSession session) {
        session.setAttribute("user", null);

        return "login.html";
    }

    @GetMapping("/")
    public String index(){
        return "index.html";
    }

}
