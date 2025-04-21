package br.com.dominio.minhasfinancas.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Builder
@Data
@Document(collection = "categoria_transacoes")
public class CategoriaTransacao {
    @Id
    private String id;

    @Indexed(unique = true)
    private String descricao;

    private NaturezaRecurso tipo;
}
