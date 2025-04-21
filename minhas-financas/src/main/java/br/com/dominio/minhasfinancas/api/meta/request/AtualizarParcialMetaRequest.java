package br.com.dominio.minhasfinancas.api.meta.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AtualizarParcialMetaRequest {
    @NotBlank(message = "id não pode ser em branco")
    private String id;
    @NotBlank(message = "descricao não pode ser em branco")
    private String descricao;
    @NotBlank(message = "meta não pode ser em branco")
    private BigDecimal meta;
}
