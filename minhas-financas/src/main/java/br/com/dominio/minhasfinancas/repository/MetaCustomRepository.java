package br.com.dominio.minhasfinancas.repository;

import br.com.dominio.minhasfinancas.domain.Meta;
import br.com.dominio.minhasfinancas.domain.Transacao;

import java.util.List;

public interface MetaCustomRepository {
    List<Meta> buscarMetasComCategoria(String idUsuario);
    Meta atualizarParcial(Meta meta);
}
