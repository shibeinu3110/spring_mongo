package com.shiba.springbootmongodb.collection;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Contact {

    private String email;
    private String phone;
    private String facebookUrl;
    private String linkedIn;
}
