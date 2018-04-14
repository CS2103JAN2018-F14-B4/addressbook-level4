package seedu.address.logic;
//@@author 592363789
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.BookShelfChangedEvent;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.*;
import java.security.SecureRandom;

public class CipherEngine {

    private static final String defaultKey = "abcdefgh";
    private static final String DES = "DES";
    private static final String ENCODE = "GBK";
    private static String file;

    /**
     *  Encrypting files
     */
    public static void encryptFile(String path) {
        try {
            file = path;
            FileInputStream fis = new FileInputStream(path);
            FileOutputStream fos = new FileOutputStream("data/change.xml");
            EnDecrypt(defaultKey, Cipher.ENCRYPT_MODE, fis, fos);
            File temp = new File(file);
            temp.delete();
            changeName("data/change.xml");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     *  Decrypting files
     */
    public static void decryptFile(String path) {
        try {
            file = path;
            FileInputStream fis = new FileInputStream(path);
            FileOutputStream fos = new FileOutputStream("data/change.xml");
            EnDecrypt(defaultKey, Cipher.DECRYPT_MODE, fis, fos);
            File temp = new File(file);
            temp.delete();
            changeName("data/change.xml");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * Encrypt or Decrypt method
     */
    public static void EnDecrypt(String key, int mode, InputStream inputStream, OutputStream outputStream) throws Throwable {

        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = secretKeyFactory.generateSecret(desKeySpec);
        Cipher cipher = Cipher.getInstance("DES"); // DES/ECB/PKCS5Padding for SunJCE

        if (mode == Cipher.ENCRYPT_MODE) {
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
            Hiding(cipherInputStream, outputStream);
        } else if (mode == Cipher.DECRYPT_MODE) {
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
            Hiding(inputStream, cipherOutputStream);
        }
    }

    /**
     *  Cover the file (hiding the normal file)
     */
    public static void Hiding(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bytes = new byte[64];
        int num;
        while ((num = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, num);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    /**
     * replace the file
     */
    public static boolean changeName(String from) {

        File f0 = new File(from);
        File f1 = new File(file);

        return f0.renameTo(f1);

    }

    /**
     * Use defaultkey to encrypt
     * @param mykey
     * @return
     * @throws Exception
     */
    public static String encrypKey(String mykey) throws Exception {
        byte[] byarray = encrypt(mykey.getBytes(ENCODE), defaultKey.getBytes(ENCODE));
        String encryptkey = new BASE64Encoder().encode(byarray);
        return encryptkey;
    }

    /**
     * Convert the bytearray of string into encrypt key.
     *
     * @param mykey
     * @param key
     *
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] mykey, byte[] key) throws Exception {
        SecureRandom secureRandom = new SecureRandom();

        DESKeySpec desKeySpec = new DESKeySpec(key);

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = secretKeyFactory.generateSecret(desKeySpec);

        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(DES);

        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, securekey, secureRandom);

        return cipher.doFinal(mykey);
    }

    /**
     * Use defaultkey to decrypt
     * @param yourkey
     * @return
     * @throws Exception
     * @throws IOException
     */
    public static String decryptKey(String yourkey) throws IOException, Exception {
        if (yourkey == null) {
            return null;
        }
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] decodeBuffer = base64Decoder.decodeBuffer(yourkey);
        byte[] bytes = decrypt(decodeBuffer, defaultKey.getBytes(ENCODE));
        return new String(bytes, ENCODE);
    }

    /**
     * Convert the bytearray of string into decrypt key.
     *
     * @param yourkey
     * @param key
     *
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] yourkey, byte[] key) throws Exception {

        SecureRandom secureRandom = new SecureRandom();

        DESKeySpec desKeySpec = new DESKeySpec(key);

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = secretKeyFactory.generateSecret(desKeySpec);

        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(DES);

        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, securekey, secureRandom);

        return cipher.doFinal(yourkey);
    }
}
