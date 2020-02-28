package by.epam.clinic.core.service.impl;

import by.epam.clinic.core.model.Appointment;
import by.epam.clinic.core.model.Customer;
import by.epam.clinic.core.model.Doctor;
import by.epam.clinic.core.model.User;
import by.epam.clinic.core.pool.TransactionManager;
import by.epam.clinic.core.pool.TransactionManagerException;
import by.epam.clinic.core.repository.impl.*;
import by.epam.clinic.core.service.CustomerService;
import by.epam.clinic.core.specification.impl.FindActiveAppointmentsByCustomerIdSpecification;
import by.epam.clinic.core.specification.impl.FindCustomerByUserIdSpecification;
import by.epam.clinic.core.specification.impl.FindDoctorsByCustomerIdSpecification;
import by.epam.clinic.util.TextEncryptor;
import by.epam.clinic.util.TextEncryptorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class CustomerServiceImpl implements CustomerService {
    private static Logger logger = LogManager.getLogger();

    public boolean createCustomer(User user, Customer customer) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.init();
            transactionManager.beginTransaction();
            UserRepository userRepository = new UserRepository();
            CustomerRepository customerRepository = new CustomerRepository();
            transactionManager.setConnectionToRepository(customerRepository , userRepository);
            String unencryptedPassword = user.getPassword();
            user.setPassword(TextEncryptor.encrypt(unencryptedPassword));
            userRepository.add(user);
            customer.setUserId(user.getId());
            customerRepository.add(customer);
            transactionManager.commitTransaction();
        } catch (TransactionManagerException e) {
            throw new ServiceException("Transaction manager error",e);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository error",e);
        } catch (TextEncryptorException e) {
           throw new ServiceException("Password encrypting error",e);
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e) {
                logger.error("Error in releasing connection", e);
            }
        }
        return true;
    }

    public List[] findActiveAppointments(long userId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.init();
            CustomerRepository customerRepo = new CustomerRepository();
            DoctorRepository doctorRepo = new DoctorRepository();
            AppointmentRepository appointmentRepo = new AppointmentRepository();
            transactionManager.setConnectionToRepository(customerRepo, doctorRepo, appointmentRepo);
            FindCustomerByUserIdSpecification customerSpec = new FindCustomerByUserIdSpecification(userId);
            List<Customer> customers = customerRepo.query(customerSpec);
            if(customers.size() != 0) {
                Customer customer = customers.get(0);
                long customerId = customer.getId();
                FindActiveAppointmentsByCustomerIdSpecification appointmentSpec
                        = new FindActiveAppointmentsByCustomerIdSpecification(customerId);
                List<Appointment> appointments = appointmentRepo.query(appointmentSpec);
                FindDoctorsByCustomerIdSpecification doctorsSpec
                        = new FindDoctorsByCustomerIdSpecification(customerId);
                List<Doctor> doctors = doctorRepo.query(doctorsSpec);
                List[] result = new List[2];
                result[0] = appointments;
                result[1] = doctors;
                return result;
            } else {
                throw new ServiceException("Incorrect customer user id");
            }
        } catch (TransactionManagerException e) {
            throw new ServiceException("Transaction manager error", e);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository error" , e);
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e) {
                logger.error("Error in releasing connection", e);
            }
        }
    }
}
