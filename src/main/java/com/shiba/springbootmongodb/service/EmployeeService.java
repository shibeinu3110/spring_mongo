package com.shiba.springbootmongodb.service;

import com.shiba.springbootmongodb.collection.Employee;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    void deleteEmployee(String employeeId);

    Employee getEmployeeById(String employeeId);
    Employee updateEmployee(String employeeId, Employee newEmployee);
}
