package by.epam.clinic.command.impl;

import by.epam.clinic.command.AdminCommand;
import by.epam.clinic.core.model.DoctorAttribute;
import by.epam.clinic.core.service.impl.DoctorServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.manager.MessageManager;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;

public class DeleteDoctorCommand implements AdminCommand {
    private static final String RESULT_ATTR = "ajax_response";

    private static final String SUCCESSFUL_MESSAGE = "message.successful_deleting";

    private static final String ERROR_MESSAGE = "message.failed_deleting";

    DoctorServiceImpl doctorService = new DoctorServiceImpl();

    public DeleteDoctorCommand(DoctorServiceImpl doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        long id = Long.parseLong(requestContent.getRequestParameter(DoctorAttribute.ID_ATTR));
        try {
            doctorService.deleteDoctor(id);
            String message = MessageManager.getProperty(SUCCESSFUL_MESSAGE);
            requestContent.setRequestAttribute(RESULT_ATTR,message);
        } catch (ServiceException e) {
            String message = MessageManager.getProperty(ERROR_MESSAGE);
            requestContent.setRequestAttribute(RESULT_ATTR,message);
        }
        return new TransitionContent(null, TransitionType.NONE);
    }
}
