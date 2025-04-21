package br.com.dominio.minhasfinancas.repository;

import br.com.dominio.minhasfinancas.domain.Meta;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MetaRepository extends MongoRepository<Meta, String>, MetaCustomRepository {
    int deleteByIdCategoriaTransacaoAndIdUsuario(String categoria, String idUsuario);
}
