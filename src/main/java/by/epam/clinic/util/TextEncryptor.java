package by.epam.clinic.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class TextEncryptor {
    private static final String KEY = "PBKDF2WithHmacSHA1";

    private static final byte[] SALT = new byte[]{-128, -77, 109, -103, 77,
            104, -92, -6, -38, -76, -17, -101, -123, -47, 63, 57};

    private static final int ENCRYPTION_STRENGTH = 65536;

    public static String encrypt(String text) throws TextEncryptorException {
        SecureRandom random = new SecureRandom();
        byte[]salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(text.toCharArray(), SALT, ENCRYPTION_STRENGTH, 128);
        SecretKeyFactory factory;
        try {
            factory = SecretKeyFactory.getInstance(KEY);
            byte[]hash = factory.generateSecret(spec).getEncoded();
            BigInteger bigInteger = new BigInteger(1,hash);
            return bigInteger.toString(16);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
           throw new TextEncryptorException("Error while encrypting text: " + text, e);
        }
    }
}
