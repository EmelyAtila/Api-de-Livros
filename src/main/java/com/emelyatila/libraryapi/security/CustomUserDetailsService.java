package com.emelyatila.libraryapi.security;

import com.emelyatila.libraryapi.model.Usuario;
import com.emelyatila.libraryapi.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioService service;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = service.obterPorLogin(login);

        if(usuario == null){
            throw new UsernameNotFoundException("Usuario não encontrado");

        }

        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .authorities(usuario.getRoles().toArray(new String[0]))
                // .roles(usuario.getRoles().toArray(new String[usuario.getRoles().size()]))
                .build();
    }
}
