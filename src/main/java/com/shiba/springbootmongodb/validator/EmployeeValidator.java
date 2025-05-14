package com.shiba.springbootmongodb.validator;

import com.shiba.springbootmongodb.collection.Contact;
import com.shiba.springbootmongodb.collection.Employee;
import com.shiba.springbootmongodb.common.exception.ErrorMessages;
import com.shiba.springbootmongodb.common.exception.StandardException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
@Slf4j(topic = "EMPLOYEE-VALIDATOR")
@RequiredArgsConstructor
public class EmployeeValidator {
    private final MongoTemplate mongoTemplate;
    public void checkEmployee(Employee employee) {
        log.info("Validating employee: {}", employee);
        checkDuplicateContact(employee.getContact());
        checkDuplicatePostcode(employee.getPostcode());
        checkDuplicateEmployeeId(employee.getEmployeeId());
    }

    public void checkEmployeeToUpdate(Employee currentEmployee, Employee newEmployee) {
        log.info("Validating current employee: {}", currentEmployee);
        log.info("Validating employee to update: {}", newEmployee);
        // Check if the employee ID is valid
        if(!newEmployee.getEmployeeId().equals(currentEmployee.getEmployeeId())) {
            throw new StandardException(ErrorMessages.SAVE_DATABASE_ERROR, "Can't change employee ID when updating");
        }

        // Check if the employee phone number and email already exists
        if(!currentEmployee.getContact().equals(newEmployee.getContact())) {
            checkDuplicateContact(newEmployee.getContact());
        }
        if(!currentEmployee.getPostcode().equals(newEmployee.getPostcode())) {
            checkDuplicatePostcode(newEmployee.getPostcode());
        }
    }


    private void checkDuplicateContact(Contact contact) {
        log.info("Checking duplicate contact: {}", contact);
        boolean exitsMail = mongoTemplate.exists(
                new Query(Criteria.where("contact.email").is(contact.getEmail())),
                Employee.class
        );
        boolean exitsPhone = mongoTemplate.exists(
                new Query(Criteria.where("contact.phone").is(contact.getPhone())),
                Employee.class
        );
        if(exitsMail) {
            throw new StandardException(ErrorMessages.DUPLICATE, "Email already exists");
        }
        if(exitsPhone) {
            throw new StandardException(ErrorMessages.DUPLICATE, "Phone already exists");
        }
    }
    private void checkDuplicatePostcode(String postcode) {
        log.info("Checking duplicate postcode: {}", postcode);
        boolean exitsPostcode = mongoTemplate.exists(
                new Query(Criteria.where("postcode").is(postcode)),
                Employee.class
        );
        if(exitsPostcode) {
            throw new StandardException(ErrorMessages.DUPLICATE, "Postcode already exists");
        }
    }
    private void checkDuplicateEmployeeId(String employeeId) {
        log.info("Checking employee id: {}", employeeId);
        boolean exitsEmployeeId = mongoTemplate.exists(
                new Query(Criteria.where("employeeId").is(employeeId)),
                Employee.class
        );
        if(exitsEmployeeId) {
            throw new StandardException(ErrorMessages.DUPLICATE, "Employee id already exists");
        }
    }






    public void checkEmployeeId(String employeeId) {
        log.info("Checking employee id: {}", employeeId);
        boolean exitsEmployeeId = mongoTemplate.exists(
                new Query(Criteria.where("employeeId").is(employeeId)),
                Employee.class
        );
        if(!exitsEmployeeId) {
            throw new StandardException(ErrorMessages.NOT_FOUND, "Can't find employee with id: " + employeeId);
        }
    }

}
