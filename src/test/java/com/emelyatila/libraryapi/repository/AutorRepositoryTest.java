package com.emelyatila.libraryapi.repository;

import com.emelyatila.libraryapi.model.Autor;
import com.emelyatila.libraryapi.model.repository.AutorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Emely");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1950,1,31));

        var autorSalvo = repository.save(autor);
        System .out.println("Autor Salvo: "+ autorSalvo);
    }

    @Test
    public void atualizarTest(){
        // id gerado no banco
        var id = UUID.fromString("d2bd6ba0-2331-4dbf-b0b8-309a12ffec6f");

        Optional<Autor> possivelAutor = repository.findById(id);

        if (possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor: " + autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1960,1,30));
            repository.save(autorEncontrado);
        }
    }


    // Listar autores
    @Test
    public void listarTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    // Contar Autores
    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + repository.count());
    }

    // Detelar pelo ID
    @Test
    public void deletePorIDTest(){
        // inserir id da tabela
        var id = UUID.fromString("98b98492-0fac-448f-9ba8-19e4fe32b49f");
        repository.deleteById(id);
    }

    // Detelar por objeto
    @Test
    public void deleteTest(){
        // inserir id da tabela
        var id = UUID.fromString("b977d639-0cc8-4f57-940f-ba9ced2e10e4");
        // uso o metodo get porque to segura que esse id existe {usar opcional}
        var jose = repository.findById(id).get();
        repository.delete(jose);
    }
}
