package br.com.dominio.minhasfinancas.service;

import br.com.dominio.minhasfinancas.domain.CategoriaTransacao;
import br.com.dominio.minhasfinancas.exception.DeleteCategoriaTransacaoException;
import br.com.dominio.minhasfinancas.repository.CategoriaTransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaTransacaoService {

    @Autowired
    private CategoriaTransacaoRepository categoriaTransacaoRepository;

    public CategoriaTransacao criar(CategoriaTransacao categoriaGasto){
        return categoriaTransacaoRepository.save(categoriaGasto);
    }

    public List<CategoriaTransacao> criarTodos(List<CategoriaTransacao> categoriasTransacoes){
        return categoriaTransacaoRepository.saveAll(categoriasTransacoes);
    }

    public List<CategoriaTransacao> buscarTodos(){
        return categoriaTransacaoRepository.findAll();
    }

    public void deletar(String id){
        if (!categoriaTransacaoRepository.existsById(id)) {
            throw new DeleteCategoriaTransacaoException();
        }
        categoriaTransacaoRepository.deleteById(id);
    }

    public CategoriaTransacao atualizarDescricao(CategoriaTransacao categoriaTransacao){
        return categoriaTransacaoRepository.atualizarById(categoriaTransacao.getId(), categoriaTransacao.getDescricao());
    }

}
