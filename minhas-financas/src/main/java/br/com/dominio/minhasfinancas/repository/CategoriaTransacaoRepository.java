package br.com.dominio.minhasfinancas.repository;

import br.com.dominio.minhasfinancas.domain.CategoriaTransacao;
import br.com.dominio.minhasfinancas.domain.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoriaTransacaoRepository extends MongoRepository<CategoriaTransacao, String>, CategoriaTransacaoCustomRepository {
   CategoriaTransacao findByDescricao(String descricao);
}
