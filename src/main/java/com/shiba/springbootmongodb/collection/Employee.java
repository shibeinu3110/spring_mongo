package com.shiba.springbootmongodb.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Employee collection")
public class Employee {
    @Id
    @Schema(description = "Employee ID", example = "EMP123456")
    private String employeeId;
    @Schema(description = "Employee first name", example = "John")
    private String firstName;
    @Schema(description = "Employee last name", example = "Doe")
    private String lastName;
    @Schema(description = "Employee age", example = "30")
    private Integer age;
    @Schema(description = "Employee postcode", example = "10003")
    private String postcode;
    @Schema(description = "Employee contact information")
    private Contact contact;
    @Schema(description = "Employee address list")
    private List<Address> addressList;
}
