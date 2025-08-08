package com.aether.business.filemanagement.JSON;

import com.aether.business.Exceptions.valid.AetherCryptoFilesException;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

/**
 * Класс для шифрования и дешифрования сереализованных в json-String данных устройств
 * <p>Кодировка AES-128</p>
 */
public class AetherCryptoFiles {
    /**
     * Код формата файла
     */
    private final static byte[] FILECODE = {'A', 'e', 'T', 'H'};
    /**
     * версия формата
     */
    private final static byte currentFormatVersion = 1;


    private static final String ALG = "AES";
    private static final String TRANSF = "AES/CBC/PKCS5Padding";

    /**
     * Ключ для генерации SekretKey
     */
    private static final String SECURE_KEY = "lashdjail3wefljndjhalJHSIDIFLG73nklAhbqbhb3HBh31";
    /**
     * Соль для того же
     */
    private static final byte[] SALT = "aethersalt-sec".getBytes();

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGHT = 128;

    private static final int BUFFER_SIZE = 8128;

    /**
     * Генерация SecretKey из пароля
     * @param s
     * @return SecretKey
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private SecretKey getSecretKey(String s) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec keySpec = new PBEKeySpec(s.toCharArray(), SALT, ITERATIONS, KEY_LENGHT);
        byte[] keyBytes = secretKeyFactory.generateSecret(keySpec).getEncoded();
        return new SecretKeySpec(keyBytes, ALG);
    }

//    private Cipher initCipherEn() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException {
//        SecretKey secretKey = getSecretKey(SECURE_KEY);
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//        return cipher;
//    }

    /**
     * Шифрование и сохранение данных в файл .aether
     * @param data
     * @param file
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IOException
     */
    public void secureDataSave(String data, File file) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IOException {
        if (data == null || data.isEmpty()) throw new AetherCryptoFilesException("Data cannot be empty");
        byte[] byteData = data.getBytes(StandardCharsets.UTF_8);

        try {
            SecretKey secretKey = getSecretKey(SECURE_KEY);
            Cipher cipher = Cipher.getInstance(TRANSF);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] iv = cipher.getIV();

            try(FileOutputStream fileOutputStream = new FileOutputStream(file); BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {
                bufferedOutputStream.write(FILECODE);
                bufferedOutputStream.write(currentFormatVersion);
                bufferedOutputStream.write(iv);
                byte[] encrypted = cipher.doFinal(byteData);
                bufferedOutputStream.write(encrypted);
                bufferedOutputStream.flush();
            } catch (Exception e) {
                throw new AetherCryptoFilesException("Encrypt error: " + e.getMessage());
            }

        } catch (Exception e) {
            throw new AetherCryptoFilesException(e.getMessage());
        }
    }

    /**
     * Загружает и дешифрует данные из .aether
     * @param file
     * @return
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchPaddingException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     */
    public String secureDataLoad(File file) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        if(!file.exists() || !file.canRead()) throw new AetherCryptoFilesException("File doesn't exist or can't be read: " + file.getPath());


        try (FileInputStream fileInputStream = new FileInputStream(file); BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)){
            byte[] fileCode = new byte[4];
            if (bufferedInputStream.read(fileCode) != 4 || !Arrays.equals(fileCode, FILECODE))
                throw new AetherCryptoFilesException("Invalid file format");

            int fileVersion = bufferedInputStream.read();
            if (fileVersion != currentFormatVersion)
                throw new AetherCryptoFilesException("Invalid format version");

            byte[] iv = new byte[16];
            if (bufferedInputStream.read(iv) != 16)
                throw new AetherCryptoFilesException("Fail read initialization vector");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read(buffer)) != -1)
                byteArrayOutputStream.write(buffer, 0, bytesRead);

            byte[] enctyptedData = byteArrayOutputStream.toByteArray();
            if (enctyptedData.length == 0) throw new AetherCryptoFilesException("Data not founded");

            SecretKey secretKey = getSecretKey(SECURE_KEY);
            Cipher cipher = Cipher.getInstance(TRANSF);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

            byte[] dectryptedData = cipher.doFinal(enctyptedData);
            return new String(dectryptedData, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new AetherCryptoFilesException(e.getMessage());
        }
    }

}
