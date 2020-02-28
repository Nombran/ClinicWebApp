package by.epam.clinic.filter;

import by.epam.clinic.command.*;
import by.epam.clinic.command.Command;
import by.epam.clinic.command.admin.AdminCommand;
import by.epam.clinic.command.common.EmptyCommand;
import by.epam.clinic.command.customer.CustomerCommand;
import by.epam.clinic.command.doctor.DoctorCommand;
import by.epam.clinic.command.guest.GuestCommand;
import by.epam.clinic.core.model.User;
import by.epam.clinic.core.model.UserRole;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.manager.MessageManager;
import by.epam.clinic.servlet.SessionRequestContent;


import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static by.epam.clinic.core.model.UserRole.GUEST;


public class ServletSecurityFilter implements Filter {

    private static final String WRONG_ACTION_ATTR = "wrongAction";

    private final static String NO_RIGHTS_MESSAGE = "message.no_rights";

    private final static String HOME_PAGE_PROPERTY = "path.page.home_page";

    private final static String USER_ROLE_ATTR = "userRole";

    private static final String CURRENT_USER_ATTR = "current_user";

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(true);
        UserRole role = (UserRole) session.getAttribute(USER_ROLE_ATTR);
        if (role == null) {
            role = GUEST;
            session.setAttribute(USER_ROLE_ATTR, role);
            User user = new User();
            user.setRole(GUEST);
            session.setAttribute(CURRENT_USER_ATTR, user);
        }
        SessionRequestContent requestContent = new SessionRequestContent();
        requestContent.extractValues(req);
        ActionFactory actionFactory = new ActionFactory();
        Command command = actionFactory.defineCommand(requestContent);
        if (command.getClass() != EmptyCommand.class) {
            switch (role) {
                case DOCTOR:
                    if (command instanceof DoctorCommand) {
                        chain.doFilter(request, response);
                        return;
                    }
                    break;
                case GUEST:
                    if (command instanceof GuestCommand) {
                        chain.doFilter(request, response);
                        return;
                    }
                    break;
                case ADMIN:
                    if (command instanceof AdminCommand) {
                        chain.doFilter(request, response);
                        return;
                    }
                    break;
                case CUSTOMER:
                    if (command instanceof CustomerCommand) {
                        chain.doFilter(request, response);
                        return;
                    }
                    break;
                default:
                    break;
            }
        } else {
            chain.doFilter(request, response);
            return;
        }
        String message = MessageManager.getProperty(NO_RIGHTS_MESSAGE);
        req.setAttribute(WRONG_ACTION_ATTR, message);
        String page = ConfigurationManager.getProperty(HOME_PAGE_PROPERTY);
        RequestDispatcher requestDispatcher = request
                .getServletContext()
                .getRequestDispatcher(page);
        requestDispatcher.forward(req, resp);
    }

    public void init(FilterConfig fConfig) {
    }
}