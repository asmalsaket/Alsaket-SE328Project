package com.app.asma;

public class StudentModel {
    private Integer ID;
    private String Name, Surname, Fathers_name, National_ID, date_of_birth, Gender;

    public StudentModel(Integer ID, String name, String surname, String fathers_name, String national_ID, String date_of_birth, String gender) {
        this.ID = ID;
        Name = name;
        Surname = surname;
        Fathers_name = fathers_name;
        National_ID = national_ID;
        this.date_of_birth = date_of_birth;
        Gender = gender;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getFathers_name() {
        return Fathers_name;
    }

    public void setFathers_name(String fathers_name) {
        Fathers_name = fathers_name;
    }

    public String getNational_ID() {
        return National_ID;
    }

    public void setNational_ID(String national_ID) {
        National_ID = national_ID;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
