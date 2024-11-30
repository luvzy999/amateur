package com.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class KeyUtils {
    @Value("${key_file_path}")
    private static String PATH_OF_FILE;
    private static final Logger log = LoggerFactory.getLogger(KeyUtils.class);

    public static String getAppPrivateKey() {
        return getContent("app-private.txt");
    }

    public static String getAlipayPublicKey() {
        return getContent("aplipay-pub.txt");
    }

    private static String getContent(String name) {
        byte[] bytes = null;
        log.info("key文件路径配置:{}",PATH_OF_FILE);
        try {
            bytes = Files.readAllBytes(Paths.get(PATH_OF_FILE+name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
