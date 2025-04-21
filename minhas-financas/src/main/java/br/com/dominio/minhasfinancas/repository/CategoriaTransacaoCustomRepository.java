package br.com.dominio.minhasfinancas.repository;

import br.com.dominio.minhasfinancas.domain.CategoriaTransacao;

public interface CategoriaTransacaoCustomRepository {
    public CategoriaTransacao atualizarById(String id, String descricao);
}
