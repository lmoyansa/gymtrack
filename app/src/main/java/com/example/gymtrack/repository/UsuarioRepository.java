package com.example.gymtrack.repository;

import com.example.gymtrack.data.GymTrackDatabase;
import com.example.gymtrack.data.dao.UsuarioDao;
import com.example.gymtrack.model.Usuario;
import com.example.gymtrack.security.PasswordUtils;

import java.util.Date;
import java.util.Locale;

public class UsuarioRepository {

    private static UsuarioRepository instance;

    private final UsuarioDao usuarioDao;

    private UsuarioRepository() {
        GymTrackDatabase database =
                GymTrackDatabase.obtenerInstancia();

        usuarioDao = database.usuarioDao();
    }

    public static UsuarioRepository getInstance() {
        if (instance == null) {
            instance = new UsuarioRepository();
        }

        return instance;
    }

    public Usuario registrarUsuario(
            String nombre,
            String email,
            String password
    ) {
        String nombreNormalizado =
                normalizarNombre(nombre);

        String emailNormalizado =
                normalizarEmail(email);

        if (nombreNormalizado.isEmpty()
                || emailNormalizado.isEmpty()
                || password == null
                || password.isEmpty()) {

            return null;
        }

        if (existeEmail(emailNormalizado)) {
            return null;
        }

        String passwordHash =
                PasswordUtils.generarHash(
                        password
                );

        Usuario usuario =
                new Usuario(
                        0,
                        nombreNormalizado,
                        emailNormalizado,
                        passwordHash,
                        new Date()
                );

        long idGenerado =
                usuarioDao.insertar(usuario);

        if (idGenerado <= 0) {
            return null;
        }

        usuario.setIdUsuario(
                (int) idGenerado
        );

        return usuario;
    }

    public Usuario autenticar(
            String email,
            String password
    ) {
        String emailNormalizado =
                normalizarEmail(email);

        if (emailNormalizado.isEmpty()
                || password == null
                || password.isEmpty()) {

            return null;
        }

        Usuario usuario =
                usuarioDao.obtenerPorEmail(
                        emailNormalizado
                );

        if (usuario == null) {
            return null;
        }

        boolean passwordCorrecto =
                PasswordUtils.verificarPassword(
                        password,
                        usuario.getPasswordHash()
                );

        if (!passwordCorrecto) {
            return null;
        }

        return usuario;
    }

    public boolean existeEmail(String email) {
        String emailNormalizado =
                normalizarEmail(email);

        if (emailNormalizado.isEmpty()) {
            return false;
        }

        return usuarioDao.contarPorEmail(
                emailNormalizado
        ) > 0;
    }

    public Usuario obtenerUsuarioPorId(
            int idUsuario
    ) {
        return usuarioDao.obtenerPorId(
                idUsuario
        );
    }

    private String normalizarNombre(
            String nombre
    ) {
        if (nombre == null) {
            return "";
        }

        return nombre.trim();
    }

    private String normalizarEmail(
            String email
    ) {
        if (email == null) {
            return "";
        }

        return email
                .trim()
                .toLowerCase(
                        Locale.ROOT
                );
    }
}