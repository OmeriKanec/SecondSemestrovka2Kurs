package java.helpers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {
    public static String hash(String password) {
        String saltedPassword = password + "ni";
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] pb = new byte[0];
        pb = saltedPassword.getBytes(StandardCharsets.UTF_8);
        byte[] digestedPassword = messageDigest.digest(pb);
        String hashedPassword = new String(digestedPassword, StandardCharsets.UTF_8);
        return hashedPassword;
    }
}