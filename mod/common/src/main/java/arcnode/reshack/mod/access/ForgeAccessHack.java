package arcnode.reshack.mod.access;

import arcnode.reshack.common.CrypticUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ForgeAccessHack {
    public static boolean accessValidate(byte[] data) {
        return CrypticUtils.validate(data);
    }

    public static byte[] accessDecrypt(String key, byte[] data) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return CrypticUtils.decrypt(key, data);
    }
}
