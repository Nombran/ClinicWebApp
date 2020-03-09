package by.epam.clinic.core.service;

import by.epam.clinic.core.model.Appointment;
import by.epam.clinic.core.model.Doctor;
import by.epam.clinic.core.model.User;
import by.epam.clinic.core.service.impl.ServiceException;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Optional;

/**
 * Interface represents doctors's processing logic
 */
public interface DoctorService {

    /**
     * Method defines logic of creating new doctor.
     *
     * @param user               represents doctor account info.
     * @param doctor             represents doctor personal info.
     * @param servletContextPath the servlet context path.
     * @param imageFile          the image file.
     * @return the result of creating.
     * @throws ServiceException if an error occurs while creating a doctor.
     */
    boolean createDoctor(User user, Doctor doctor, String servletContextPath, Part imageFile) throws ServiceException;

    /**
     * Method defines logic of getting all doctors by their working
     * department.
     *
     * @param id represents primary key of departments database table.
     * @return the list of doctors.
     * @throws ServiceException if an error occurs while getting doctors.
     */
    List<Doctor> getDoctorsByDepartmentId(long id) throws ServiceException;

    /**
     * Method defines logic of getting doctor's future appointments.
     *
     * @param userId represents primary key in user's database table.
     * @return the list of appointments
     * @throws ServiceException if error occurs while getting appointments.
     */
    List<Appointment> findDoctorFutureAppointments(long userId) throws ServiceException;

    /**
     * Method defines logic of getting all doctors.
     *
     * @return the list of doctors.
     * @throws ServiceException if ann error occurs while getting doctors.
     */
    List<Doctor> findAllDoctors() throws ServiceException;

    /**
     * Method defines logic of deleting doctor by his id.
     *
     * @param id represents primary key in doctor's database table.
     * @return the boolean as a result of deleting doctor.
     * @throws ServiceException if an error occurs while deleting doctor.
     */
    boolean deleteDoctor(long id) throws ServiceException;

    /**
     * Method defines logic of getting doctor by his id.
     *
     * @param id represents primary key in doctor's database table.
     * @return the optional, which can contains doctor.
     * @throws ServiceException if an error occurs while getting a doctor.
     */
    Optional<Doctor> findDoctor(long id) throws ServiceException;

    /**
     * Method defines logic of updating doctor's info.
     *
     * @param user               the user.
     * @param doctor             the doctor.
     * @param servletContextPath the servlet context path.
     * @param imageFile          the image file.
     * @return the result of updating.
     * @throws ServiceException if error occurs while updating doctor.
     */
    boolean updateDoctor(User user, Doctor doctor, String servletContextPath, Part imageFile)
            throws ServiceException;
}
