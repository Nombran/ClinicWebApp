package by.epam.clinic.core.service;

import by.epam.clinic.core.model.Customer;
import by.epam.clinic.core.model.User;
import by.epam.clinic.core.service.impl.ServiceException;

import java.util.List;

/**
 * Interface represents customer requests processing logic
 */
public interface CustomerService {

    /**
     * Method defines logic of creating new customer.
     *
     * @param user     represents info about new account.
     * @param customer represents personal customer info.
     * @return the boolean as a result of creating customer.
     * @throws ServiceException if an error occurs while creating customer.
     */
    boolean createCustomer(User user, Customer customer) throws ServiceException;

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
