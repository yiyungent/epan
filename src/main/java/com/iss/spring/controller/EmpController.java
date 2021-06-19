package com.iss.spring.controller;

import com.iss.spring.model.Emp;
import com.iss.spring.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/emp")
public class EmpController {

    @Autowired
    private EmpService empService;


    @PostMapping("login")
    @ResponseBody
    public Emp login(String ename, String password) {
        if (ename == null || ename.length() == 0) {
            System.out.println("用户名或密码错误，请重新登录");
            return null;
        }
        Emp emp = this.empService.login(ename, password);
        if (emp == null) {
            System.out.println("用户名或密码错误，请重新登录");
        } else {
            System.out.println("登录成功");
        }
        return emp;
    }

    @GetMapping("getEmps")
    @ResponseBody
    public List<Emp> getEmps() {
        return this.empService.getEmps();
    }

    @PostMapping("add")
    @ResponseBody
    public Emp add(@RequestBody Emp inputModel) {
        // 方法1
//        return this.empService.myInsert(inputModel);
        // 方法2
        this.empService.myInsert2(inputModel);
        // MyBatis自动赋值给 empno
        return inputModel;
    }


}
