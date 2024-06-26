package com.alexdouble.simpleweb.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Departmant")
public class Departmant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Departmant")
    private Integer idDepartmant;
    @Column(name = "name_Departmant")
    private String nameDepartmant;


    public Departmant() {
    }

    public void setIdDepartmant(Integer idDepartmant) {
        this.idDepartmant = idDepartmant;
    }

    public void setNameDepartmant(String nameDepartmant) {
        this.nameDepartmant = nameDepartmant;
    }

    public Integer getIdDepartmant() {
        return idDepartmant;
    }

    public String getNameDepartmant() {
        return nameDepartmant;
    }

}
