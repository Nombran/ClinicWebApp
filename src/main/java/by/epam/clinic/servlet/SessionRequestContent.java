package by.epam.clinic.servlet;

import by.epam.clinic.core.model.UserRole;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;

public class SessionRequestContent {
    private final static String LANGUAGE_ATTR = "language";

    private final static String USER_ROLE_ATTR = "userRole";

    private String servletContextPath;
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;
    private HashMap<String, Part> requestParts;

    private boolean isSessionInvalidate = true;

    public SessionRequestContent() {
        requestAttributes = new HashMap<>();
        requestParameters =  new HashMap<>();
        sessionAttributes = new HashMap<>();
        requestParts = new HashMap<>();
    }

    public void extractValues(HttpServletRequest request) {
        servletContextPath = request.getServletContext().getRealPath("");
        Enumeration enumeration = request.getAttributeNames();
        while(enumeration.hasMoreElements()) {
            String attributeName = (String)enumeration.nextElement();
            requestAttributes.put(attributeName,request.getAttribute(attributeName));
        }
        requestParameters.putAll(request.getParameterMap());
        HttpSession session = request.getSession(false);
        if(session != null) {
            isSessionInvalidate = false;
            enumeration = request.getSession().getAttributeNames();
            while(enumeration.hasMoreElements()) {
                String attributeName = (String) enumeration.nextElement();
                sessionAttributes.put(attributeName,session.getAttribute(attributeName));
            }
        }
        try {
            Collection<Part> parts = request.getParts();
            for(Part part :parts) {
                requestParts.put(part.getName(),part);
            }
        } catch (IOException | ServletException e) {
            //log
        }
    }

    void insertAttributes(HttpServletRequest request) {
            for(String attribute : requestAttributes.keySet()) {
                request.setAttribute(attribute, requestAttributes.get(attribute));
            }
            HttpSession session = request.getSession(false);
            if(!isSessionInvalidate) {
                for (String attribute : sessionAttributes.keySet()) {
                    session.setAttribute(attribute, sessionAttributes.get(attribute));
                }
            } else {
                    String language = (String)sessionAttributes.get(LANGUAGE_ATTR);
                    session.invalidate();
                    session = request.getSession(true);
                    session.setAttribute(LANGUAGE_ATTR, language);
                    session.setAttribute(USER_ROLE_ATTR , UserRole.GUEST);
            }
    }

    public void setRequestAttribute(String key, Object value) {
            requestAttributes.put(key, value);
    }

    public Object getRequestAttribute(String key) {
        return requestAttributes.get(key);
    }

    public void setRequestParameter(String key, String[] value) {
            requestParameters.put(key, value);
    }

    public String getRequestParameter(String key) {
        if(requestParameters.containsKey(key)) {
            return requestParameters.get(key)[0];
        } else {
            return null;
        }
    }

    public String[] getParameterValues(String key) {
        return requestParameters.get(key);
    }

    public boolean containsParameters(String key, String... keys) {
        boolean resutlt = true;
        if(!requestParameters.containsKey(key)){
            return false;
        }
        for(String stringKey : keys) {
            if(!requestParameters.containsKey(stringKey)) {
                return false;
            }
        }
        return true;
    }

    public void setSessionAttribute(String key, Object value) {
            sessionAttributes.put(key, value);
    }

    public Object getSessionAttribute(String key) {
        return sessionAttributes.get(key);
    }

    public void setSessionInvalidateStatus(boolean status) {
        isSessionInvalidate = status;
    }

    public boolean getSessionInvalidateStatus() {
        return isSessionInvalidate;
    }

    public Part getPart(String name) {
        return requestParts.get(name);
    }

    public String getServletContextPath() {
        return servletContextPath;
    }
}
