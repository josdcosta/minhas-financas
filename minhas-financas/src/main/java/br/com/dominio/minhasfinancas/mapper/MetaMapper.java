package br.com.dominio.minhasfinancas.mapper;

import br.com.dominio.minhasfinancas.api.meta.request.AtualizarParcialMetaRequest;
import br.com.dominio.minhasfinancas.api.meta.request.CriarMetaRequest;
import br.com.dominio.minhasfinancas.api.meta.response.BuscarMetaResponse;
import br.com.dominio.minhasfinancas.api.meta.response.CriarMetaResponse;
import br.com.dominio.minhasfinancas.domain.Meta;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MetaMapper {

    public Meta fromToMeta(CriarMetaRequest criarMetaRequest){
      return Meta.builder()
            .meta(criarMetaRequest.getMeta())
            .descricao(criarMetaRequest.getDescricao())
            .categoriaTransacao(criarMetaRequest.getCategoriaTransacao())
            .build();
    }

    public CriarMetaResponse fromToCriarMetaResponse(Meta meta){
        return CriarMetaResponse.builder()
                .id(meta.getId())
                .build();
    }

    public BuscarMetaResponse fromToBuscarMetaResponse(Meta meta){
        return BuscarMetaResponse.builder()
                .id(meta.getId())
                .descricao(meta.getDescricao())
                .categoriaTransacao(meta.getCategoriaTransacao())
                .meta(meta.getMeta())
                .gastoAtual(meta.getGastoAtual())
                .build();
    }

    public List<BuscarMetaResponse> fromtoBuscarMetaResponseList(List<Meta> metaList){
        return metaList.stream().map(this::fromToBuscarMetaResponse).collect(Collectors.toList());
    }

    public List<Meta> fromToMetaList(List<CriarMetaRequest> criarMetaRequestList){
        return criarMetaRequestList.stream().map(this::fromToMeta).collect(Collectors.toList());
    }

    public List<CriarMetaResponse> fromToCriarMetaResponseList(List<Meta> metaList){
        return metaList.stream().map(this::fromToCriarMetaResponse).collect(Collectors.toList());
    }


    public Meta fromToMeta(AtualizarParcialMetaRequest atualizarParcialMetaRequest){
        return Meta.builder()
                .id(atualizarParcialMetaRequest.getId())
                .descricao(atualizarParcialMetaRequest.getDescricao())
                .meta(atualizarParcialMetaRequest.getMeta())
                .build();
    }

}
