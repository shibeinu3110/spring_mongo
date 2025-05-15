package com.shiba.springbootmongodb.controller;

import com.shiba.springbootmongodb.collection.Employee;
import com.shiba.springbootmongodb.common.StandardResponse;
import com.shiba.springbootmongodb.dto.CityPopulationDTO;
import com.shiba.springbootmongodb.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j(topic = "EMPLOYEE-CONTROLLER")
@RestController
@RequestMapping("/api/v1/employee")
@Tag(name = "Employee controller API", description = "URLs to operate employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    @Operation(summary = "Create employee", description = "Create employee and save to database")
    @PostMapping
    public StandardResponse<Employee> saveEmployee(@RequestBody Employee employee) {
        log.info("Saving employee: {}", employee);
        return StandardResponse.build(employeeService.saveEmployee(employee));
    }

    @Operation(summary = "Delete employee", description = "Delete an employee from database by employeeId")
    @DeleteMapping("/{employeeId}")
    public StandardResponse<String> deleteEmployee(@PathVariable String employeeId) {
        log.info("Deleting employee with id: {}", employeeId);
        employeeService.deleteEmployee(employeeId);
        return StandardResponse.build("Employee deleted successfully");
    }

    @Operation(summary = "Get employee", description = "Get employee by employeeId")
    @GetMapping("/{employeeId}")
    public StandardResponse<Employee> getEmployeeById(@PathVariable String employeeId) {
        log.info("Getting employee with id: {}", employeeId);
        return StandardResponse.build(employeeService.getEmployeeById(employeeId), "Get employee successfully");
    }

    @Operation(summary = "Update employee", description = "Update existing employee by employeeId")
    @PutMapping("/{employeeId}")
    public StandardResponse<Employee> updateEmployee(@PathVariable String employeeId, @RequestBody Employee newEmployee) {
        log.info("Updating employee with id: {}", employeeId);
        return StandardResponse.build(employeeService.updateEmployee(employeeId, newEmployee), "Update employee successfully");
    }

    @Operation(summary = "Get employee between age", description = "Get employees between minAge and maxAge")
    @GetMapping("/between-age")
    public StandardResponse<List<Employee>> getEmployeesBetweenAge(@RequestParam int minAge, @RequestParam int maxAge) {
        log.info("Getting employees between age: {} and {}", minAge, maxAge);
        return StandardResponse.build(employeeService.getEmployeesBetweenAge(minAge, maxAge), "Get employees successfully");
    }

    @Operation(summary = "Population by city", description = "Statistics of each city and its population")
    @GetMapping("/city-population")
    public StandardResponse<List<CityPopulationDTO>> getEmployeesByCityPopulation() {
        log.info("Each city and its population");
        return StandardResponse.build(employeeService.getCityPopulation(), "Get city and population successfully");
    }



}
