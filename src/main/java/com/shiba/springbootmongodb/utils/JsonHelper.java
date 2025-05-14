package com.shiba.springbootmongodb.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonHelper {

    public static String toJson(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.setDateFormat(new SimpleDateFormat(DateHelper.GLOBAL_DATE));

            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            log.info("Fail toJson with errMsg = {}", ex.getMessage());
            return "{}";
        }
    }
}
