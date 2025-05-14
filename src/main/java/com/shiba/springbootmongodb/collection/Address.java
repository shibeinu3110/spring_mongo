package com.shiba.springbootmongodb.collection;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    private String street;
    private String subDistrict;
    private String district;
    private String city;
}
