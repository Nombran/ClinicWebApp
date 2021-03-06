package by.epam.clinic.manager;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ConfigurationManagerTest {
    private String expected;

    @AfterClass
    public void setDown() {
        expected = null;
    }

    @DataProvider
    public Object[][] configurations() {
        return new Object[][] {
                {"path.page.login","/jsp/login.jsp"},
                {"path.page.main_page","/jsp/main_page.jsp"},
                {"path.page.add_doctor","/jsp/admin/add_doctor.jsp"},
                {"path.page.add_department","/jsp/admin/add_department.jsp"},
                {"path.page.doctor_appointments","/jsp/doctor/doctor_appointments.jsp"},
                {"path.page.make_appointment","/jsp/customer/make_appointment.jsp"},
                {"path.page.personal_page", "/jsp/customer/personal_page.jsp"},
                {"path.page.home_page", "/jsp/main_page.jsp"},
                {"path.page.doctors_page", "/jsp/admin/doctors.jsp"},
                {"path.page.edit_doctor","/jsp/admin/edit_doctor.jsp"},
                {"path.page.departments_page", "/jsp/admin/departments.jsp"},
                {"path.page.edit_department", "/jsp/admin/edit_department.jsp"}};
        }

    @Test(dataProvider = "configurations")
    public void getPropertyTest(String key, String property) {
        expected = property;

        String actual = ConfigurationManager.getProperty(key);

        Assert.assertEquals(actual, expected);
    }
}
