package br.com.dominio.minhasfinancas.api.transacao.response;

import br.com.dominio.minhasfinancas.domain.TipoTransacao;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
public class BuscarTransacaoResponse {
    private String id;

    private String descricao;

    private LocalDate data;

    private BigDecimal valor;

    private TipoTransacao tipoMovimentacao;

    private String CategoriaTransacao;

}
