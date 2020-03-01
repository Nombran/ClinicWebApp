package by.epam.clinic.command;

import by.epam.clinic.servlet.SessionRequestContent;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.*;


public class ActionFactoryTest {
    private static final String COMMAND_PARAMETER = "command";

    @Mock
    SessionRequestContent requestContent;

    private ActionFactory actionFactory;

    @BeforeMethod
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeClass
    void initTest() {
        actionFactory = new ActionFactory();
    }

    @AfterClass
    public void setDown() {
        requestContent = null;
        actionFactory = null;
    }

    @DataProvider
    public Object[][] correctCommand() {
        return new Object[][]{
                {"login", CommandType.LOGIN.getCurrentCommand()},
                {"logout", CommandType.LOGOUT.getCurrentCommand()},
                {"add_appointment", CommandType.ADD_APPOINTMENT.getCurrentCommand()},
                {"add_department", CommandType.ADD_DEPARTMENT.getCurrentCommand()},
                {"add_department_page", CommandType.ADD_DEPARTMENT_PAGE.getCurrentCommand()},
                {"add_doctor_page", CommandType.ADD_DOCTOR_PAGE.getCurrentCommand()},
                {"delete_appointment", CommandType.DELETE_APPOINTMENT.getCurrentCommand()},
                {"delete_doctor", CommandType.DELETE_DOCTOR.getCurrentCommand()},
                {"delete_ticket_reservation", CommandType.DELETE_TICKET_RESERVATION.getCurrentCommand()},
                {"departments_page", CommandType.DEPARTMENTS_PAGE.getCurrentCommand()},
                {"doctor_appointments_page", CommandType.DOCTOR_APPOINTMENTS_PAGE.getCurrentCommand()},
                {"doctors_page", CommandType.DOCTORS_PAGE.getCurrentCommand()},
                {"edit_department", CommandType.EDIT_DEPARTMENT.getCurrentCommand()},
                {"edit_department_page", CommandType.EDIT_DEPARTMENT_PAGE.getCurrentCommand()},
                {"edit_doctor", CommandType.EDIT_DOCTOR.getCurrentCommand()},
                {"edit_doctor_page", CommandType.EDIT_DOCTOR_PAGE.getCurrentCommand()},
                {"get_active_tickets_info", CommandType.GET_ACTIVE_TICKETS_INFO.getCurrentCommand()},
                {"get_all_departments", CommandType.GET_ALL_DEPARTMENTS.getCurrentCommand()},
                {"get_all_doctors", CommandType.GET_ALL_DOCTORS.getCurrentCommand()},
                {"get_doctors_by_department", CommandType.GET_DOCTORS_BY_DEPARTMENT.getCurrentCommand()},
                {"get_free_appointments", CommandType.GET_FREE_APPOINTMENTS.getCurrentCommand()},
                {"home_page", CommandType.HOME_PAGE.getCurrentCommand()},
                {"login_page", CommandType.LOGIN_PAGE.getCurrentCommand()},
                {"make_appointment", CommandType.MAKE_APPOINTMENT.getCurrentCommand()},
                {"make_appointment_page", CommandType.MAKE_APPOINTMENT_PAGE.getCurrentCommand()},
                {"personal_customer_page", CommandType.PERSONAL_CUSTOMER_PAGE.getCurrentCommand()},
                {"registration", CommandType.REGISTRATION.getCurrentCommand()}};
    }

    @Test(dataProvider = "correctCommand")
    public void correctCommandTest(String command, Command expected) {
        Mockito.when(requestContent.getRequestParameter(COMMAND_PARAMETER)).thenReturn(command);
        Command actual = actionFactory.defineCommand(requestContent);
        Assert.assertEquals(actual,expected);
    }
}

