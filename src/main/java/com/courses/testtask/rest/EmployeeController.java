package com.courses.testtask.rest;

import com.courses.testtask.dto.Employee;
import com.courses.testtask.repos.EmployeeRepo;
import com.courses.testtask.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    private final EmployeeRepo employeeRepo;
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeRepo employeeRepo, EmployeeService employeeService) {
        this.employeeRepo = employeeRepo;
        this.employeeService = employeeService;
    }

    @GetMapping()
    public List<Employee> list(){
        List<Employee> emp = employeeService.show();

        //return emp;
        return employeeRepo.findAll();
    }
    @GetMapping("{id}")
    public Employee getOne(@PathVariable String id){
        return employeeService.readOne(id);
    }
    @PostMapping
    public boolean create(@RequestBody Employee employee){
        return employeeService.create(employee);
        //return employeeRepo.save(employee);
    }
    @PutMapping("{id}")
    public Employee update(@PathVariable("id") Employee employeeFromDb, @RequestBody Employee employee){
        return employeeService.update(employee);
        //BeanUtils.copyProperties(employee, employeeFromDb);
        //return employeeRepo.save(employeeFromDb);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        employeeService.delete(id);
        //employeeRepo.delete(employee);
    }
}
