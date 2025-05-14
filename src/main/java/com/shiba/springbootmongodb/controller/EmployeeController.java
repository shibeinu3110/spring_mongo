package com.shiba.springbootmongodb.controller;

import com.shiba.springbootmongodb.collection.Employee;
import com.shiba.springbootmongodb.common.StandardResponse;
import com.shiba.springbootmongodb.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j(topic = "EMPLOYEE-CONTROLLER")
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    @PostMapping
    public StandardResponse<Employee> saveEmployee(@RequestBody Employee employee) {
        log.info("Saving employee: {}", employee);
        return StandardResponse.build(employeeService.saveEmployee(employee));
    }

    @DeleteMapping("/{employeeId}")
    public StandardResponse<String> deleteEmployee(@PathVariable String employeeId) {
        log.info("Deleting employee with id: {}", employeeId);
        employeeService.deleteEmployee(employeeId);
        return StandardResponse.build("Employee deleted successfully");
    }

    @GetMapping("/{employeeId}")
    public StandardResponse<Employee> getEmployeeById(@PathVariable String employeeId) {
        log.info("Getting employee with id: {}", employeeId);
        return StandardResponse.build(employeeService.getEmployeeById(employeeId), "Get employee successfully");
    }

    @PutMapping("/{employeeId}")
    public StandardResponse<Employee> updateEmployee(@PathVariable String employeeId, @RequestBody Employee newEmployee) {
        log.info("Updating employee with id: {}", employeeId);
        return StandardResponse.build(employeeService.updateEmployee(employeeId, newEmployee), "Update employee successfully");
    }

    @GetMapping("/between-age")
    public StandardResponse<List<Employee>> getEmployeesBetweenAge(@RequestParam int minAge, @RequestParam int maxAge) {
        log.info("Getting employees between age: {} and {}", minAge, maxAge);
        return StandardResponse.build(employeeService.getEmployeesBetweenAge(minAge, maxAge), "Get employees successfully");
    }




}
