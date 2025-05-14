package com.shiba.springbootmongodb.service;

import com.shiba.springbootmongodb.collection.Employee;

import java.util.List;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    void deleteEmployee(String employeeId);

    Employee getEmployeeById(String employeeId);
    Employee updateEmployee(String employeeId, Employee newEmployee);
    List<Employee> getEmployeesBetweenAge(int minAge, int maxAge);
}
