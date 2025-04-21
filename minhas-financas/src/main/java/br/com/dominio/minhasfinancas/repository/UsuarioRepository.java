package br.com.dominio.minhasfinancas.repository;

import br.com.dominio.minhasfinancas.domain.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Usuario findByCpf(String cpf);
}
