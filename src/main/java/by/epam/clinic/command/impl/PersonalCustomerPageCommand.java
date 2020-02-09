package by.epam.clinic.command.impl;

import by.epam.clinic.command.CustomerCommand;
import by.epam.clinic.core.model.User;
import by.epam.clinic.core.service.impl.CustomerServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

import java.util.List;

public class PersonalCustomerPageCommand implements CustomerCommand {
    private static final String PAGE_PROPERTY = "path.page.personal_page";

    private static final String CURRENT_USER_ATTR = "current_user";

    private static final String APPOINTMENTS_ATTR = "appointments";

    private static final String DOCTORS_ATTR = "doctors";

    private CustomerServiceImpl customerServiceImpl;

    public PersonalCustomerPageCommand(CustomerServiceImpl customerServiceImpl) {
        this.customerServiceImpl = customerServiceImpl;
    }

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        String page = ConfigurationManager.getProperty(PAGE_PROPERTY);
        TransitionType transitionType = TransitionType.FORWARD;
        User user = (User)requestContent.getSessionAttribute(CURRENT_USER_ATTR);
        long userId = user.getId();
        try {
            List[] appointmentsData = customerServiceImpl.findActiveAppointments(userId);
            List appointments = appointmentsData[0];
            List doctors = appointmentsData[1];
            requestContent.setRequestAttribute(APPOINTMENTS_ATTR, appointments);
            requestContent.setRequestAttribute(DOCTORS_ATTR, doctors);
        } catch (ServiceException e) {
            //log
        }
        return new TransitionContent(page,transitionType);
    }
}
