package com.library.repositories;

import com.library.models.Book;
import com.library.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nurs Tech
 * @project library;
 */
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {}
