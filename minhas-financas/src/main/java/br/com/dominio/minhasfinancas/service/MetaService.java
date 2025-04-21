package br.com.dominio.minhasfinancas.service;

import br.com.dominio.minhasfinancas.domain.CategoriaTransacao;
import br.com.dominio.minhasfinancas.domain.Meta;
import br.com.dominio.minhasfinancas.domain.Transacao;
import br.com.dominio.minhasfinancas.exception.CategoriaNaoEncontradaException;
import br.com.dominio.minhasfinancas.exception.SalvarMetaException;
import br.com.dominio.minhasfinancas.repository.CategoriaTransacaoRepository;
import br.com.dominio.minhasfinancas.repository.MetaRepository;
import br.com.dominio.minhasfinancas.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MetaService {

    @Autowired
    private MetaRepository metaRepository;

    @Autowired
    private CategoriaTransacaoRepository categoriaTransacaoRepository;

    @Autowired
    TransacaoRepository transacaoRepository;

    @Autowired
    private CategoriaTransacaoRepository categoriaGastoRepository;

    public Meta salvar(Meta meta){
        CategoriaTransacao categoriaTransacao = categoriaTransacaoRepository.findByDescricao(meta.getCategoriaTransacao());
        meta.setIdCategoriaTransacao(categoriaTransacao.getId());

        try {
            return metaRepository.save(meta);
        }
        catch(Exception e) {
            throw new SalvarMetaException();
        }
    }

    public List<Meta> BuscarTodos(String idUsuario, Integer mes, Integer ano){
        List<Meta> metaList = metaRepository.buscarMetasComCategoria(idUsuario);

        for (Meta meta : metaList) {
            String idCategoriaMeta = meta.getIdCategoriaTransacao();

            List<Transacao> transacaoList = transacaoRepository.buscarTransacoesComCategoriaMesAno(idUsuario, idCategoriaMeta, mes, ano);

            BigDecimal total = transacaoList.stream()
                    .map(Transacao::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            meta.setGastoAtual(total);
        }
        return metaList;
    }

    public void deletar(String id){
       metaRepository.deleteById(id);
    }

    public Meta atualizarParcial(Meta meta){
       return metaRepository.atualizarParcial(meta);
    }
}
