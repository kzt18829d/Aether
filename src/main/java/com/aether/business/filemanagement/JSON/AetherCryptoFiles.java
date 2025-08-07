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

public class AetherCryptoFiles {
    private final static byte[] FILECODE = {'A', 'e', 'T', 'H'};
    private final static byte currentFormatVersion = 1;

    private static final String ALG = "AES";
    private static final String SECURE_KEY = "lashdjail3wefljndjhalJHSIDIFLG73nklAhbqbhb3HBh31";
    private static final byte[] SALT = "aethersalt-sec".getBytes();

    private SecretKey getSecretKey(String s) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec keySpec = new PBEKeySpec(s.toCharArray(), SALT, 535, 128);
        return new SecretKeySpec(secretKeyFactory.generateSecret(keySpec).getEncoded(), ALG);
    }

//    private Cipher initCipherEn() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException {
//        SecretKey secretKey = getSecretKey(SECURE_KEY);
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//        return cipher;
//    }

    public void secureDataSave(String data, File file) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IOException {
        byte[] byteData = data.getBytes(StandardCharsets.UTF_8);

        SecretKey secretKey = getSecretKey(SECURE_KEY);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(cipher.getIV());

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream, cipher);

            fileOutputStream.write(FILECODE);
            fileOutputStream.write(currentFormatVersion);
            fileOutputStream.write(ivParameterSpec.getIV());

            cipherOutputStream.write(byteData);

        } catch (IOException e) {
            throw e;
        }
    }

    public byte[] secureDataLoad(File file) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);

            byte[] codeBytes = new byte[4];
            fileInputStream.read(codeBytes);
            if (!Arrays.equals(codeBytes, FILECODE)) throw new AetherCryptoFilesException("Invalid file format.");
            int fileVersion = fileInputStream.read();
            if (fileVersion != currentFormatVersion) throw new AetherCryptoFilesException("Invalid format version.");

            byte[] iv = new byte[16];
            fileInputStream.read(iv);
            SecretKey secretKey = getSecretKey(SECURE_KEY);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

            CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher);
            ByteArrayOutputStream unsecuredData = new ByteArrayOutputStream();

            byte[] buffer = new byte[3096];
            int bufferRead;
            while ((bufferRead = cipherInputStream.read(buffer)) != -1) {
                unsecuredData.write(buffer, 0, bufferRead);
            }

            return unsecuredData.toByteArray();

        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException |
                 InvalidAlgorithmParameterException | InvalidKeyException e) {
            throw e;
        }
    }

}
