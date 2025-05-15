package com.shiba.springbootmongodb.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CityPopulationDTO {
    String city;
    long population;
}
