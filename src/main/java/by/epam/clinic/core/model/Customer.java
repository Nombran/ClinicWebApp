package by.epam.clinic.core.model;

import java.time.LocalDate;
import java.util.Objects;

public class Customer {
    private long id;

    private String name;

    private String surname;

    private String lastname;

    private LocalDate birthday;

    private String phone;

    private long userId;

    public Customer(String name, String surname, String lastname, LocalDate birthday, String phone) {
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.phone = phone;
    }

    public Customer() {
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return getId() == customer.getId() &&
                getUserId() == customer.getUserId() &&
                Objects.equals(getName(), customer.getName()) &&
                Objects.equals(getSurname(), customer.getSurname()) &&
                Objects.equals(getLastname(), customer.getLastname()) &&
                Objects.equals(getBirthday(), customer.getBirthday()) &&
                Objects.equals(getPhone(), customer.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurname(), getLastname(), getBirthday(), getPhone(), getUserId());
    }
}
