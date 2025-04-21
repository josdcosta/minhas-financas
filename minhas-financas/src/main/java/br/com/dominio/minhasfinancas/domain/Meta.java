package br.com.dominio.minhasfinancas.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "metas")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
@CompoundIndexes({
        @CompoundIndex(name = "unique_usuario_categoria", def = "{'idUsuario' : 1, 'idCategoriaTransacao': 1}", unique = true)
})
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
