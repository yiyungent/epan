package com.stu.yun.service;

import com.stu.yun.model.Emp;

import java.util.List;

public interface EmpService {

    List<Emp> getEmps();

    Emp myInsert(Emp inputModel);

    int myInsert2(Emp inputModel);

    Emp login(String ename, String password);
}
