package com.iss.spring.service;

import com.iss.spring.model.Emp;

import java.util.List;

public interface EmpService {

    List<Emp> getEmps();

    Emp myInsert(Emp inputModel);

    int myInsert2(Emp inputModel);

    Emp login(String ename, String password);
}
