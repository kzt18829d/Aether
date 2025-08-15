package com.aether.services;

import com.aether.exceptions.CryptoServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class CryptoService {
    private final static byte FILE_VERSION = 1;
    private final static byte[] FILE_CODE = {'A', 'E', 't', 'H', FILE_VERSION};

    private final static String ALGORITHM = "AES";
    private static final String TRANSF = "AES/CBC/PKCS5Padding";
    private static final String ALG2 = "PBKDF2WithHmacSHA256";

    // in .env
    private static String SECURE_KEY;
    private static final byte[] SALT = "aethersalt-sec".getBytes();

    private static final int ITERATIONS = 100000;
    private static final int KEY_LENGTH = 256;

    // in .env
    private static int BUFFER_SIZE = 8128;

    public static void loadSecureKey(String secureKey) {
        SECURE_KEY = secureKey;
    }

    public static void loadBufferSize(Integer bufferSize) {
        BUFFER_SIZE = bufferSize;
    }

    private void checkSecureKey() {
        Objects.requireNonNull(SECURE_KEY, "Secure key wasn't loaded");
        if (SECURE_KEY.isEmpty()) throw new CryptoServiceException("Secure key cannot be empty");
    }

    private SecretKey getSecretKey(String string) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALG2);
        KeySpec keySpec = new PBEKeySpec(string.toCharArray(), SALT, ITERATIONS, KEY_LENGTH);
        byte[] keyBytes = secretKeyFactory.generateSecret(keySpec).getEncoded();
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }

    private byte[] concat(byte[]... arrays) {
        int arraysLength = 0;
        for(byte[] array: arrays) arraysLength += array.length;
        byte[] result = new byte[arraysLength];
        int pos = 0;
        for(byte[] array: arrays) {
            System.arraycopy(array, 0, result, pos, array.length);
            pos += array.length;
        }
        return result;
    }

    public byte[] encrypt(String dataString) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        checkSecureKey();

        byte[] byteData = dataString.getBytes(StandardCharsets.UTF_8);

        SecretKey secretKey = getSecretKey(SECURE_KEY);
        Cipher cipher = Cipher.getInstance(SECURE_KEY);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] initializeVector = cipher.getIV();
        byte[] encryptedData = cipher.doFinal(byteData);

        return concat(FILE_CODE, initializeVector, encryptedData);
    }

    public String decrypt(BufferedInputStream bufferedInputStream) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        byte[] fileCode = new byte[5];
        if (bufferedInputStream.read(fileCode) != 5 || !Arrays.equals(fileCode, FILE_CODE))
            throw new CryptoServiceException("Invalid file format");

        byte[] initializeVector = new byte[16];
        if (bufferedInputStream.read(initializeVector) != 16)
            throw new CryptoServiceException("Fail to read initialization vector");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = bufferedInputStream.read(buffer)) != -1)
            byteArrayOutputStream.write(buffer, 0, bytesRead);

        byte[] encryptData = byteArrayOutputStream.toByteArray();
        if (encryptData.length == 0) throw new CryptoServiceException("Data not found");

        SecretKey secretKey = getSecretKey(SECURE_KEY);
        Cipher cipher = Cipher.getInstance(TRANSF);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(initializeVector));

        byte[] decrypted = cipher.doFinal(encryptData);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

}
