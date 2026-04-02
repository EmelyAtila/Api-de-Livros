package com.emelyatila.libraryapi.controller.mappers;

import com.emelyatila.libraryapi.controller.dto.UsuarioDTO;
import com.emelyatila.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);


}
