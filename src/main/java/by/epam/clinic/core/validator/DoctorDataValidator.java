package by.epam.clinic.core.validator;

import org.apache.commons.io.FilenameUtils;

public class DoctorDataValidator {
    private static final String NAME_PATTERN = "[A-Za-zА-Яа-я]{2,20}";

    private static final String JPG_EXTENSION = "jpg";

    private static final String PNG_EXTENSION = "png";

    private static final String JPEG_EXTENSION = "jpeg";

    private static final String SPECIALIZATION_PATTERN = "[-а-яёА-ЯЁ]{4,20}\\s*[а-яёА-ЯЁ]{4,20}";

    private static final String CATEGORY_PATTERN = "[а-яёА-ЯЁ]{4,10}\\s*[а-яёА-ЯЁ]{4,15}";

    public static boolean isDataValid(String name, String surname, String lastname, String specialization,
                                      String category, String imageFileName) {
        if(name != null && surname != null && lastname != null && specialization != null
                && category != null && imageFileName != null) {
            String extension = FilenameUtils.getExtension(imageFileName);
            if(JPG_EXTENSION.equals(extension) || PNG_EXTENSION.equals(extension) ||
                    JPEG_EXTENSION.equals(extension)) {
                return name.matches(NAME_PATTERN) && surname.matches(NAME_PATTERN) && lastname.matches(NAME_PATTERN) &&
                        specialization.matches(SPECIALIZATION_PATTERN) && category.matches(CATEGORY_PATTERN);
            } else {
                return false;
            }
        } else  {
            return false;
        }
    }
}
