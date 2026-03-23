package com.emelyatila.libraryapi.validator;

import com.emelyatila.libraryapi.exceptions.CampoInvalidoException;
import com.emelyatila.libraryapi.exceptions.RegistroDuplicadoException;
import com.emelyatila.libraryapi.model.Livro;
import com.emelyatila.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;

    private final LivroRepository respository;

    public void validar(Livro livro){
        if(existeLivroComIsbn(livro)){
            throw new RegistroDuplicadoException("Isbn já cadastrado");
        }

        if (isPrecoObrigatorioNulo(livro)){
            throw new CampoInvalidoException("preço","Para livros com ano de publicação a parti de 2020, o preço é obrigatório");
        }

    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {

        return livro.getPreco() == null &&
                livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    private boolean existeLivroComIsbn(Livro livro){
         Optional<Livro> livroEncontrado = respository.findByIsbn(livro.getIsbn());

         if (livro.getId() == null){
             return livroEncontrado.isPresent();
         }

         return livroEncontrado
                 .map(Livro::getId)
                 .stream()
                 .anyMatch(id -> !id.equals(livro.getId()));
    }


}
