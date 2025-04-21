package br.com.dominio.minhasfinancas.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "transacoes")
public class Transacao {
    @Id
    private String id;

    private String descricao;

    private LocalDate data;

    private BigDecimal valor;

    private TipoTransacao tipoMovimentacao;

    private String idUsuario;

    private String idCategoriaTransacao;

    @Transient
    private String CategoriaTransacao;

}
