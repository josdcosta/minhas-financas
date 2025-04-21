package br.com.dominio.minhasfinancas.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;

    private String nome;

    private LocalDate dataNascimento;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    private String cpf;

    private String senha;

    @Transient
    private String token;

    @NotNull(message = "roles não pode ser nulo")
    private List<String> roles = new ArrayList<>();
}
