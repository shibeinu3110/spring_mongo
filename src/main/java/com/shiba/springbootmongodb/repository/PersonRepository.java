package com.shiba.springbootmongodb.repository;

import com.shiba.springbootmongodb.collection.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {
    Person findByFirstName(String name);
    List<Person> findByAgeBetween(int minAge, int maxAge);
}
