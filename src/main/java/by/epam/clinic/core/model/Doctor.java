package by.epam.clinic.core.model;

import java.util.Objects;

public class Doctor {
    private long id;
    private String name;
    private String surname;
    private String lastname;
    private String specialization;
    private String category;
    private long userId;
    private long departmentId;
    private String imagePath;

    public Doctor() {
    }

    public Doctor(long id, String name, String surname, String lastname,
                  String specialization, String category, long userId, long departmentId, String imagePath) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.specialization = specialization;
        this.category = category;
        this.userId = userId;
        this.departmentId = departmentId;
        this.imagePath = imagePath;
    }

    public Doctor(String name, String surname, String lastname, String specialization, String category, long departmentId) {
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.specialization = specialization;
        this.category = category;
        this.departmentId = departmentId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return getId() == doctor.getId() &&
                getUserId() == doctor.getUserId() &&
                getDepartmentId() == doctor.getDepartmentId() &&
                Objects.equals(getName(), doctor.getName()) &&
                Objects.equals(getSurname(), doctor.getSurname()) &&
                Objects.equals(getLastname(), doctor.getLastname()) &&
                Objects.equals(getSpecialization(), doctor.getSpecialization()) &&
                Objects.equals(getCategory(), doctor.getCategory()) &&
                Objects.equals(getImagePath(), doctor.getImagePath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurname(), getLastname(), getSpecialization(), getCategory(), getUserId(), getDepartmentId(), getImagePath());
    }
}
