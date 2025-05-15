package com.shiba.springbootmongodb.collection;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Contact collection")
public class Contact {
    @Schema(description = "User email", example = "huyhungshiba@gmail.com")
    private String email;
    @Schema(description = "User phone number", example = "0123456789")
    private String phone;
    @Schema(description = "User website", example = "https://shiba.com")
    private String facebookUrl;
    @Schema(description = "User website", example = "https://shiba.com")
    private String linkedIn;
}
