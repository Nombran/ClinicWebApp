package by.epam.clinic.command;

import by.epam.clinic.command.impl.*;
import by.epam.clinic.core.service.impl.*;

public enum CommandEnum {
    LOGIN {
        {
            this.command = new LoginCommand(new UserServiceImpl());
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    REGISTRATION {
        {
            this.command = new RegistrationCommand(new CustomerServiceImpl());
        }
    },
    CHANGE_LOCALIZATION {
        {
            this.command = new LocalizationCommand();
        }
    },
    ADD_DEPARTMENT_PAGE {
        {
            this.command = new AddDepartmentPageCommand();
        }
    },
    ADD_DEPARTMENT {
        {
            this.command = new AddDepartmentCommand(new DepartmentServiceImpl());
        }
    },
    ADD_DOCTOR_PAGE {
        {
            this.command = new AddDoctorPageCommand(new DepartmentServiceImpl());
        }
    },
    ADD_DOCTOR {
        {
            this.command = new AddDoctorCommand(new DoctorServiceImpl());
        }
    },
    DOCTOR_APPOINTMENTS_PAGE {
        {
            this.command = new DoctorAppointmentsPageCommand();
        }
    },
    ADD_APPOINTMENT {
        {
            this.command = new AddAppointmentCommand(new AppointmentServiceImpl());
        }
    },
    DELETE_APPOINTMENT {
        {
            this.command = new DeleteAppointmentCommand(new AppointmentServiceImpl());
        }
    },GET_ALL_DEPARTMENTS {
        {
            this.command = new GetAllDepartmentsCommand(new DepartmentServiceImpl());
        }
    },
    MAKE_APPOINTMENT_PAGE {
        {
            this.command = new MakeAppointmentPageCommand();
        }
    },
    GET_DOCTORS_BY_DEPARTMENT {
        {
            this.command = new GetDoctorsByDepartmentCommand(new DoctorServiceImpl());
        }
    },
    GET_FREE_APPOINTMENTS {
        {
            this.command = new GetFreeAppointmentsCommand(new AppointmentServiceImpl());
        }
    },
    MAKE_APPOINTMENT {
        {
            this.command = new MakeAppointmentCommand(new AppointmentServiceImpl());
        }
    },
    PERSONAL_CUSTOMER_PAGE {
        {
            this.command = new PersonalCustomerPageCommand(new CustomerServiceImpl());
        }
    },
    DELETE_TICKET_RESERVATION {
        {
            this.command = new DeleteTicketReservationCommand(new AppointmentServiceImpl());
        }
    },
    HOME_PAGE {
        {
            this.command = new HomePageCommand();
        }
    },
    DOCTORS_PAGE {
        {
            this.command = new DoctorsPageCommand();
        }
    },
    GET_ALL_DOCTORS {
        {
            this.command = new GetAllDoctorsCommand(new DoctorServiceImpl());
        }
    },
    EDIT_DOCTOR_PAGE {
        {
            this.command = new EditDoctorPageCommand(new DoctorServiceImpl(),new UserServiceImpl());
        }
    },
    EDIT_DOCTOR {
        {
            this.command = new EditDoctorCommand(new UserServiceImpl(),new DoctorServiceImpl());
        }
    },
    DEPARTMENTS_PAGE {
        {
            this.command = new DepartmentsPageCommand();
        }
    },
    DELETE_DEPARTMENT {
        {
            this.command = new DeleteDepartmentCommand(new DepartmentServiceImpl());
        }
    },EDIT_DEPARTMENT_PAGE {
        {
            this.command = new EditDepartmentPageCommand(new DepartmentServiceImpl());
        }
    }, EDIT_DEPARTMENT {
        {
            this.command = new EditDepartmentCommand(new DepartmentServiceImpl());
        }
    },GET_ACTIVE_TICKETS_INFO {
        {
            this.command = new GetActiveTicketsInfoCommand(new DoctorServiceImpl());
        }
    },LOGIN_PAGE {
        {
            this.command = new LoginPageCommand();
        }
    };

    Command command;


    public Command getCurrentCommand() {
        return command;
    }
}