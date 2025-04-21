package br.com.dominio.minhasfinancas.mapper;

import br.com.dominio.minhasfinancas.api.transacao.request.CriarTransacaoRequest;
import br.com.dominio.minhasfinancas.api.transacao.response.BuscarTransacaoResponse;
import br.com.dominio.minhasfinancas.domain.TipoTransacao;
import br.com.dominio.minhasfinancas.domain.Transacao;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransacaoMapper {
    private String descricao;
    private LocalDate data;
    private BigDecimal valor;
    private TipoTransacao tipoMovimentacao;
    private String idUsuario;

    public Transacao fromToTransacao(CriarTransacaoRequest criarTransacaoRequest) {
        return Transacao.builder()
                .id(criarTransacaoRequest.getId())
                .descricao(criarTransacaoRequest.getDescricao())
                .data(criarTransacaoRequest.getData())
                .valor(criarTransacaoRequest.getValor())
                .tipoMovimentacao(criarTransacaoRequest.getTipoMovimentacao())
                .build();
    }

    public List<Transacao> fromToTransacaoList(List<CriarTransacaoRequest> requestList) {
        return requestList.stream().map(this::fromToTransacao).collect(Collectors.toList());
    }

    public BuscarTransacaoResponse fromToBuscarTransacaoResponse(Transacao transacao){
        return BuscarTransacaoResponse.builder()
                .id(transacao.getId())
                .descricao(transacao.getDescricao())
                .data(transacao.getData())
                .valor(transacao.getValor())
                .tipoMovimentacao(transacao.getTipoMovimentacao())
                .CategoriaTransacao(transacao.getCategoriaTransacao())
                .build();

    }

    public List<BuscarTransacaoResponse> fromToBuscarTransacaoResponseList(List<Transacao> transacaoList) {
        return transacaoList.stream().map(this::fromToBuscarTransacaoResponse).collect(Collectors.toList());
    }

}
