package by.epam.clinic.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public class ImageUploader {
    private String uploadDir;

    public ImageUploader(String uploadDir) {
        this.uploadDir = uploadDir;
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public void write(Part image, String nameForImage) throws IOException {
        String fullFilePath = uploadDir + File.separator + nameForImage;
        image.write(fullFilePath);
    }

    public String generateUniqueFileName(Part image) {
        String imageName = image.getSubmittedFileName();
        String extension = "."  + FilenameUtils.getExtension(imageName);
        String fullFilePath ;
        String fileName;
        do {
            fileName = RandomStringUtils.randomAlphabetic(5, 8)  + extension;
            fullFilePath = uploadDir + File.separator + fileName;
        } while (new File(fullFilePath).exists());
        return fileName;
    }
}
