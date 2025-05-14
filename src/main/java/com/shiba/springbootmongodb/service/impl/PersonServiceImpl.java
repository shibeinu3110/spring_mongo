package com.shiba.springbootmongodb.service.impl;

import com.shiba.springbootmongodb.collection.Person;
import com.shiba.springbootmongodb.repository.PersonRepository;
import com.shiba.springbootmongodb.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;



import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "PERSON-SERVICE")
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final MongoTemplate mongoTemplate;


    @Override
    public String savePerson(Person person) {
        Person savedPerson = personRepository.save(person);
        return savedPerson.getPersonId();
    }

    @Override
    public Person getPersonStartWithName(String name) {
        log.info("Getting person with name starting with: {}", name);
        return personRepository.findByFirstName(name);
    }

    @Override
    public List<Person> getPersonBetweenAge(int minAge, int maxAge) {
        return personRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Page<Person> getPersonByCriteria(String name, Integer minAge, Integer maxAge, String city, Pageable pageable) {
        log.info("Getting person with name: {}, minAge: {}, maxAge: {}, city: {}", name, minAge, maxAge, city);
        Query query = new Query().with(pageable);
        List<Criteria> criterias = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            // find by name regardless of case (upper or lower)
            criterias.add(Criteria.where("firstName").regex(name, "i"));
        }
        if(minAge!=null && maxAge!=null){
            criterias.add(Criteria.where("age").gte(minAge).lte(maxAge));
        }
        if(city!=null && !city.isEmpty()){
            criterias.add(Criteria.where("address.city").regex(city, "i"));
        }

        if(!criterias.isEmpty()) {
            Criteria criteria = new Criteria();
            criteria.andOperator(criterias.toArray(new Criteria[0]));
            query.addCriteria(criteria);
        }

        return PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Person.class), pageable,
                () -> mongoTemplate.count(Query.of(query).limit(0).skip(0), Person.class) // count total number of records
        );
    }

    @Override
    public List<Document> getOldestPersonByCity() {
        log.info("Getting oldest person by city");

        UnwindOperation unwindOperation = Aggregation.unwind("addresses");
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "age");
        GroupOperation groupOperation = Aggregation.group("addresses.city")
                .first(Aggregation.ROOT)
                .as("oldestPerson");

        Aggregation aggregation = Aggregation.newAggregation(unwindOperation, sortOperation, groupOperation);
        log.info("Aggregation pipeline: {}", aggregation.toString());
        return mongoTemplate.aggregate(aggregation, Person.class, Document.class).getMappedResults();
    }

    @Override
    public List<Document> getCityPopulation() {

        UnwindOperation unwindOperation = Aggregation.unwind("addresses");
        GroupOperation groupOperation = Aggregation.group("addresses.city")
                .count().as("population");
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "population");
        ProjectionOperation projectionOperation = Aggregation.project()
                .andExpression("_id").as("city")
                .andExpression("population").as("population")
                .andExclude("_id");
        Aggregation aggregation = Aggregation.newAggregation(unwindOperation, groupOperation, sortOperation, projectionOperation);

        return mongoTemplate.aggregate(aggregation, Person.class, Document.class).getMappedResults();
    }
}
