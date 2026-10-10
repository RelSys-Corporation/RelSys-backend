package com.infrastructure.security.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class HashUtils {
    public static final Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    private static String applyPepper(String content) {
        return System.getenv("PEPPER_CODE") + content;
    }

    public static String encode(String content) {
        content = applyPepper(content);
        return argon2.hash(2, 65536, 2, content.toCharArray());
    }

    public static boolean verify(String hashedContent, String content) {
        content = applyPepper(content);
        return argon2.verify(hashedContent, content.toCharArray());
    }
}
