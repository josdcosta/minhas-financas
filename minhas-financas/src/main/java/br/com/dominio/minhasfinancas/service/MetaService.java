package br.com.dominio.minhasfinancas.service;

import br.com.dominio.minhasfinancas.domain.CategoriaTransacao;
import br.com.dominio.minhasfinancas.domain.Meta;
import br.com.dominio.minhasfinancas.domain.Transacao;
import br.com.dominio.minhasfinancas.exception.CategoriaNaoEncontradaException;
import br.com.dominio.minhasfinancas.exception.DeleteMetaException;
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

    private void preencherCategoria(Meta meta) {
        CategoriaTransacao categoriaTransacao = categoriaTransacaoRepository.findByDescricao(meta.getCategoriaTransacao());
        if (categoriaTransacao == null) {
            throw new CategoriaNaoEncontradaException();
        }
        meta.setIdCategoriaTransacao(categoriaTransacao.getId());
    }

    public Meta salvar(Meta meta){
        preencherCategoria(meta);
        try {
            return metaRepository.save(meta);
        } catch(Exception e) {
            throw new SalvarMetaException();
        }
    }

    public List<Meta> salvarTodos(List<Meta> metas){

        for(Meta meta: metas){
            preencherCategoria(meta);
        }

        try {
            return metaRepository.saveAll(metas);
        }
        catch(Exception e) {
            throw new SalvarMetaException();
        }
    }

    public List<Meta> buscarTodosMesAno(String idUsuario, Integer mes, Integer ano){
        List<Meta> metas = metaRepository.buscarMetasComCategoria(idUsuario);

        for (Meta meta : metas) {
            String idCategoriaMeta = meta.getIdCategoriaTransacao();

            List<Transacao> transacoes = transacaoRepository.buscarTransacoesComCategoriaMesAno(idUsuario, idCategoriaMeta, mes, ano);

            BigDecimal total = transacoes.stream()
                    .map(Transacao::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            meta.setGastoAtual(total);
        }
        return metas;
    }

    public void deletar(String id){
        if (!metaRepository.existsById(id)) {
            throw new DeleteMetaException();
        }
       metaRepository.deleteById(id);
    }

    public Meta atualizarParcial(Meta meta){
       return metaRepository.atualizarParcial(meta);
    }
}
