package com.alexdouble.simpleweb.repository;

import com.alexdouble.simpleweb.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryPerson extends JpaRepository<Person, Integer> {

   /* private List<Person> listPerson = new ArrayList<>();

    public RepositorePeson() {
        listPerson.add(new Person("Ivan","Petrov","IT", 150000));
        listPerson.add(new Person("Petr","Sidorov","office", 100000));
        listPerson.add(new Person("Olga","Rav","home", 500000));
    }


    public List<Person> getListPerson(){
        return listPerson;
    }

    public Person findPersonById(int idPerson){
        return listPerson.stream().filter(s->s.getIdPerson()==idPerson).findFirst().get();

    }*/
}
