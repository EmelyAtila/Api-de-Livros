package com.emelyatila.libraryapi.controller;

import com.emelyatila.libraryapi.controller.dto.CadastroLivroDTO;
import com.emelyatila.libraryapi.controller.dto.ErroResposta;
import com.emelyatila.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.emelyatila.libraryapi.controller.mappers.LivroMapper;
import com.emelyatila.libraryapi.exceptions.RegistroDuplicadoException;
import com.emelyatila.libraryapi.model.GeneroLivro;
import com.emelyatila.libraryapi.model.Livro;
import com.emelyatila.libraryapi.services.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        // Acrescengtar Lógica para mapear dto para entidade
        Livro livro = mapper.toEntity(dto);
        // enviar a entidade para o service salvar na base de dados
        service.salvar(livro);
        // criar url para acesso dos dados do livro
        //retornar código created com header location
        var url = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(
            @PathVariable("id") String id){

        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @DeleteMapping ("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    service.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(value ="isbn",required = false)
            String isbn,
            @RequestParam(value ="titulo",required = false)
            String titulo,
            @RequestParam(value ="nome-autor",required = false)
            String nomeAutor,
            @RequestParam(value ="genero",required = false)
            GeneroLivro genero,
            @RequestParam(value ="ano-publicacao",required = false)
            Integer anoPublicacao
    ){
        var resultado = service.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao);

        var lista = resultado.stream().map(mapper::toDTO).collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody @Valid CadastroLivroDTO dto){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    Livro entidadeAuxiliar = mapper.toEntity(dto);
                    livro.setDataPublicacao(entidadeAuxiliar.getDataPublicacao());
                    livro.setIsbn(entidadeAuxiliar.getIsbn());
                    livro.setPreco(entidadeAuxiliar.getPreco());
                    livro.setGenero(entidadeAuxiliar.getGenero());
                    livro.setTitulo(entidadeAuxiliar.getTitulo());
                    livro.setAutor(entidadeAuxiliar.getAutor());

                    service.atualizar(livro);
                   return ResponseEntity.noContent().build();

                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

}
