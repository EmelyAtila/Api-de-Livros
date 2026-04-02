package com.emelyatila.libraryapi.services;

import com.emelyatila.libraryapi.model.Usuario;
import com.emelyatila.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// Completar Lógica de usuários .

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    private final PasswordEncoder encoder;


    public void salvar(Usuario usuario){
        var senha = usuario.getSenha();
        usuario.setSenha(encoder.encode(senha));

        repository.save(usuario);
    }


    public Usuario obterPorLogin(String login){
        return repository.findByLogin(login);
    }

}
