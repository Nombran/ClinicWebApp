package by.epam.clinic.core.service;

import by.epam.clinic.core.model.Appointment;
import by.epam.clinic.core.model.Doctor;
import by.epam.clinic.core.model.User;
import by.epam.clinic.core.service.impl.ServiceException;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Optional;

public interface DoctorService {

    boolean createDoctor(User user, Doctor doctor, String servletContextPath, Part imageFile) throws ServiceException;

    List<Doctor> getDoctorsByDepartmentId(long id) throws ServiceException;

    List<Appointment> findDoctorFutureAppointments(long userId) throws ServiceException;

    List<Doctor> findAllDoctors() throws ServiceException;

    void deleteDoctor(long id) throws ServiceException;

    Optional<Doctor> findDoctor(long id) throws ServiceException;

    void updateDoctor(User user, Doctor doctor, String servletContextPath, Part imageFile)
            throws ServiceException;
}
