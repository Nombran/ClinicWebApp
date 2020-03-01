package by.epam.clinic.util;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TextEncryptorTest {
    private String expected;

    @AfterClass
    public void setDown() {
        expected = null;
    }


    @DataProvider
    public Object[][] encryptionResults() {
        return new Object[][]{
                {"asdASD123", "8335680b0e73e49aebe8f9657875297"},
                {"TestPassword123", "a84caf11b7cada1a1c802830c5817867"},
                {"DoctPasss123", "1f5c38161659d5c0030f930cb528da98"}};
    }

    @Test(dataProvider = "encryptionResults")
    public void textEncryptorTest(String unencrypted, String encrypted) throws TextEncryptorException {
        expected = encrypted;

        String actual = TextEncryptor.encrypt(unencrypted);

        Assert.assertEquals(actual, expected);
    }
}
