package by.epam.clinic.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Appointment  {
    private long id;
    private long doctorId;
    private long customerId;
    private String purpose;
    private LocalDateTime dateTime;

    public Appointment(long doctorId, long customerId, String purpose, LocalDateTime dateTime) {
        this.doctorId = doctorId;
        this.customerId = customerId;
        this.purpose = purpose;
        this.dateTime = dateTime;
    }

    public Appointment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getPurpose() {
        return purpose;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return getId() == that.getId() &&
                getDoctorId() == that.getDoctorId() &&
                getCustomerId() == that.getCustomerId() &&
                Objects.equals(getPurpose(), that.getPurpose()) &&
                Objects.equals(getDateTime(), that.getDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDoctorId(), getCustomerId(), getPurpose(), getDateTime());
    }
}
