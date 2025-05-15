package com.shiba.springbootmongodb.collection;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    @Schema(description = "Street address", example = "123 Main St")
    private String street;
    @Schema(description = "Sub-district", example = "Downtown")
    private String subDistrict;
    @Schema(description = "District", example = "Central")
    private String district;
    @Schema(description = "City", example = "New York")
    private String city;
}
