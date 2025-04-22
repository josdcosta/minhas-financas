package br.com.dominio.minhasfinancas.api.categoria.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AtualizarDescricaoCategoriaTransacaoRequest {
    @NotBlank(message = "descricao não pode ser em branco")
    private String descricao;
}
