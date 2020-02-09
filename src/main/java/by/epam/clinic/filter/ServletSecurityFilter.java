package by.epam.clinic.filter;

import by.epam.clinic.command.*;
import by.epam.clinic.command.Command;
import by.epam.clinic.command.impl.EmptyCommand;
import by.epam.clinic.core.model.UserRole;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.manager.MessageManager;
import by.epam.clinic.servlet.SessionRequestContent;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/controller"}, servletNames = {"Controller"})
public class ServletSecurityFilter implements Filter {

    private static final String WRONG_ACTION_ATTR = "wrongAction";

    private final static String NO_RIGHTS_MESSAGE = "message.no_rights";

    private final static String HOME_PAGE_PROPERTY = "path.page.home_page";

    private final static String COMMAND_PACKAGE = "by.epam.clinic.command.";

    private final static String COMMAND_INTERFACE_NAME = "Command";

    private final static String USER_ROLE_ATTR = "userRole";

    private final static String LANGUAGE_ATTR = "language";


    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        UserRole role = (UserRole) session.getAttribute(USER_ROLE_ATTR);
        if (role == null) {
            role = UserRole.GUEST;
            session.setAttribute(USER_ROLE_ATTR, role);
            session.setAttribute(LANGUAGE_ATTR, Locale.getDefault().toString());
        }
        SessionRequestContent requestContent = new SessionRequestContent();
        requestContent.extractValues(req);
        ActionFactory actionFactory = new ActionFactory();
        Command command = actionFactory.defineCommand(requestContent);
        if (command.getClass() != EmptyCommand.class) {
            String roleInterface = role.toString().toLowerCase() + COMMAND_INTERFACE_NAME;
            String firstLetter = roleInterface.substring(0,1).toUpperCase();
            roleInterface = firstLetter +roleInterface.substring(1);
            try {
                Class commandClass = Class.forName(COMMAND_PACKAGE + roleInterface);
                if (commandClass.isInstance(command)) {
                    chain.doFilter(request, response);
                    return;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
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