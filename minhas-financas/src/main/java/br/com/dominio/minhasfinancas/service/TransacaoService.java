package br.com.dominio.minhasfinancas.service;

import br.com.dominio.minhasfinancas.domain.Transacao;
import br.com.dominio.minhasfinancas.exception.SalvarListaTransacaoException;
import br.com.dominio.minhasfinancas.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    TransacaoRepository transacaoRepository;

    public void salvarTodos(List<Transacao> transacoes){
        try{
            transacoes = transacaoRepository.saveAll(transacoes);

            if(transacoes.isEmpty()){
                throw new SalvarListaTransacaoException();
            }
        } catch (Exception e){
            throw new SalvarListaTransacaoException();
        }
    }

    public List<Transacao> buscarTodos(String idCategoria){
        return transacaoRepository.buscarTransacoesComCategoria(idCategoria);
    }

}
