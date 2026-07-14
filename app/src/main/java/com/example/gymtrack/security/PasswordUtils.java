package com.example.gymtrack.security;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class PasswordUtils {

    private static final String ALGORITMO =
            "PBKDF2WithHmacSHA256";

    private static final String PREFIJO =
            "pbkdf2_sha256";

    private static final int ITERACIONES =
            120_000;

    private static final int LONGITUD_SALT_BYTES =
            16;

    private static final int LONGITUD_CLAVE_BITS =
            256;

    private PasswordUtils() {
    }

    public static String generarHash(
            String password
    ) {
        if (password == null) {
            throw new IllegalArgumentException(
                    "La contraseña no puede ser nula"
            );
        }

        try {
            byte[] salt =
                    new byte[LONGITUD_SALT_BYTES];

            SecureRandom secureRandom =
                    new SecureRandom();

            secureRandom.nextBytes(salt);

            byte[] hash =
                    calcularHash(
                            password.toCharArray(),
                            salt,
                            ITERACIONES
                    );

            String saltBase64 =
                    Base64.encodeToString(
                            salt,
                            Base64.NO_WRAP
                    );

            String hashBase64 =
                    Base64.encodeToString(
                            hash,
                            Base64.NO_WRAP
                    );

            return PREFIJO
                    + "$"
                    + ITERACIONES
                    + "$"
                    + saltBase64
                    + "$"
                    + hashBase64;

        } catch (Exception exception) {
            throw new IllegalStateException(
                    "No se pudo proteger la contraseña",
                    exception
            );
        }
    }

    public static boolean verificarPassword(
            String password,
            String hashGuardado
    ) {
        if (password == null
                || hashGuardado == null
                || hashGuardado.trim().isEmpty()) {

            return false;
        }

        try {
            String[] partes =
                    hashGuardado.split("\\$");

            if (partes.length != 4) {
                return false;
            }

            if (!PREFIJO.equals(partes[0])) {
                return false;
            }

            int iteraciones =
                    Integer.parseInt(partes[1]);

            byte[] salt =
                    Base64.decode(
                            partes[2],
                            Base64.NO_WRAP
                    );

            byte[] hashEsperado =
                    Base64.decode(
                            partes[3],
                            Base64.NO_WRAP
                    );

            byte[] hashCalculado =
                    calcularHash(
                            password.toCharArray(),
                            salt,
                            iteraciones
                    );

            return MessageDigest.isEqual(
                    hashEsperado,
                    hashCalculado
            );

        } catch (Exception exception) {
            return false;
        }
    }

    private static byte[] calcularHash(
            char[] password,
            byte[] salt,
            int iteraciones
    ) throws Exception {

        PBEKeySpec keySpec =
                new PBEKeySpec(
                        password,
                        salt,
                        iteraciones,
                        LONGITUD_CLAVE_BITS
                );

        try {
            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance(
                            ALGORITMO
                    );

            return factory
                    .generateSecret(keySpec)
                    .getEncoded();

        } finally {
            keySpec.clearPassword();
        }
    }
}