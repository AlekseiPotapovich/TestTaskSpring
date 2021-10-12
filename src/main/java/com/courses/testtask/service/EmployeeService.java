package com.courses.testtask.service;

import com.courses.testtask.dao.EmployeeDao;
import com.courses.testtask.dto.Employee;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Service
public class EmployeeService {

    private String url = "jdbc:postgresql://localhost/employeedb";
    private String userName = "postgres";
    private String password = "1234";


    public EmployeeService() throws SQLException {
    }


    public Connection ConnectionDb() throws SQLException {
        return DriverManager.getConnection(url, userName, password);
    }

    private final EmployeeDao employeeDao = new EmployeeDao(ConnectionDb());

    public Employee readOne(String id){
        return employeeDao.read(id);
    }

    public Employee create(Employee employee){
        return employeeDao.create(employee);
    }
    public boolean delete(String id){
        return employeeDao.delete(id);
    }
    public Employee update(Employee employee, String id){
        return employeeDao.update(employee, id);

    }
    public List<Employee> show(){
        return employeeDao.getAll();
    }


}
