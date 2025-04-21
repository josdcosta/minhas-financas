package br.com.dominio.minhasfinancas.api.usuario.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginUsuarioRequest {
    @NotBlank(message = "dataNascimento não pode ser em branco")
    private String cpf;

    @NotBlank(message = "dataNascimento não pode ser em branco")
    private String senha;
}
