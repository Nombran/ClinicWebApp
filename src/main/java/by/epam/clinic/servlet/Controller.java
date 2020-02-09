package by.epam.clinic.servlet;

import by.epam.clinic.command.Command;
import by.epam.clinic.command.ActionFactory;
import by.epam.clinic.manager.ConfigurationManager;
import by.epam.clinic.manager.MessageManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/controller")
@MultipartConfig
public class Controller extends HttpServlet {
    private static final String NULL_PAGE_PROPERTY = "path.page.index";

    private static final String NULL_PAGE_ATTRIBUTE = "nullPage";

    private static final String NULL_PAGE_MESSAGE = "message.nullpage";

    private static final String AJAX_ATTR = "ajax_response";

    private static final String CURRENT_PAGE_ATTR = "current_page";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page;
        TransitionContent transitionContent;
        ActionFactory client = new ActionFactory();
        SessionRequestContent requestContent = new SessionRequestContent();
        requestContent.extractValues(request);
        Command command = client.defineCommand(requestContent);
        transitionContent = command.execute(requestContent);
        if (transitionContent != null) {
            TransitionType transitionType = transitionContent.getTransitionType();
            page = transitionContent.getPage();
            requestContent.insertAttributes(request);
            HttpSession session = request.getSession();
            session.setAttribute(CURRENT_PAGE_ATTR, page);
            switch (transitionType) {
                case FORWARD:
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                    dispatcher.forward(request, response);
                    break;
                case REDIRECT:
                    response.sendRedirect(request.getContextPath() + page);
                    break;
                case NONE: String serializedData = (String)request.getAttribute(AJAX_ATTR);
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.print(serializedData);
                    out.flush();
                    break;
            }
        } else {
            page = ConfigurationManager.getProperty(NULL_PAGE_PROPERTY);
            request.getSession().setAttribute(NULL_PAGE_ATTRIBUTE,
                    MessageManager.getProperty(NULL_PAGE_MESSAGE));
            response.sendRedirect(request.getContextPath() + page);
        }
    }
}
