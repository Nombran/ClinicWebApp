package by.epam.clinic.validator;

import by.epam.clinic.core.validator.UserDataValidator;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UserDataValidatorTest {
    private String expected;

    @AfterClass
    public void setDown() {
        expected = null;
    }

    @DataProvider
    public Object[][] userData() {
        return new Object[][]{
                {new String[]{"login", "password"}, false},
                {new String[]{"LoginPass2123", "TestPassw245"}, true},
                {new String[]{"1231231213", "1231231231231"}, false},
                {new String[]{"dfnsSDFSD3423", "sdfjjgGHJg4654"}, true},
                {new String[]{"QwertY2342", "poiytrF34ff"}, true}};
    }

    @Test(dataProvider = "userData")
    public void idDataValidTest(String[] userData, boolean expected) {
       String login  = userData[0];
       String password = userData[1];
       Assert.assertEquals(UserDataValidator.isDataValid(login,password),expected);
    }
}
