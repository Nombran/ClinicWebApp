package by.epam.clinic.manager;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {
    private static final String RESOURCE_BUNDLE = "property.message";

    private static ResourceBundle resourceBundle =
            ResourceBundle.getBundle(RESOURCE_BUNDLE, Locale.getDefault());
    private MessageManager() { }
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }

    public static void changeResource(Locale locale) {
        resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE, locale);
    }
}