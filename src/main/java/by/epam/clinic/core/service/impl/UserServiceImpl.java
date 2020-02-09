package by.epam.clinic.core.service.impl;

import by.epam.clinic.core.model.User;
import by.epam.clinic.core.pool.TransactionManager;
import by.epam.clinic.core.pool.TransactionManagerException;
import by.epam.clinic.core.repository.impl.RepositoryException;
import by.epam.clinic.core.repository.impl.UserRepository;
import by.epam.clinic.core.service.UserService;
import by.epam.clinic.core.specification.impl.FindUserByDoctorIdSpeification;
import by.epam.clinic.core.specification.impl.FindUserByLoginSpecification;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    public Optional<User> login(String login, String password) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.init();
            FindUserByLoginSpecification specification = new FindUserByLoginSpecification(login, password);
            UserRepository repository = new UserRepository();
            transactionManager.setConnectionToRepository(repository);
            List<User> queryResult = repository.query(specification);
            if (queryResult.size() != 0) {
                return Optional.of(queryResult.get(0));
            } else {
                return Optional.empty();
            }
        } catch (TransactionManagerException e) {
            e.printStackTrace();
            throw new ServiceException("Error in login",e);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository error",e);
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e) {
                //log
            }
        }
    }

    public Optional<User> findUser(long doctorId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.init();
            UserRepository repository = new UserRepository();
            transactionManager.setConnectionToRepository(repository);
            FindUserByDoctorIdSpeification specification
                    = new FindUserByDoctorIdSpeification(doctorId);
            List<User> queryResult = repository.query(specification);
            if(queryResult.size() != 0 ){
                User user = queryResult.get(0);
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (TransactionManagerException e) {
            throw new ServiceException("Transaction manager error", e);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository error",e);
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e) {
                //log
            }
        }
    }

}
