package br.com.dominio.minhasfinancas.api.meta.response;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class BuscarMetasResponse {
    private String id;
    private String descricao;
    private String categoriaTransacao;
    private BigDecimal meta;
    private BigDecimal gastoAtual;
}
