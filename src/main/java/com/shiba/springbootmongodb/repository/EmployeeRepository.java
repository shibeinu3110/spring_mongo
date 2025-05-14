package com.shiba.springbootmongodb.repository;

import com.shiba.springbootmongodb.collection.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    void deleteEmployeeByEmployeeId(String employeeId);
    Employee findByEmployeeId(String employeeId);
}
