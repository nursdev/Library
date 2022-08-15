package com.library.services;

import com.library.models.Book;
import com.library.models.Person;
import com.library.repositories.BooksRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.TemporalField;
import java.util.List;
import java.util.Optional;

/**
 * @author Nurs Tech
 * @project library;
 */
@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Book findById(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setId(id);
        booksRepository.save(book);
    }

    @Transactional
    public void deleteById(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    public Optional<Person> getOwner(Book book) {
        Optional<Person> owner = Optional.ofNullable(book.getOwner());
        return owner;
    }

    @Transactional
    public void removeOwner(int id) {
        Book book = booksRepository.findById(id).get();
        book.getOwner().getBooks().remove(book);
        book.setOwner(null);
        book.setTookAt(null);
    }

    @Transactional
    public void setOwner(Person person, int bookId) {
        Book book = findById(bookId);
        System.out.println("Book: " + book);
        book.setOwner(person);
    }

    public List<Book> getBookStartingWith(String startWith) {
        return booksRepository.findByTitleStartingWith(startWith);
    }

    public List<Book> getBookBySortingYear() {
        return booksRepository.findByOrderByYear();
    }
}
