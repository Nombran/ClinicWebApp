package by.epam.clinic.core.service;

import by.epam.clinic.core.model.Appointment;
import by.epam.clinic.core.model.User;
import by.epam.clinic.core.service.impl.ServiceException;

import java.util.List;

/**
 * Interface represents appointment's processing logic
 */
public interface AppointmentService{

    /**
     * Method defines logic of creating appointment by user id.
     * and appointments info
     *
     * @param appointment represents ticket information.
     * @param userId      represents user's id.
     * @return the boolean which describe the result of creating appointment.
     * @throws ServiceException if an error occurs while creating appointment.
     */
    boolean createAppointment(Appointment appointment, long userId) throws ServiceException;

    /**
     * Method defines logic of getting doctor's appointments.
     *
     * @param user represents doctor account information.
     * @return the list of doctor's appointments.
     * @throws ServiceException if an error occurs while getting appointments.
     */
    List<Appointment> findDoctorAppointments(User user) throws ServiceException;

    /**
     * Method defines logic of getting doctor's free appointments by {@link User} object.
     *
     * @param doctorId represents primary key in doctors database table.
     * @return the list of doctor's free appointments.
     * @throws ServiceException if an error occurs while getting appointments.
     */
    List<Appointment> getDoctorsFreeAppointments(long doctorId) throws ServiceException;

    /**
     * Method defines logic of deleting appointment by doctor id.
     *
     * @param appointmentId represents primary key in appointments database table.
     * @param userId        represents primary key in users database table.
     * @return the boolean as result of deleting.
     * @throws ServiceException if an error occurs while deleting appointment.
     */
    boolean deleteAppointment(long appointmentId, long userId) throws ServiceException;

    /**
     * Method represents logic of making appointment by customer.
     *
     * @param userId        represents primary key in users database table.
     * @param appointmentId represents primary key in appointments database table.
     * @param purpose       represents the purpose of the appointment.
     * @return the boolean as a result of reservation an appointment.
     * @throws ServiceException if an error occurs while making appointment.
     */
    boolean makeAppointment(long userId, long appointmentId, String purpose) throws ServiceException;

    /**
     * Method defines logic of deleting ticket reservation by user.
     *
     * @param userId        represents primary key in users database table.
     * @param appointmentId represents primary key in appointments database table.
     * @throws ServiceException if an error occurs while deleting reservation.
     */
    void deleteTicketReservation(long userId, long appointmentId) throws ServiceException;

    /**
     * Method defines logic of getting info about active user's appointments.
     *
     * @param userId represents primary key in users database table.
     * @return the list [ ] with two elements, where the first is a list of
     * active appointments, and the second is a list of doctors, contains
     * with customer's active tickets.
     * @throws ServiceException if an error occurs while getting appointments info.
     */
    List[] findActiveAppointments(long userId) throws ServiceException;
}
