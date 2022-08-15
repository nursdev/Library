package com.library.services;

import com.library.models.Book;
import com.library.models.Person;
import com.library.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Nurs Tech
 * @project library;
 */
@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findById(int id) {
        return peopleRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(Person updatedPerson, int id) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public List<Book> getBooks(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        if(person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            return person.get().getBooks();
        }
        return Collections.emptyList();
    }

    public List<Book> checkOverdue(List<Book> books) {
        List<Book> bookList = new ArrayList<>(books);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            Date tookAt = book.getTookAt();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(tookAt);
            calendar.add(Calendar.DATE, 10);

            Date tookAtPlus10Days = calendar.getTime();
            Date now = new Date();

            if(tookAtPlus10Days.before(now) || tookAtPlus10Days.equals(now)) {
                book.setOverdue(true);
            } else book.setOverdue(false);

        }

        return bookList;
    }
}
