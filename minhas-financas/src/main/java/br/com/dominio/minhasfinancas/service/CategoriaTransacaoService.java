package br.com.dominio.minhasfinancas.service;

import br.com.dominio.minhasfinancas.domain.CategoriaTransacao;
import br.com.dominio.minhasfinancas.repository.CategoriaTransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaTransacaoService {

    @Autowired
    private CategoriaTransacaoRepository categoriaGastoRepository;

    public CategoriaTransacao criar(CategoriaTransacao categoriaGasto){
        return categoriaGastoRepository.save(categoriaGasto);
    }

    public List<CategoriaTransacao> criarTodos(List<CategoriaTransacao> categoriaGastoList){
        return categoriaGastoRepository.saveAll(categoriaGastoList);
    }

    public List<CategoriaTransacao> buscarTodos(){
        return categoriaGastoRepository.findAll();
    }

    public void deletar(String id){
        categoriaGastoRepository.deleteById(id);
    }

    public CategoriaTransacao atualizarDescricao(CategoriaTransacao categoriaTransacao){
        return categoriaGastoRepository.atualizarById(categoriaTransacao.getId(), categoriaTransacao.getDescricao());
    }

}
