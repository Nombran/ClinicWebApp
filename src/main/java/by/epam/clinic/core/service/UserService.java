package by.epam.clinic.core.service;

import by.epam.clinic.core.model.User;
import by.epam.clinic.core.service.impl.ServiceException;

import java.util.Optional;

/**
 * Interface represents user's processing logic
 */
public interface UserService {

    /**
     * Method defines logic of login user into system.
     *
     * @param login    the user's login.
     * @param password the user's password.
     * @return the optional, which can contains user object.
     * @throws ServiceException if error occurs while login process.
     */
    Optional<User> login(String login, String password) throws ServiceException;

    /**
     * Method defines logic of getting user by doctor id.
     *
     * @param doctorId the doctor id.
     * @return the optional, which can contains user object.
     * @throws ServiceException if error occurs, while getting user.
     */
    Optional<User> findUser(long doctorId) throws ServiceException;
}
