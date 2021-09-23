package com.courses.testtask.dao;

import com.courses.testtask.dto.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao implements DAO<Employee, String> {
    private final Connection connection;

    enum SQLRequest {
        GET("SELECT * FROM employee WHERE employee_id = (?)"),
        INSERT("INSERT INTO employee (employee_id, first_name, last_name, department_id, job_title, gender, date_of_birth) " +
                "VALUES ((?), (?), (?), (?), (?), (?), (?)) RETURNING employee_id"),
        DELETE("DELETE FROM employee WHERE employee_id = (?) RETURNING employee_id"),
        UPDATE("UPDATE employee SET first_name = (?), last_name = (?), department_id = (?), job_title = (?)," +
                "gender = (?), data_of_birth = (?) WHERE employee_id = (?) RETURNING employee_id"),
        SHOW("SELECT * FROM employee");

        String QUERY;

        SQLRequest(String QUERY) {
            this.QUERY = QUERY;
        }
    }

    public EmployeeDao(Connection connection) {
        this.connection = connection;
    }
//    public void countOfTable(){
//        try (Statement statement = connection.createStatement()) {
//            final ResultSet rs = statement.executeQuery(SQLRequest.SHOW.QUERY);
//            int count = rs.getInt("employee_id");
//            System.out.println("COUNT = " + count);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println();
//    }
    private boolean ma( Employee employee, String request){
        boolean result = false;
        int gender = 1;
        try (PreparedStatement statement = connection.prepareStatement(request)) {
            if(employee.getGender().equals("MALE"))
                gender = 0;
            statement.setLong(1, employee.getEmployee_id());
            statement.setString(2, employee.getFirst_name());
            statement.setString(3, employee.getLast_name());
            statement.setInt(4, employee.getDepartment_id());
            statement.setString(5, employee.getJob_title());
            statement.setInt(6, gender);
            statement.setString(7, employee.getDate_of_birth());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean create(Employee employee) {
//        boolean result = false;
//        int gender = 1;
//        try (PreparedStatement statement = connection.prepareStatement(SQLRequest.INSERT.QUERY)) {
//            if(employee.getGender().equals("MALE"))
//                gender = 0;
//            statement.setLong(1, employee.getEmployee_id());
//            statement.setString(2, employee.getFirst_name());
//            statement.setString(3, employee.getLast_name());
//            statement.setInt(4, employee.getDepartment_id());
//            statement.setString(5, employee.getJob_title());
//            statement.setInt(6, gender);
//            statement.setString(7, employee.getDate_of_birth());
//            result = statement.executeQuery().next();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return ma(employee, SQLRequest.INSERT.QUERY);
    }

    @Override
    public Employee read(String  id) {
        final Employee employee = new Employee();
        employee.setEmployee_id(-1L);
        try (PreparedStatement statement = connection.prepareStatement(SQLRequest.GET.QUERY)) {

            statement.setLong(1, Long.parseLong(id));
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                employee.setEmployee_id(rs.getLong("employee_id"));
                employee.setFirst_name(rs.getString("first_name"));
                employee.setLast_name(rs.getString("last_name"));
                employee.setDepartment_id(rs.getInt("department_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(employee.getFirst_name());
        return employee;
    }
    public List<Employee> getAll(){
        List<Employee> employees = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQLRequest.SHOW.QUERY)) {

            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployee_id(rs.getLong("employee_id"));
                employee.setFirst_name(rs.getString("first_name"));
                employee.setLast_name(rs.getString("last_name"));
                employee.setDepartment_id(rs.getInt("department_id"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(employees);
        return employees;
    }

    @Override
    public Employee update(Employee employee) {
//        boolean result = false;
//
//        try (PreparedStatement statement = connection.prepareStatement(SQLRequest.UPDATE.QUERY)) {
//            statement.setString(1, employee.getFirst_name());
//            statement.setString(2, employee.getLast_name());
//            statement.setString(3, employee.getLast_name());
//            statement.setString(4, employee.getLast_name());
//            statement.setString(5, employee.getLast_name());
//            statement.setString(6, employee.getLast_name());
//            statement.setString(7, employee.getLast_name());
//            result = statement.executeQuery().next();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        ma(employee, SQLRequest.UPDATE.QUERY);
        return read(employee.getEmployee_id().toString());
    }

    @Override
    public boolean delete(String id) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQLRequest.DELETE.QUERY)) {
            statement.setLong(1, Long.parseLong(id));
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
