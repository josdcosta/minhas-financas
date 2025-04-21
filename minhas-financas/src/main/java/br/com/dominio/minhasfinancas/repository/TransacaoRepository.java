package br.com.dominio.minhasfinancas.repository;

import br.com.dominio.minhasfinancas.domain.Transacao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TransacaoRepository extends MongoRepository<Transacao, String>, TransacaoCustomRepository {
}
