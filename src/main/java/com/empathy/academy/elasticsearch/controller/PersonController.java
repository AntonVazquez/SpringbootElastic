package com.empathy.academy.elasticsearch.controller;


import com.empathy.academy.elasticsearch.document.Person;
import com.empathy.academy.elasticsearch.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    private final PersonService service;

    @Autowired
    public PersonController(PersonService service) {
        this.service = service;
    }

    @PostMapping
    public void save(@RequestBody final Person person) {
        service.save(person);
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable final String id) {
        return service.findById(id);
    }

    @GetMapping("/search")
    public List<Person> search(@RequestParam final String query) {
        return service.searchByQuery(query);

    }
}
