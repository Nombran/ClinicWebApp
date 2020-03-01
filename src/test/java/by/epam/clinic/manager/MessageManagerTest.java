package by.epam.clinic.manager;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MessageManagerTest {
    private String expected;

    @AfterClass
    public void setDown() {
        expected = null;
    }

    @DataProvider
    public Object[][] messages() {
        return new Object[][] {
                {"message.invalid_command","Некорректное действие!"},
                {"message.successful_deleting", "Врач успешно удален!"},
                {"message.no_rights", "Нет прав для данного действия!"}};
        }

    @Test(dataProvider = "messages")
    public void getMessageTest(String key, String message) {
        expected = message;

        String actual = MessageManager.getProperty(key);

        Assert.assertEquals(actual,expected);
    }
}
