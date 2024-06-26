package com.alexdouble.simpleweb.controller;

import com.alexdouble.simpleweb.entity.Person;
import com.alexdouble.simpleweb.repository.RepositoryPerson;
import com.alexdouble.simpleweb.repository.RepositoryDepartmant;
import com.alexdouble.simpleweb.services.UploadCSV;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@Controller
public class IndexController {
    private static final Logger LOGGER = Logger.getLogger(IndexController.class.getName());
    @Autowired
    private RepositoryPerson repositoryPerson;
    @Autowired
    private RepositoryDepartmant repositoryDepartmant;
    @Autowired
    private UploadCSV uploadCSV;

    @GetMapping("/")
    public String index(Model model){
        List<Person> personList = repositoryPerson.findAll(); // Получение списка объектов Person
        model.addAttribute("personList", personList);
        return "index";
    }
    @GetMapping("/listPerson")
    public String listPerson(Model model){
        return "redirect:/";
    }

    @GetMapping("/person/{id}")
    public String getPersonById(@PathVariable int id, Model model){
        model.addAttribute("person", repositoryPerson.findById(id).get());
        return "person";
    }

    @GetMapping("/person/newPerson")
    public String newPerson(Model model){
        model.addAttribute("person",new Person());
        model.addAttribute("departmants",repositoryDepartmant.findAll());
        return "addPerson";
    }


    @PostMapping("/savePerson")
    public String savePerson(@ModelAttribute Person person){
        repositoryPerson.save(person);
        return "redirect:/";
    }

    @PostMapping("/person/delete/{id}")
    public String deletePersonById(@PathVariable int id){
        repositoryPerson.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/person/edit/{id}")
    public String editPerson(@PathVariable int id, Model model){
        model.addAttribute("person", repositoryPerson.findById(id).get());
        model.addAttribute("departmants",repositoryDepartmant.findAll());
        return "editPerson";
    }

    @PostMapping("/person/edit/{id}")
    public String editPerson(@PathVariable int id, Person personInput){
        Person person = repositoryPerson.findById(id).get();
        person.setFirstName(personInput.getFirstName());
        person.setLastName(personInput.getLastName());
        person.setDepartmant(personInput.getDepartmant());
        person.setSalary(personInput.getSalary());
        repositoryPerson.save(person);
        return "redirect:/";
    }

    @PostMapping("/upload-csv")
    public String uploadCSV(@RequestParam("file") MultipartFile file) {
        uploadCSV.uploadCSVFile(file);
        return  "redirect:/";
    }



}
