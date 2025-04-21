package br.com.dominio.minhasfinancas.repository;

import br.com.dominio.minhasfinancas.domain.Transacao;

import java.util.List;

public interface TransacaoCustomRepository {
    public List<Transacao> buscarTransacoesComCategoriaMesAno(String idUsuario, String idCategoriaTransacao, Integer mes, Integer ano);
    public List<Transacao> buscarTransacoesComCategoria(String idUsuario);
    }
