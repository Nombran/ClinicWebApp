package by.epam.clinic.core.service.impl;

import by.epam.clinic.core.model.User;
import by.epam.clinic.core.pool.TransactionManager;
import by.epam.clinic.core.pool.TransactionManagerException;
import by.epam.clinic.core.repository.impl.RepositoryException;
import by.epam.clinic.core.repository.impl.UserRepository;
import by.epam.clinic.core.service.UserService;
import by.epam.clinic.core.specification.impl.FindUserByDoctorIdSpeification;
import by.epam.clinic.core.specification.impl.FindUserByLoginAndPasswordSpecification;
import by.epam.clinic.util.TextEncryptor;
import by.epam.clinic.util.TextEncryptorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static Logger logger = LogManager.getLogger();


    public Optional<User> login(String login, String password) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.init();
            String encryptedPassword = TextEncryptor.encrypt(password);
            FindUserByLoginAndPasswordSpecification specification = new FindUserByLoginAndPasswordSpecification(login, encryptedPassword);
            UserRepository repository = new UserRepository();
            transactionManager.setConnectionToRepository(repository);
            List<User> queryResult = repository.query(specification);
            if (queryResult.size() != 0) {
                return Optional.of(queryResult.get(0));
            } else {
                return Optional.empty();
            }
        } catch (TransactionManagerException e) {
            throw new ServiceException("Error in login",e);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository error",e);
        } catch (TextEncryptorException e) {
            throw new ServiceException("Error in encrypting password" ,e);
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e) {
                logger.error("Error in releasing connection", e);
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
                logger.error("Error in releasing connection", e);
            }
        }
    }

}
