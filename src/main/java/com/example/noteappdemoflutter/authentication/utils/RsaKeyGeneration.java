package com.example.noteappdemoflutter.authentication.utils;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class RsaKeyGeneration {

    public static void main(String[] args) throws NoSuchAlgorithmException, FileNotFoundException, IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        var keyPair = keyPairGenerator.generateKeyPair();
        byte[] pub = keyPair.getPublic().getEncoded();
        byte[] priv = keyPair.getPrivate().getEncoded();
        String targetDir = "src/main/resources/certs/";
        Path path = Path.of(targetDir);
        Files.createDirectories(path);
        PemWriter pemWriter = new PemWriter(new OutputStreamWriter(new FileOutputStream(targetDir + "pub.pem")));
        PemObject pemObject = new PemObject("PUBLIC KEY", pub);
        pemWriter.writeObject(pemObject);
        pemWriter.close();
        PemWriter pemWriter2 = new PemWriter(new OutputStreamWriter(new FileOutputStream(targetDir + "priv.pem")));
        PemObject pemObject2 = new PemObject("PRIVATE KEY", priv);
        pemWriter2.writeObject(pemObject2);
        pemWriter2.close();
    }
}

