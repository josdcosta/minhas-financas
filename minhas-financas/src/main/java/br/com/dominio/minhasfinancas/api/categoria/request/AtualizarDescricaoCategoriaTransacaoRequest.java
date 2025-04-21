package br.com.dominio.minhasfinancas.api.categoria.request;

import lombok.Data;

@Data
public class AtualizarDescricaoCategoriaTransacaoRequest {
    private String id;
    private String descricao;
}
