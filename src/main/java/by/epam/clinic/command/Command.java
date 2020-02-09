package by.epam.clinic.command;

import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;

public interface Command {
    TransitionContent execute(SessionRequestContent requestContent);
}
