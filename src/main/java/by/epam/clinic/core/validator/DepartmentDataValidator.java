package by.epam.clinic.core.validator;
import org.apache.commons.io.FilenameUtils;

public class DepartmentDataValidator {
    private static final String NAME_PATTERN = "[-а-яёА-ЯЁ]{4,30}\\s*[а-яёА-ЯЁ]{4,30}";

    private static final String PHONE_PATTERN = "[\\+]\\d{12}";

    private static final String JPG_EXTENSION = "jpg";

    private static final String PNG_EXTENSION = "png";

    private static final String JPEG_EXTENSION = "jpeg";

    private static final int DESCRIPTION_MIN_SIZE = 30;

    private static final int DESCRIPTION_MAX_SIZE = 500;

    public static boolean isDataValid(String name, String description, String phone, String imageFileName) {
        if(name != null && description != null && phone != null && imageFileName != null) {
                String extension = FilenameUtils.getExtension(imageFileName);
                if(JPG_EXTENSION.equals(extension) || PNG_EXTENSION.equals(extension) ||
                JPEG_EXTENSION.equals(extension)) {
                    return name.matches(NAME_PATTERN) && phone.matches(PHONE_PATTERN) &&
                            description.length() > DESCRIPTION_MIN_SIZE && description.length() < DESCRIPTION_MAX_SIZE;

                } else {
                    return false;
                }
        } else {
            return false;
        }
    }
}
