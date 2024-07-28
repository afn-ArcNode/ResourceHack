package arcnode.reshack.common;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class CrypticUtils {
    private static final String HEADER = "ResourceHack Encrypted Pack";

    public static boolean validate(byte[] data) {
        byte[] shit = HEADER.getBytes(StandardCharsets.UTF_8);
        byte[] header = Arrays.copyOfRange(data, 0, shit.length);
        String headerString = new String(header, StandardCharsets.UTF_8);

        return headerString.equals(HEADER);
    }

    public static byte[] encrypt(String key, byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKey sk = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, sk);

        byte[] enc = cipher.doFinal(data);
        byte[] pad = HEADER.getBytes(StandardCharsets.UTF_8);

        byte[] out = new byte[pad.length + enc.length];
        System.arraycopy(pad, 0, out, 0, pad.length);
        System.arraycopy(enc, 0, out, pad.length, enc.length);

        return out;
    }

    public static byte[] decrypt(String key, byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] shit = HEADER.getBytes(StandardCharsets.UTF_8);
        byte[] raw = Arrays.copyOfRange(data, shit.length, data.length);

        SecretKey sk = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, sk);

        return cipher.doFinal(raw);
    }
}
