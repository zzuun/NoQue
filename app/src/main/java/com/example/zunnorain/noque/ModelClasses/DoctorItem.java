package com.example.zunnorain.noque.ModelClasses;

public class DoctorItem {

    private String name;
    private String specialization;
    private String id;

    public DoctorItem() {
    }

    public DoctorItem(String name, String specialization, String id) {
        this.name = name;
        this.specialization = specialization;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
