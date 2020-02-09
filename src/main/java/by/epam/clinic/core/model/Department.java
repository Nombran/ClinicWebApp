package by.epam.clinic.core.model;

import java.util.Objects;

public class Department {
    private long id;
    private String name;
    private String description;
    private String phone;
    private String imagePath;

    public Department(String name, String description, String phone, String imagePath) {
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.imagePath = imagePath;
    }

    public Department() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        Department that = (Department) o;
        return getId() == that.getId() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getImagePath(), that.getImagePath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getPhone(), getImagePath());
    }
}
