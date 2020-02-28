package by.epam.clinic.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

public class LocalizationFilter implements Filter {

    private static final String LOCALIZATION_ATTR = "language";

    private static final String CURRENT_PAGE_URL = "current_page_url";

    private static final String HOME_PAGE_URL = "/controller?command=home_page";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(true);
        String localization = req.getParameter(LOCALIZATION_ATTR);
        if(localization != null) {
            String currentUrl = (String)session.getAttribute(CURRENT_PAGE_URL);
            if(currentUrl == null) {
                currentUrl = HOME_PAGE_URL;
            }
            session.setAttribute(LOCALIZATION_ATTR, localization);
            req.getRequestDispatcher(currentUrl).forward(request,response);
        } else {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig fConfig) {
    }

    public void destroy() {
    }

}
