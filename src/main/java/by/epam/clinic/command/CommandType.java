package by.epam.clinic.command;

import by.epam.clinic.command.admin.impl.*;
import by.epam.clinic.command.common.*;
import by.epam.clinic.command.customer.impl.DeleteTicketReservationCommand;
import by.epam.clinic.command.customer.impl.MakeAppointmentCommand;
import by.epam.clinic.command.customer.impl.MakeAppointmentPageCommand;
import by.epam.clinic.command.customer.impl.PersonalCustomerPageCommand;
import by.epam.clinic.command.doctor.impl.AddAppointmentCommand;
import by.epam.clinic.command.doctor.impl.DeleteAppointmentCommand;
import by.epam.clinic.command.doctor.impl.DoctorAppointmentsPageCommand;
import by.epam.clinic.command.doctor.impl.GetActiveTicketsInfoCommand;
import by.epam.clinic.command.guest.impl.LoginCommand;
import by.epam.clinic.command.guest.impl.LoginPageCommand;
import by.epam.clinic.command.guest.impl.RegistrationCommand;

/**
 *A Command Type. Contain objects that contain command.
 */
public enum CommandType {
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTRATION(new RegistrationCommand()),
    ADD_DEPARTMENT_PAGE(new AddDepartmentPageCommand()),
    ADD_DEPARTMENT(new AddDepartmentCommand()),
    ADD_DOCTOR_PAGE(new AddDoctorPageCommand()),
    ADD_DOCTOR(new AddDoctorCommand()),
    DOCTOR_APPOINTMENTS_PAGE(new DoctorAppointmentsPageCommand()),
    ADD_APPOINTMENT(new AddAppointmentCommand()),
    DELETE_APPOINTMENT(new DeleteAppointmentCommand()),
    GET_ALL_DEPARTMENTS(new GetAllDepartmentsCommand()),
    MAKE_APPOINTMENT_PAGE(new MakeAppointmentPageCommand()),
    GET_DOCTORS_BY_DEPARTMENT(new GetDoctorsByDepartmentCommand()),
    GET_FREE_APPOINTMENTS(new GetFreeAppointmentsCommand()),
    MAKE_APPOINTMENT(new MakeAppointmentCommand()),
    PERSONAL_CUSTOMER_PAGE(new PersonalCustomerPageCommand()),
    DELETE_TICKET_RESERVATION(new DeleteTicketReservationCommand()),
    HOME_PAGE(new HomePageCommand()),
    DOCTORS_PAGE(new DoctorsPageCommand()),
    GET_ALL_DOCTORS(new GetAllDoctorsCommand()),
    EDIT_DOCTOR_PAGE(new EditDoctorPageCommand()),
    EDIT_DOCTOR(new EditDoctorCommand()),
    DEPARTMENTS_PAGE(new DepartmentsPageCommand()),
    EDIT_DEPARTMENT_PAGE(new EditDepartmentPageCommand()),
    EDIT_DEPARTMENT(new EditDepartmentCommand()),
    GET_ACTIVE_TICKETS_INFO(new GetActiveTicketsInfoCommand()),
    LOGIN_PAGE(new LoginPageCommand()),
    DELETE_DOCTOR(new DeleteDoctorCommand());

    CommandType(Command command) {
        this.command = command;
    }

    private Command command;

    public Command getCurrentCommand() {
        return command;
    }
}