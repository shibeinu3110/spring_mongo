package com.shiba.springbootmongodb.service;

import com.shiba.springbootmongodb.collection.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.bson.Document;
import java.util.List;

public interface PersonService {
    String savePerson(Person person);

    Person getPersonStartWithName(String name);

    List<Person> getPersonBetweenAge(int minAge, int maxAge);

    Page<Person> getPersonByCriteria(String name, Integer minAge, Integer maxAge, String city, Pageable pageable);

    List<Document> getOldestPersonByCity();

    List<Document> getCityPopulation();
}
