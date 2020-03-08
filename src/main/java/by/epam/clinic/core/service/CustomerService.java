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
}
