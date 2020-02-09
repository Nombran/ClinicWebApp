package by.epam.clinic.core.service.impl;

import by.epam.clinic.core.model.Appointment;
import by.epam.clinic.core.model.Customer;
import by.epam.clinic.core.model.Doctor;
import by.epam.clinic.core.model.User;
import by.epam.clinic.core.pool.TransactionManager;
import by.epam.clinic.core.pool.TransactionManagerException;
import by.epam.clinic.core.repository.impl.*;
import by.epam.clinic.core.service.DoctorService;
import by.epam.clinic.core.specification.impl.*;
import by.epam.clinic.util.ImageUploader;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoctorServiceImpl implements DoctorService {
    private static final String UPLOAD_DIR_PATH = "/img/doctors";

    public boolean createDoctor(User user, Doctor doctor, String servletContextPath, Part imageFile) throws ServiceException {
        String uploadDir = servletContextPath + UPLOAD_DIR_PATH;
        ImageUploader uploader = new ImageUploader(uploadDir);
        TransactionManager transactionManager = new TransactionManager();
        String imageFileName = uploader.generateUniqueFileName(imageFile);
        String dbFilePath = UPLOAD_DIR_PATH + "/" + imageFileName;
        try {
            transactionManager.init();
            UserRepository userRepository = new UserRepository();
            DoctorRepository doctorRepository = new DoctorRepository();
            transactionManager.beginTransaction();
            transactionManager.setConnectionToRepository(userRepository, doctorRepository);
            userRepository.add(user);
            doctor.setUserId(user.getId());
            doctor.setImagePath(dbFilePath);
            doctorRepository.add(doctor);
            try {
                uploader.write(imageFile, imageFileName);
                return true;
            } catch (IOException e) {
                //log
                transactionManager.rollbackTransaction();
                return false;
            }
        } catch (
                TransactionManagerException e) {
            //log
            try {
                transactionManager.rollbackTransaction();
            } catch (TransactionManagerException e1) {
                //log
            }
            throw new ServiceException("Transaction manager error", e);
        } catch (
                RepositoryException e) {
            throw new ServiceException("Repository error", e);
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e) {
                e.printStackTrace();
            }
        }

    }

    public List<Doctor> getDoctorsByDepartmentId(long id) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.init();
            DoctorRepository repository = new DoctorRepository();
            transactionManager.setConnectionToRepository(repository);
            FindDoctorsByDepartmentIdSpecification specification
                    = new FindDoctorsByDepartmentIdSpecification(id);
            return repository.query(specification);
        } catch (TransactionManagerException e) {
            //log
            throw new ServiceException("Transaction manager error", e);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository error", e);
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e) {
                //log
            }
        }
    }

    public List<Appointment> findDoctorFutureAppointments(long userId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.init();
            AppointmentRepository appointmentRepo = new AppointmentRepository();
            transactionManager.setConnectionToRepository(appointmentRepo);
            FindAppointmentsByDoctorUserIdSpecification appointmentSpec
                    = new FindAppointmentsByDoctorUserIdSpecification(userId);
            return appointmentRepo.query(appointmentSpec);
        } catch (TransactionManagerException e) {
            throw new ServiceException("Transaction manager error", e);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository exception", e);
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e) {
                //log
            }
        }
    }

    public List<Customer> findDoctorsFutureCustomers(long userId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.init();
            CustomerRepository customerRepository = new CustomerRepository();
            transactionManager.setConnectionToRepository(customerRepository);
            FindCustomersByDoctorUserIdSpecification specification
                    = new FindCustomersByDoctorUserIdSpecification(userId);
            return customerRepository.query(specification);
        } catch (TransactionManagerException e) {
            throw new ServiceException("Transaction manager error");
        } catch (RepositoryException e) {
            throw new ServiceException("Repository error", e);
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e) {
                //log
            }
        }
    }

    public List<Doctor> findAllDoctors() throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.init();
            DoctorRepository repository = new DoctorRepository();
            transactionManager.setConnectionToRepository(repository);
            FindAllDoctorsSpecification specification
                    = new FindAllDoctorsSpecification();
            return repository.query(specification);
        } catch (TransactionManagerException e) {
            throw new ServiceException("Transaction manager error", e);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository error", e);
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e) {
                //log
            }
        }
    }

    public void deleteDoctor(long id) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.init();
            DoctorRepository repository = new DoctorRepository();
            transactionManager.setConnectionToRepository(repository);
            repository.remove(id);
        } catch (TransactionManagerException e) {
            throw new ServiceException("Transaction manager error", e);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository error", e);
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e) {
                //log
            }
        }
    }

    public Optional<Doctor> findDoctor(long id) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            Optional<Doctor> result = Optional.empty();
            transactionManager.init();
            DoctorRepository repository = new DoctorRepository();
            transactionManager.setConnectionToRepository(repository);
            FindDoctorByIdSpecification specification
                    = new FindDoctorByIdSpecification(id);
            List<Doctor> queryResult = repository.query(specification);
            if (queryResult.size() != 0) {
                return Optional.of(queryResult.get(0));
            } else {
                return result;
            }
        } catch (TransactionManagerException e) {
            throw new ServiceException("Transaction manager error", e);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository error", e);
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e) {
                //log
            }
        }
    }

    public void updateDoctor(User user, Doctor doctor, String servletContextPath, Part imageFile) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        String uploadDir = servletContextPath + UPLOAD_DIR_PATH;
        try {
            transactionManager.init();
            UserRepository userRepository = new UserRepository();
            DoctorRepository repository = new DoctorRepository();
            transactionManager.setConnectionToRepository(repository, userRepository);
            transactionManager.beginTransaction();
            userRepository.update(user);
            if (doctor.getImagePath() != null) {
                repository.update(doctor);
            } else {
                ImageUploader uploader = new ImageUploader(uploadDir);
                String imageFileName = uploader.generateUniqueFileName(imageFile);
                String dbFilePath = UPLOAD_DIR_PATH + "/" + imageFileName;
                doctor.setImagePath(dbFilePath);
                repository.update(doctor);
                uploader.write(imageFile, imageFileName);
            }
            transactionManager.commitTransaction();
        } catch (TransactionManagerException e) {
            throw new ServiceException("Transaction manager error", e);
        } catch (IOException e) {
            try {
                transactionManager.rollbackTransaction();
            } catch (TransactionManagerException e1) {
                //log
            }
            throw new ServiceException("Error in uploading image", e);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository error", e);
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e) {
                //log
            }
        }
    }
}
