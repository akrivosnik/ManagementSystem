package com.example.model;

import java.util.List;

public class Student {
    private int id;
    private String fullName;
    private String email;
    private List<Integer> registeredCourses;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getRegisteredCourses() {
        return registeredCourses;
    }

    public void setRegisteredCourses(List<Integer> registeredCourses) {
        this.registeredCourses = registeredCourses;
    }

}
