package br.com.dominio.minhasfinancas.api.meta.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CriarMetaRequest {
    @NotBlank(message = "categoriaTransacao não pode ser em branco")
    private String categoriaTransacao;

    @NotBlank(message = "descricao não pode ser em branco")
    private String descricao;

    @NotNull(message = "meta não pode ser nulo")
    private BigDecimal meta;
}
