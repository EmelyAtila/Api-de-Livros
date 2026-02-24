package com.emelyatila.libraryapi.services;

import com.emelyatila.libraryapi.model.Autor;
import com.emelyatila.libraryapi.repository.AutorRepository;
import com.emelyatila.libraryapi.validator.AutorValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;

    public AutorService (AutorRepository repository, AutorValidator validator){
        this.validator = validator;
        this.repository = repository;
    }

    public Autor salvar (Autor autor){
        validator.validar(autor);
        return repository.save(autor);
    }

    public void atualizar (Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar é necessario que o autor já esteja cadastrado na base.");
        }
        validator.validar(autor);
        repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Autor autor){
        repository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade){
        if (nome != null && nacionalidade != null){
            return repository.findByNomeAndNacionalidade(nome,nacionalidade);
        }

        if(nome != null){
            return repository.findByNome(nome);
        }

        if(nacionalidade != null){
            return repository.findByNacionalidade(nacionalidade);
        }

        return repository.findAll();
    }
}
