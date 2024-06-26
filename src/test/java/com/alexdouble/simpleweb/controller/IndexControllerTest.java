package com.alexdouble.simpleweb.controller;

import com.alexdouble.simpleweb.entity.Departmant;
import com.alexdouble.simpleweb.entity.Person;
import com.alexdouble.simpleweb.repository.RepositoryPerson;
import com.alexdouble.simpleweb.repository.RepositoryDepartmant;
import com.alexdouble.simpleweb.services.UploadCSV;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class IndexControllerTest {
    @Mock
    private RepositoryPerson repositoryPerson;
    @Mock
    private RepositoryDepartmant repositoryDepartmant;
    @Mock
    private UploadCSV uploadCSV;
    @InjectMocks
    private IndexController indexController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
    }

    @Test
    void testIndex() throws Exception{
        Person person = new Person();
        List<Person> personList = Collections.singletonList(person);
        when(repositoryPerson.findAll()).thenReturn(personList);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("personList"))
                .andExpect(model().attribute("personList", personList));

        verify(repositoryPerson, times(1)).findAll();
    }

    @Test
    void testListPerson() throws Exception{
        mockMvc.perform(get("/listPerson"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void  testGetPersonById() throws Exception {
        int personId = 1;
        Person person = new Person();
        person.setIdPerson(personId);

        when(repositoryPerson.findById(personId)).thenReturn(Optional.of(person));

        mockMvc.perform(get("/person/{id}", personId))
                .andExpect(status().isOk())
                .andExpect(view().name("person"))
                .andExpect(model().attributeExists("person"))
                .andExpect(model().attribute("person", person));

        verify(repositoryPerson, times(1)).findById(personId);
    }

    @Test
    void testNewPerson() throws Exception{
        int personId = 1;
        Person person = new Person();
        person.setIdPerson(personId);

        Departmant departmant = new Departmant();
        List<Departmant> departmantList = Collections.singletonList(departmant);
        when(repositoryDepartmant.findAll()).thenReturn(departmantList);

        mockMvc.perform(get("/person/newPerson"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("person"))
                .andExpect(model().attributeExists("departmants"))
                .andExpect(view().name("addPerson"));

        verify(repositoryDepartmant, times(1)).findAll();
    }

    @Test
    void testSavePerson()  throws Exception{
        Person person = new Person();

        mockMvc.perform(post("/savePerson")
                .flashAttr("person", person))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(repositoryPerson, times(1)).save(person);
    }

    @Test
    void testDeletePersonById()  throws Exception{
        int personId = 1;

        mockMvc.perform(post("/person/delete/{id}", personId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(repositoryPerson, times(1)).deleteById(personId);
    }

    @Test
    void testEditPerson() throws Exception{
        int personId = 1;
        Person person = new Person();
        person.setIdPerson(personId);

        when(repositoryPerson.findById(personId)).thenReturn(Optional.of(person));

        Departmant departmant = new Departmant();
        List<Departmant> departmantList = Collections.singletonList(departmant);

        when(repositoryDepartmant.findAll()).thenReturn(departmantList);

        mockMvc.perform(get("/person/edit/{id}",personId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("person"))
                .andExpect(model().attributeExists("departmants"))
                .andExpect(model().attribute("person", person))
                .andExpect(model().attribute("departmants", departmantList))
                .andExpect(view().name("editPerson"));

        verify(repositoryDepartmant, times(1)).findAll();
    }

    @Test
    public void testEditPersonSave() throws Exception {
        int personId = 1;
        Person personInput = new Person();
        Person person = new Person();

        when(repositoryPerson.findById(personId)).thenReturn(java.util.Optional.of(person));

        mockMvc.perform(post("/person/edit/{id}", personId)
                        .flashAttr("personInput", personInput))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(repositoryPerson, times(1)).findById(personId);
        verify(repositoryPerson, times(1)).save(person);
    }

}