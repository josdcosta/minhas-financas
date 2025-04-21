package br.com.dominio.minhasfinancas.api.transacao.request;

import br.com.dominio.minhasfinancas.domain.TipoTransacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CriarTransacaoRequest {
    @NotBlank(message = "descricao não pode ser em branco")
    private String id;

    @NotBlank(message = "descricao não pode ser em branco")
    private String descricao;

    @NotNull(message = "data não pode ser em branco")
    private LocalDate data;

    @NotNull(message = "valor não pode ser nulo")
    private BigDecimal valor;

    @NotNull(message = "tipoMovimentacao não pode ser nulo")
    private TipoTransacao tipoMovimentacao;
}
