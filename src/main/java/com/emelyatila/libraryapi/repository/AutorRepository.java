package com.emelyatila.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.emelyatila.libraryapi.model.Autor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor,UUID> {

    // forma dificil
    List<Autor> findByNome(String nome);
    List<Autor> findByNacionalidade(String nacionalidade);
    List<Autor> findByNomeAndNacionalidade(String nome, String Nacionalidade);

    Optional<Autor> findByNomeAndDataNascimentoAndNacionalidade(
            String nome, LocalDate dataNascimento, String nacionalidade
    );

    // metodo leve, mas é necessario o autor na validação
//    boolean existsByNomeAndDataNascimentoAndNacionalide(
//            String nome, LocalDate dataNascimento, String nacionalidade
//    );
}