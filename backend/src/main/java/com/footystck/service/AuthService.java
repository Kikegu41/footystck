package com.footystck.service;

import com.footystck.dto.DTOMapper;
import com.footystck.dto.LoginRequest;
import com.footystck.dto.RegistroRequest;
import com.footystck.dto.UsuarioDTO;
import com.footystck.model.Usuario;
import com.footystck.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

// Logica de registro e inicio de sesion (login basico: compara la contraseña).
@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Crea un usuario nuevo y lo devuelve.
    public UsuarioDTO registrar(RegistroRequest req) {
        if (usuarioRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Ese email ya esta registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(req.getNombre());
        usuario.setEmail(req.getEmail());
        usuario.setPassword(req.getPassword());
        usuario.setRol("cliente");
        return DTOMapper.toUsuarioDTO(usuarioRepository.save(usuario));
    }

    // Comprueba el email y la contraseña; si son correctos devuelve el usuario.
    public UsuarioDTO login(LoginRequest req) {
        Usuario usuario = usuarioRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Email o contraseña incorrectos"));

        if (!usuario.getPassword().equals(req.getPassword())) {
            throw new RuntimeException("Email o contraseña incorrectos");
        }
        return DTOMapper.toUsuarioDTO(usuario);
    }
}
