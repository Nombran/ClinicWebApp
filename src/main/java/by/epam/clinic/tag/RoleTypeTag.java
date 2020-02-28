package by.epam.clinic.tag;

import by.epam.clinic.core.model.UserRole;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class RoleTypeTag extends TagSupport {
    private static final String COLOR_GREEN = "#35D031";
    private static final String COLOR_RED = "#D01C17";
    private static final String COLOR_BLACK = "#130502";
    private static final String COLOR_ORANGE = "#d07503c2";
    private static final String COLOR_WHITE = "#fff";
    private UserRole role;

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public int doStartTag() throws JspException {
        if (role != null) {
            try {
                String color;
                switch (role) {
                    case CUSTOMER:
                        color = COLOR_GREEN;
                        break;
                    case GUEST:
                        color = COLOR_ORANGE;
                        break;
                    case ADMIN:
                        color = COLOR_RED;
                        break;
                    case DOCTOR:
                        color = COLOR_WHITE;
                        break;
                    default:
                        color = COLOR_BLACK;
                        break;
                }
                pageContext.getOut().write(" <i class=\"fas fa-user-tag\"></i><b style=\"color: " + color + "\">" + role + "</b>");
            } catch (IOException e) {
                throw new JspException(e);
            }
        }
        return SKIP_BODY;
    }
}