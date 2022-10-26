package com.empathy.academy.elasticsearch.service;


import com.empathy.academy.elasticsearch.document.Person;
import com.empathy.academy.elasticsearch.repository.PersonRepository;
import org.elasticsearch.index.query.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service
public class PersonService {

    private final PersonRepository repository;
    private final ElasticsearchOperations elasticsearchTemplate;

    public PersonService(PersonRepository repository, ElasticsearchOperations elasticsearchTemplate) {
        this.repository = repository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    public void save(final Person person) {
        repository.save(person);
    }

    public Person findById(final String id) {
        return repository.findById(id).orElse(null);
    }

    public List<Person> searchByQuery(final String query) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("name", query).operator(Operator.AND))
                .build();
        SearchHits<Person> people = elasticsearchTemplate.search(searchQuery, Person.class, IndexCoordinates.of("person"));

        return people.stream().map(hit -> hit.getContent() ).collect(Collectors.toList());
    }
}

