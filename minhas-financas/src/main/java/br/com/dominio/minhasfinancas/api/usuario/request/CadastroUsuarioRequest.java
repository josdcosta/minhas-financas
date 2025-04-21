package br.com.dominio.minhasfinancas.api.usuario.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CadastroUsuarioRequest {
    @NotBlank(message = "nome não pode ser em branco")
    private String nome;

    @NotNull(message = "dataNascimento não pode ser nulo")
    private LocalDate dataNascimento;

    @NotBlank(message = "email não pode ser em branco")
    private String email;

    @NotBlank(message = "senha não pode ser em branco")
    private String senha;

    @NotBlank(message = "cpf não pode ser em branco")
    private String cpf;
    //private List<String> roles = new ArrayList<>();
}
