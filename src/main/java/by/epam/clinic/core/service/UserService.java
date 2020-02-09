package by.epam.clinic.core.service;

import by.epam.clinic.core.model.User;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.core.service.impl.UserServiceException;

import java.util.Optional;

public interface UserService {

    Optional<User> login(String login, String password) throws UserServiceException, ServiceException;

    Optional<User> findUser(long doctorId) throws ServiceException;
}
