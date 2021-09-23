package com.courses.testtask.repos;

import com.courses.testtask.dto.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
}
