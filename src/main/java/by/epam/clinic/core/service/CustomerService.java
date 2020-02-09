package by.epam.clinic.core.service;

import by.epam.clinic.core.model.Customer;
import by.epam.clinic.core.model.User;
import by.epam.clinic.core.service.impl.ServiceException;

import java.util.List;

public interface CustomerService {

    boolean createCustomer(User user, Customer customer) throws ServiceException;

    List[] findActiveAppointments(long userId) throws ServiceException;
}
