package by.epam.clinic.command.common;

import by.epam.clinic.command.admin.AdminCommand;
import by.epam.clinic.command.customer.CustomerCommand;
import by.epam.clinic.command.doctor.DoctorCommand;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

/**
 * Command is is forwarding users to {@code login.jsp}
 */
public class LogoutCommand implements DoctorCommand, AdminCommand, CustomerCommand {
    private static final String PAGE_URL = "/controller?command=login_page";


    /**
     * Call invalidates user's active session.
     *
     * @param requestContent object of that contain request, response and session information.
     * @return {@link TransitionContent} object, which contains redirect routing type.
     */
    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        requestContent.setSessionInvalidateStatus(true);
        return new TransitionContent(PAGE_URL,TransitionType.REDIRECT);
    }
}
