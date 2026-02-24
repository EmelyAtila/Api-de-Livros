package com.emelyatila.libraryapi.controller;

import com.emelyatila.libraryapi.controller.dto.AutorDTO;
import com.emelyatila.libraryapi.controller.dto.ErroResposta;
import com.emelyatila.libraryapi.exceptions.RegistroDuplicadoException;
import com.emelyatila.libraryapi.model.Autor;
import com.emelyatila.libraryapi.services.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
//http://localhost:8080/autores
public class AutorController {

    private final AutorService service;

    public AutorController(AutorService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody AutorDTO autor){
        try {
            Autor autorEntidade = autor.mapearParaAutor();
            service.salvar(autorEntidade);

            // criar url com id
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/ {id}")
                    .buildAndExpand(autorEntidade.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch(RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if(autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            AutorDTO dto =  new AutorDTO(autor.getId(),
                                         autor.getNome(),
                                         autor.getDataNascimento(),
                                         autor.getNacionalidade());

            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build(); 
    }
// opção para limpar quebra de linha na url, mas no postman usei ../:id para receber o id por parametro e não url
//    @GetMapping("{id}")
//    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
//        try {
//            // Remove espaços e quebras de linha
//            UUID idAutor = UUID.fromString(id.trim());
//
//            Optional<Autor> autorOptional = service.obterPorId(idAutor);
//
//            if (autorOptional.isPresent()) {
//                Autor autor = autorOptional.get();
//                AutorDTO dto = new AutorDTO(
//                        autor.getId(),
//                        autor.getNome(),
//                        autor.getDataNascimento(),
//                        autor.getNacionalidade()
//                );
//                return ResponseEntity.ok(dto);
//            }
//
//            return ResponseEntity.notFound().build();
//
//        } catch (IllegalArgumentException e) {
//            // ID inválido → retorna 400 em vez de 500
//            return ResponseEntity.badRequest().build();
//        }
//    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        service.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List <AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false)String nacionalidade){

            List<Autor> resultado = service.pesquisa(nome,nacionalidade);
            List<AutorDTO> lista = resultado
                    .stream()
                    .map(autor -> new AutorDTO(
                            autor.getId(),
                            autor.getNome(),
                            autor.getDataNascimento(),
                            autor.getNacionalidade())
                    ).collect(Collectors.toList());

            return ResponseEntity.ok(lista);

    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable("id") String id,
            @RequestBody AutorDTO dto){

        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        service.atualizar(autor);

        return ResponseEntity.noContent().build();
    }
}
