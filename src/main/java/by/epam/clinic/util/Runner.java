package by.epam.clinic.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Runner{
    public static void main(String[] args) {
        String password = "DoctPasss123";
        try {
            System.out.println(TextEncryptor.encrypt(password));
        } catch (TextEncryptorException e) {
            e.printStackTrace();
        }

//        SecureRandom random = new SecureRandom();
//        byte[]salt = new byte[16];
//        random.nextBytes(salt);
//        System.out.println(Arrays.toString(salt));
//        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
//        SecretKeyFactory factory;
//        try {
//            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//            byte[]hash = factory.generateSecret(spec).getEncoded();
//            BigInteger bigInteger = new BigInteger(1,hash);
//            System.out.println(bigInteger.toString(16));
//        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
//            e.printStackTrace();
//        }
    }
}
