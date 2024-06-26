package com.alexdouble.simpleweb.entity;

import jakarta.persistence.*;

@Entity
@Table(name="Person")
public class Person {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_person")
    private Integer idPerson;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "departmant_id")
    private Departmant departmant;
    @Column(name = "salary")
    private Integer salary;
    public Person() {
    }

    public void setIdPerson(Integer idPerson) {
        this.idPerson = idPerson;
    }
    public Integer getIdPerson() {
        return idPerson;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Departmant getDepartmant() {
        return departmant;
    }
    public void setDepartmant(Departmant departmant) {
        this.departmant = departmant;
    }
    public Integer getSalary() {
        return salary;
    }
    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}
