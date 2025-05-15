package com.shiba.springbootmongodb.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.shiba.springbootmongodb.collection.Employee;
import com.shiba.springbootmongodb.dto.CityPopulationDTO;
import com.shiba.springbootmongodb.repository.EmployeeRepository;
import com.shiba.springbootmongodb.service.EmployeeService;
import com.shiba.springbootmongodb.validator.EmployeeValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Employee> getEmployeesBetweenAge(int minAge, int maxAge) {
        Query query = new Query();
        Criteria criteria = Criteria.where("age").gte(minAge).lte(maxAge);
        query.addCriteria(criteria);
        return mongoTemplate.find(query, Employee.class);
    }

    @Override
    public List<CityPopulationDTO> getCityPopulation() {
        UnwindOperation unwindOperation = Aggregation.unwind("addressList");

        //will duplicate if an employee has multiple addresses in the same city
        GroupOperation groupOperation = Aggregation.group("addressList.city", "employeeId");

        //we need to group by city only
        GroupOperation groupCityOperation = Aggregation.group("_id.city")
                .count().as("population");
        ProjectionOperation projectionOperation = Aggregation.project()
                .and("_id").as("city")
                .and("population").as("population")
                .andExclude("_id");

        Aggregation aggregation = Aggregation.newAggregation(unwindOperation, groupOperation, groupCityOperation, projectionOperation);
        return mongoTemplate.aggregate(aggregation, Employee.class, CityPopulationDTO.class).getMappedResults();
    }
}
