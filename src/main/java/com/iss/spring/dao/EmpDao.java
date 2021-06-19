package com.iss.spring.dao;

import com.iss.spring.model.Emp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
// 要么在每个接口加，要么在 SpringBootProjectApplicatoon 加 @MapperScan
public interface EmpDao {

    void insert(Emp emp);

    int myInsert(Emp emp);

    int myInsert2(Emp emp);

    void update(Emp emp);

    void delete(int empno);

    List<Emp> getEmps();

    Emp login(String ename, String password);
}
