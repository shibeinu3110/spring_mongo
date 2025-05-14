package com.shiba.springbootmongodb.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "employee")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee {
    @Id
    private String employeeId;
    private String firstName;
    private String lastName;
    private Integer age;
    private String postcode;
    private Contact contact;
    private List<Address> addressList;
}
