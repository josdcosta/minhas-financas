package br.com.dominio.minhasfinancas.mapper;

import br.com.dominio.minhasfinancas.api.categoria.request.AtualizarDescricaoCategoriaTransacaoRequest;
import br.com.dominio.minhasfinancas.api.categoria.request.CriarCategoriaTransacaoRequest;
import br.com.dominio.minhasfinancas.api.categoria.response.CriarCategoriaTransacaoResponse;
import br.com.dominio.minhasfinancas.domain.CategoriaTransacao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoriaTransacaoMapper {

    public CategoriaTransacao fromToCategoriaTransacao(CriarCategoriaTransacaoRequest criarCategoriaGastoRequest){
       return CategoriaTransacao.builder()
                .descricao(criarCategoriaGastoRequest.getDescricao())
                .tipo(criarCategoriaGastoRequest.getTipo())
                .build();
    }

    public CriarCategoriaTransacaoResponse fromToCriarCategoriaTransacaoResponse(CategoriaTransacao categoriaGasto){
        return CriarCategoriaTransacaoResponse.builder()
                .id(categoriaGasto.getId())
                .build();
    }

    public List<CategoriaTransacao> fromToCategoriaTransacaoList(List<CriarCategoriaTransacaoRequest> criarCategoriaTransacaoRequestsList){
        return criarCategoriaTransacaoRequestsList.stream().map(this::fromToCategoriaTransacao).collect(Collectors.toList());
    }

    public List<CriarCategoriaTransacaoResponse> fromToCriarCategoriaTransacaoResponseList(List<CategoriaTransacao> categoriaTransacaoList){
        return categoriaTransacaoList.stream().map(this::fromToCriarCategoriaTransacaoResponse).collect(Collectors.toList());
    }

    public CategoriaTransacao fromToCategoriaTransacao(AtualizarDescricaoCategoriaTransacaoRequest atualizarDescricaoCategoriaTransacaoRequest){
        return CategoriaTransacao.builder()
                .id(atualizarDescricaoCategoriaTransacaoRequest.getId())
                .descricao(atualizarDescricaoCategoriaTransacaoRequest.getDescricao())
                .build();
    }

}
