package com.emelyatila.libraryapi.repository.specs;

import com.emelyatila.libraryapi.model.GeneroLivro;
import com.emelyatila.libraryapi.model.Livro;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn){
        return (root,
                 query,
                 cb) -> cb.equal(root.get("isbn"),isbn);
    }

    public static Specification<Livro> tituloLike(String titulo){
        //upper(livro.titulo) like(%:param%)
        return (root,
                query,
                cb) ->
                cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");// comparar titulo com caixa alta
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero) {
        return (root,
                query,
                cb) -> cb.equal(root.get("genero"), genero);
    }
}
