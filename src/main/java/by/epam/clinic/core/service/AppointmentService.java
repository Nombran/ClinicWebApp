package by.epam.clinic.core.service;

import by.epam.clinic.core.model.Appointment;
import by.epam.clinic.core.model.User;
import by.epam.clinic.core.service.impl.ServiceException;

import java.util.List;

public interface AppointmentService {

    boolean createAppointment(Appointment appointment, long userId) throws ServiceException;

    List<Appointment> findDoctorAppointments(User user) throws ServiceException;

    List<Appointment> getDoctorsFreeAppointments(long doctorId) throws ServiceException;

    boolean deleteAppointment(long appointmentId, long userId) throws ServiceException;

    boolean makeAppointment(long userId, long appointmentId, String purpose) throws ServiceException;

    void deleteTicketReservation(long userId, long appointmentId) throws ServiceException;
}
