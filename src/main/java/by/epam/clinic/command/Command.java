package by.epam.clinic.command;

import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;

/**
 * Functional interface realisation of Command pattern.
 * Every command must has a corresponding element in enum {@link CommandType}
 */
@FunctionalInterface
public interface Command {
    /**
     * Sets a common interface for specific command classes.
     *
     * @param requestContent is a {@link SessionRequestContent} object that
     * contain request, response and session information.
     * @return {@link TransitionContent} which contains path to resources and
     * router type.
     */
    TransitionContent execute(SessionRequestContent requestContent);
}
