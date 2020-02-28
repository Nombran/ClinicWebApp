package by.epam.clinic.command.common;

import by.epam.clinic.command.admin.AdminCommand;
import by.epam.clinic.command.customer.CustomerCommand;
import by.epam.clinic.command.doctor.DoctorCommand;
import by.epam.clinic.core.model.DepartmentAttribute;
import by.epam.clinic.core.model.Doctor;
import by.epam.clinic.core.service.impl.DoctorServiceImpl;
import by.epam.clinic.core.service.impl.ServiceException;
import by.epam.clinic.servlet.SessionRequestContent;
import by.epam.clinic.servlet.TransitionContent;
import by.epam.clinic.servlet.TransitionType;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Command prepare doctors information for sending
 * using {@code DoctorService}.
 */
public class GetDoctorsByDepartmentCommand implements DoctorCommand, AdminCommand, CustomerCommand{
    private static Logger logger = LogManager.getLogger();

    private DoctorServiceImpl doctorService;

    private static final String RESULT_ATTR = "ajax_response";

    private static final String ERROR_MESSAGE_PROPERTY = "message.failed_loading";


    public GetDoctorsByDepartmentCommand() {
        this.doctorService = new DoctorServiceImpl();
    }

    /**
     * Call method creating {@link TransitionContent} object with transaction
     * type ajax, and puts info about doctors based on department id.
     * .
     *
     * @param requestContent object of that contain request, response and session information.
     * @return {@link TransitionContent} object, which contains ajax routing type and
     * doctors info.
     */
    @Override
    public TransitionContent execute(SessionRequestContent requestContent) {
        long departmentId = Long.parseLong(requestContent.getRequestParameter(DepartmentAttribute.ID_ATTR));
        List<Doctor> doctors;
        try {
            doctors = doctorService.getDoctorsByDepartmentId(departmentId);
            Gson gson = new Gson();
            String gsonData = gson.toJson(doctors);
            requestContent.setRequestAttribute(RESULT_ATTR, gsonData);
        } catch (ServiceException e) {
            logger.error(e);
            requestContent.setRequestAttribute(RESULT_ATTR, ERROR_MESSAGE_PROPERTY);
        }
        return new TransitionContent(TransitionType.AJAX);
    }
}
