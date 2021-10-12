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
        List<Employee> employeeList = employeeService.show();

        return employeeList;
        //return employeeRepo.findAll();
    }
    @GetMapping("{id}")
    public Employee getOne(@PathVariable String id){// Employee employee){

        //System.out.println(employeeService.readOne(id));
        return employeeService.readOne(id);

    }
    @PostMapping
    public Employee create(@RequestBody Employee employee){
        return employeeService.create(employee);
        //return employeeRepo.save(employee);
    }
    @PutMapping("{id}")
    public Employee update(@RequestBody Employee employee, @PathVariable String id){
        return employeeService.update(employee, id);
        //BeanUtils.copyProperties(employee, employeeFromDb,"id");
        //return employeeRepo.save(employeeFromDb);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){// Employee employee){
        employeeService.delete(id);
        //employeeRepo.delete(employee);
    }
}
