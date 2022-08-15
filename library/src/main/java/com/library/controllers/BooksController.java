package com.library.controllers;

import com.library.models.Book;
import com.library.models.Person;
import com.library.services.BooksService;
import com.library.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Nurs Tech
 * @project library;
 */
@Controller
@RequestMapping("books")
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }


    @GetMapping()
    public String index(Model model, @RequestParam(value = "sort_by_year", required = false) boolean isSort) {

        if(isSort) {
            model.addAttribute("books", booksService.getBookBySortingYear());
        } else {
            model.addAttribute("books", booksService.findAll());
        }


        return "books/index";
    }

    @GetMapping("{id}")
    public String information(@PathVariable("id") int id, Model model,
                              @ModelAttribute("person") Person person) {
        Book book = booksService.findById(id);
        model.addAttribute("book", book);

        Optional<Person> owner = booksService.getOwner(book);
        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get());
        } else {
            model.addAttribute("people", peopleService.findAll());
        }

        return "books/info";
    }

    @GetMapping("{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model) {

        model.addAttribute("book", booksService.findById(id));

        return "books/edit";
    }

    @PatchMapping("{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("book") Book book) {
        System.out.println(book);
        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("{id}/delete")
    public String deleteBook(@PathVariable("id") int id) {
        booksService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("new")
    public String create(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping("new")
    public String createBook(@ModelAttribute("book") Book book) {
        booksService.save(book);
        return "redirect:/books";
    }

    @PatchMapping("{id}/release")
    public String releaseBook(@PathVariable("id") int id) {
        booksService.removeOwner(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("{id}/appoint")
    public String setOwnerForBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {

        System.out.println(person);

        booksService.setOwner(peopleService.findById(person.getId()), id);

        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String searchBook(@RequestParam(value = "startingWith", required = false) String startingWith,
                             Model model) {
        List<Book> books = booksService.getBookStartingWith(startingWith);
        System.out.println(startingWith);
        System.out.println(books);
        model.addAttribute("books", books);

        return "books/search";
    }

}
