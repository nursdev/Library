package com.library.controllers;

import com.library.models.Book;
import com.library.models.Person;
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
@RequestMapping("people")
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("{id}")
    public String information(@PathVariable("id") int id, Model model) {

        model.addAttribute("person", peopleService.findById(id));

        List<Book> books = peopleService.getBooks(id);
        model.addAttribute("books", peopleService.checkOverdue(books));
        return "people/info";
    }

    @GetMapping("{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model) {

        Person person = peopleService.findById(id);
        model.addAttribute("person", person);

        return "people/edit";
    }

    @PatchMapping("{id}")
    public String edit(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        peopleService.update(person, id);
        return "redirect:/people";
    }

    @GetMapping("/new")
    public String createPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String index(@ModelAttribute("person") Person person) {
        peopleService.save(person);
        return "redirect:/people";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }

}
