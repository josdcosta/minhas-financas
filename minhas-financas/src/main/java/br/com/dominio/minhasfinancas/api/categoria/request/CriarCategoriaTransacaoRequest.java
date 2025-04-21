package br.com.dominio.minhasfinancas.api.categoria.request;

import br.com.dominio.minhasfinancas.domain.NaturezaRecurso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CriarCategoriaTransacaoRequest {
    @NotBlank(message = "descricao não pode ser em branco")
    private String descricao;

    @NotNull(message = "tipo não pode ser nulo")
    private NaturezaRecurso tipo;
}
