package com.shiba.springbootmongodb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CityPopulationDTO {
    @Schema(description = "City name", example = "New York")
    String city;
    @Schema(description = "City population", example = "8419600")
    long population;
}
