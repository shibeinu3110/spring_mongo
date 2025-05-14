package com.shiba.springbootmongodb.controller;

import com.shiba.springbootmongodb.collection.Person;
import com.shiba.springbootmongodb.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import org.bson.Document;
import java.util.List;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;
    @PostMapping
    public String savePerson(@RequestBody  Person person) {
        personService.savePerson(person);
        return "Person saved successfully";
    }
    @GetMapping
    public Person getPersonStartWithName(@RequestParam("name") String name) {
        return personService.getPersonStartWithName(name);
    }
    @GetMapping("/between")
    public List<Person> getPersonBetweenAge(@RequestParam("start") int minAge, @RequestParam("end") int maxAge) {
        return personService.getPersonBetweenAge(minAge, maxAge);
    }

    @GetMapping("/criteria")
    public Page<Person> getPersonByCriteria(@RequestParam(value = "name", required = false) String name,
                                            @RequestParam(value = "minAge", required = false) Integer minAge,
                                            @RequestParam(value = "maxAge", required = false) Integer maxAge,
                                            @RequestParam(value = "city", required = false) String city,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size

    ) {
        Pageable pageable = PageRequest.of(page, size);
        return personService.getPersonByCriteria(name, minAge, maxAge, city, pageable);
    }

    @GetMapping("/oldest")
    public List<Document> getOldestPersonByCity() {
        return personService.getOldestPersonByCity();
    }

    @GetMapping("/cityPopulation")
    public List<Document> getCityPopulation() {
        return personService.getCityPopulation();
    }
}
