package com.emelyatila.libraryapi.repository.specs;

import com.emelyatila.libraryapi.model.GeneroLivro;
import com.emelyatila.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
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

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublibacao) {
        //select to_char(data_publicacao, 'YYYY') from livro; = anoPublicacao
        return (root,
                query,
                cb) ->
                cb.equal( cb.function("to_char",String.class, root.get("dataPublicacao"),cb.literal("YYYY")) ,anoPublibacao.toString());
    }

    public static Specification<Livro> nomeAutorLike(String nome) {
        return (root,
                query,
                cb) -> {
            Join<Object,Object> joinAutor = root.join("autor", JoinType.INNER);

           return cb.like(cb.upper(joinAutor.get("nome")), "%" + nome.toUpperCase()+ "%");
//            return cb.like(cb.upper(root.get("autor").get("nome")),"%" + nome.toUpperCase() +"%");
        };
    }
}
