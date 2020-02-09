package by.epam.clinic.core.service.impl;

import by.epam.clinic.core.model.Appointment;
import by.epam.clinic.core.model.Customer;
import by.epam.clinic.core.model.Doctor;
import by.epam.clinic.core.model.User;
import by.epam.clinic.core.pool.TransactionManager;
import by.epam.clinic.core.pool.TransactionManagerException;
import by.epam.clinic.core.repository.impl.AppointmentRepository;
import by.epam.clinic.core.repository.impl.RepositoryException;
import by.epam.clinic.core.repository.impl.CustomerRepository;
import by.epam.clinic.core.repository.impl.DoctorRepository;
import by.epam.clinic.core.service.AppointmentService;
import by.epam.clinic.core.specification.impl.*;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentServiceImpl implements AppointmentService {

    public boolean createAppointment(Appointment appointment, long userId) throws ServiceException {
        LocalDateTime dateTime = appointment.getDateTime();
        LocalDateTime now = LocalDateTime.now();
        if(dateTime.minusHours(1).isBefore(now)) {
            return false;
        }
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.init();
            AppointmentRepository appointmentRepository = new AppointmentRepository();
            DoctorRepository doctorRepository = new DoctorRepository();
            transactionManager.setConnectionToRepository(doctorRepository, appointmentRepository);
            FindDoctorByUserIDSpecification docSpec = new FindDoctorByUserIDSpecification(userId);
            List<Doctor> doctors = doctorRepository.query(docSpec);
            if(doctors.size()!=0) {
                Doctor doctor = doctors.get(0);
                appointment.setDoctorId(doctor.getId());
                FindAppointmentByDateTimeSpecification specification =
                        new FindAppointmentByDateTimeSpecification(appointment.getDateTime());
                List<Appointment> appointments = appointmentRepository.query(specification);
                if (appointments.size() == 0) {
                  appointmentRepository.add(appointment);
                  return true;
                }
            }
        } catch (TransactionManagerException e) {
            throw new ServiceException("Transaction manager error", e);
            //log
        } catch (RepositoryException e) {
            throw new ServiceException("Repository error");
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e) {
                //log
            }
        }
        return false;
    }

    public List<Appointment> findDoctorAppointments(User user) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.init();
            DoctorRepository doctorRepository = new DoctorRepository();
            AppointmentRepository appointmentRepository = new AppointmentRepository();
            transactionManager.setConnectionToRepository(doctorRepository, appointmentRepository);
            long userId = user.getId();
            FindFreeAppointmentsByDoctorUserIdSpecification specification
                    = new FindFreeAppointmentsByDoctorUserIdSpecification(userId);
            List<Appointment> appointments = appointmentRepository.query(specification);
            appointments.sort((appointment1, appointment2) -> {
                LocalDateTime dateTime1 = appointment1.getDateTime();
                LocalDateTime dateTime2 = appointment2.getDateTime();
                if (dateTime1.isBefore(dateTime2)) {
                    return -1;
                } else {
                    if (dateTime1.isAfter(dateTime2)) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
            return appointments;
        } catch (TransactionManagerException e) {
            //log
            throw new ServiceException("Transaction manager error");
        } catch (RepositoryException e) {
            throw new ServiceException("Repository error");
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e) {
                //log
            }
        }
    }

    public List<Appointment> getDoctorsFreeAppointments(long doctorId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.init();
            AppointmentRepository repository = new AppointmentRepository();
            transactionManager.setConnectionToRepository(repository);
            FindFreeDoctorAppointmentsByDoctorIdSpecification specification =
                    new FindFreeDoctorAppointmentsByDoctorIdSpecification(doctorId);
            return repository.query(specification);
        } catch (TransactionManagerException e) {
            //log
            throw new ServiceException("Transaction manager error",e);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository error",e);
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e1) {
                //log
            }
        }
    }

    public boolean deleteAppointment(long appointmentId, long userId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.init();
            AppointmentRepository appointmentRepository = new AppointmentRepository();
            transactionManager.setConnectionToRepository(appointmentRepository);
            FindAppointmentByIdAndUserIdSpecification specification
                    = new FindAppointmentByIdAndUserIdSpecification(appointmentId,userId);
            List<Appointment> appointments =  appointmentRepository.query(specification);
            if(appointments.size() != 0) {
                Appointment appointment = appointments.get(0);
                appointmentRepository.remove(appointment.getId());
                return true;
            }
        } catch (TransactionManagerException e) {
            throw new ServiceException("Transaction manager error");
            //log
        } catch (RepositoryException e) {
            throw new ServiceException("Repository error");
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e) {
                //log
            }
        }
        return false;
    }

    public boolean makeAppointment(long userId, long appointmentId, String purpose) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.init();
            AppointmentRepository appointmentRepository = new AppointmentRepository();
            CustomerRepository customerRepository = new CustomerRepository();
            transactionManager.setConnectionToRepository(customerRepository, appointmentRepository);
            FindCustomerByUserIdSpecification customerSpecification =
                    new FindCustomerByUserIdSpecification(userId);
            List<Customer> customers = customerRepository.query(customerSpecification);
            Customer customer = customers.get(0);
            FindAppointmentByIdSpecification appointmentSpecification =
                    new FindAppointmentByIdSpecification(appointmentId);
            List<Appointment> appointments = appointmentRepository.query(appointmentSpecification);
            if(appointments.size() != 0) {
                Appointment appointment = appointments.get(0);
                appointment.setCustomerId(customer.getId());
                appointment.setPurpose(purpose);
                appointmentRepository.update(appointment);
                return true;
            }
        } catch (TransactionManagerException e) {
            throw new ServiceException("Transaction manager error");
            //log
        } catch (RepositoryException e) {
            throw new ServiceException("Repository error");
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e) {
                //log
            }
        }
        return false;
    }

    public void deleteTicketReservation(long userId, long appointmentId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.init();
            CustomerRepository customerRepo = new CustomerRepository();
            AppointmentRepository appointmentRepo = new AppointmentRepository();
            transactionManager.setConnectionToRepository(customerRepo, appointmentRepo);
            FindCustomerByUserIdSpecification customerSpec
                    = new FindCustomerByUserIdSpecification(userId);
            List<Customer> customers = customerRepo.query(customerSpec);
            if(customers.size() != 0) {
                Customer customer = customers.get(0);
                FindAppointmentByIdSpecification appointmentSpec
                        = new FindAppointmentByIdSpecification(appointmentId);
                List<Appointment> appointments = appointmentRepo.query(appointmentSpec);
                if(appointments.size() != 0 ){
                    Appointment appointment = appointments.get(0);
                    if(appointment.getCustomerId() == customer.getId()) {
                        appointment.setCustomerId(0);
                        appointmentRepo.update(appointment);
                    } else {
                        throw new ServiceException("User tries to delete foreign ticket");
                    }
                } else {
                    throw new ServiceException("There is no appointment with such id");
                }
            } else {
                throw new ServiceException("There is no customer with such user id");
            }
        } catch (TransactionManagerException e) {
            throw new ServiceException("Transaction manager error",e);
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
