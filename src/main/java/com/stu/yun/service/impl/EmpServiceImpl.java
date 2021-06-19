package com.stu.yun.service.impl;

import com.stu.yun.dao.EmpDao;
import com.stu.yun.model.Emp;
import com.stu.yun.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpDao empDao;

    @Override
    public List<Emp> getEmps() {
        return this.empDao.getEmps();
    }

    @Override
    public Emp myInsert(Emp inputModel) {
        int empno = this.empDao.myInsert(inputModel);
        inputModel.setEmpno(empno);
        return inputModel;
    }

    @Override
    public int myInsert2(Emp inputModel) {
        int rowNum = this.empDao.myInsert2(inputModel);
        System.out.println("emp 受影响行数: " + rowNum);
        return rowNum;
    }

    @Override
    public Emp login(String ename, String password) {
        return this.empDao.login(ename, password);
    }


}
