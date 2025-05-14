package com.shiba.springbootmongodb.service.impl;

import com.shiba.springbootmongodb.collection.Employee;
import com.shiba.springbootmongodb.repository.EmployeeRepository;
import com.shiba.springbootmongodb.service.EmployeeService;
import com.shiba.springbootmongodb.validator.EmployeeValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "EMPLOYEE-SERVICE")
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeValidator employeeValidator;
    private final EmployeeRepository  employeeRepository;
    private final MongoTemplate mongoTemplate;
    @Override
    public Employee saveEmployee(Employee employee) {
        employeeValidator.checkEmployee(employee);
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(String employeeId) {
        log.info("Deleting employee with id: {}", employeeId);
        employeeValidator.checkEmployeeId(employeeId);
        employeeRepository.deleteEmployeeByEmployeeId(employeeId);
    }

    @Override
    public Employee getEmployeeById(String employeeId) {
        log.info("Getting employee with id: {}", employeeId);
        employeeValidator.checkEmployeeId(employeeId);
        return employeeRepository.findByEmployeeId(employeeId);
    }

    @Override
    public Employee updateEmployee(String employeeId, Employee newEmployee) {
        log.info("Updating employee with id: {}", employeeId);
        employeeValidator.checkEmployeeId(employeeId);
        Employee currentEmployee = employeeRepository.findByEmployeeId(employeeId);
        employeeValidator.checkEmployeeToUpdate(currentEmployee, newEmployee);

        return mongoTemplate.save(newEmployee);
    }
}
