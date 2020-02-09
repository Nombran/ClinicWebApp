package by.epam.clinic.manager;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigurationManager {
    private static final String RESOURCE_BUNDLE = "property.config";

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE,
            Locale.getDefault());

    private ConfigurationManager() { }
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
    public static void changeResource(Locale locale) {
        resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE, locale);
    }
}