package br.com.dominio.minhasfinancas.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Meta {
    @Id
    private String id;
    private String idCategoriaTransacao;
    private String descricao;
    private String idUsuario;
    private BigDecimal meta;

    @Transient
    private BigDecimal gastoAtual;

    @Transient
    private String categoriaTransacao;

}
