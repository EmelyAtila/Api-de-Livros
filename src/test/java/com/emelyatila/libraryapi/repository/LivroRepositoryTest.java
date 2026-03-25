package com.emelyatila.libraryapi.repository;

import com.emelyatila.libraryapi.model.Autor;
import com.emelyatila.libraryapi.model.GeneroLivro;
import com.emelyatila.libraryapi.model.Livro;
import com.emelyatila.libraryapi.model.repository.AutorRepository;
import com.emelyatila.libraryapi.model.repository.LivroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("1212121-551");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Matematica");
        livro.setDataPublicacao(LocalDate.of(2024,3,25));

        Autor autor = autorRepository
                .findById(UUID.fromString("d1a71dcb-09f0-4dd4-bed9-723f28df94fe"))
                .orElse(null);

        livro.setAutor(autor);

        repository.save(livro);

    }
    @Test
    void pesquisarPorTituloTest(){
        List<Livro> lista = repository.findByTitulo("O roubo da casa assombrada");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorIsbnTest(){
        Optional<Livro> livro = repository.findByIsbn("d1a71dcb-09f0-4dd4-bed9-723f28df94fe");
        livro.ifPresent(System.out::println);
    }

    @Test
    void pesquisarPorAutorTest(){
        Autor autor = autorRepository
                .findById(UUID.fromString("d1a71dcb-09f0-4dd4-bed9-723f28df94fe"))
                .orElse(null);

        List<Livro> lista = repository.findByAutor(autor);
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisarPorTituloAndPrecoTest(){
        List<Livro> lista = repository.findByTituloAndPreco("Matematica", BigDecimal.valueOf(100));
        lista.forEach(System.out::println);
    }





    @Test
    void salvarCascadeTeste(){
        Livro livro = new Livro();
        livro.setIsbn("1212121-551");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Matematica");
        livro.setDataPublicacao(LocalDate.of(2024,3,25));

        Autor autor = new Autor();
        autor.setNome("Emely");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1950,1,31));

        livro.setAutor(autor);

        repository.save(livro);


    }
}
