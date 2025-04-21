package br.com.dominio.minhasfinancas.api.meta.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AtualizarParcialMetaRequest {
    private String id;
    private String descricao;
    private BigDecimal meta;
}
