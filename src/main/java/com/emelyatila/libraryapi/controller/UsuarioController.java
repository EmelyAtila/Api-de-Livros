package com.emelyatila.libraryapi.controller;

import com.emelyatila.libraryapi.controller.dto.UsuarioDTO;
import com.emelyatila.libraryapi.controller.mappers.UsuarioMapper;
import com.emelyatila.libraryapi.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    // Não esquecer de completar com responseEntity no final

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO dto){
        var usuario = mapper.toEntity(dto);
        service.salvar(usuario);
    }

}
