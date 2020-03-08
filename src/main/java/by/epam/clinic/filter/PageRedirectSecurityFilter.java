package by.epam.clinic.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageRedirectSecurityFilter implements Filter {

    private static final String HOME_PAGE_URL = "/controller?command=home_page";

    public void init(FilterConfig fConfig) {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.sendRedirect(httpRequest.getContextPath() + HOME_PAGE_URL);
    }

    public void destroy() {
    }
}
