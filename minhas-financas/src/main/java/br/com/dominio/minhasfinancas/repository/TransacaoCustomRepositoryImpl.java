package br.com.dominio.minhasfinancas.repository;

import br.com.dominio.minhasfinancas.domain.CategoriaTransacao;
import br.com.dominio.minhasfinancas.domain.Transacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class TransacaoCustomRepositoryImpl implements TransacaoCustomRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CategoriaTransacaoRepository categoriaRepository;

    @Override
    public List<Transacao> buscarTransacoesComCategoriaMesAno(String idUsuario, String idCategoriaTransacao, Integer mes, Integer ano) {
        // Define o primeiro e o último dia do mês
        LocalDate inicioMes = LocalDate.of(ano, mes, 1);
        LocalDate fimMes = inicioMes.withDayOfMonth(inicioMes.lengthOfMonth());

        // Converte para Date
        Date dataInicio = java.sql.Date.valueOf(inicioMes);
        Date dataFim = java.sql.Date.valueOf(fimMes);

        // Monta a query com os filtros
        Criteria criteria = Criteria.where("idUsuario").is(idUsuario)
                .and("data").gte(dataInicio).lte(dataFim)
                .and("idCategoriaTransacao").is(idCategoriaTransacao)
                ;

        Query query = new Query(criteria);
        List<Transacao> transacoes = mongoTemplate.find(query, Transacao.class);

        List<Transacao> resultado = new ArrayList<>();

        for (Transacao transacao : transacoes) {
            String categoriaNome = null;

            if (transacao.getIdCategoriaTransacao() != null) {
                categoriaNome = categoriaRepository.findById(transacao.getIdCategoriaTransacao())
                        .map(CategoriaTransacao::getDescricao)
                        .orElse(null);
            }

            Transacao resposta = new Transacao();
            resposta.setId(transacao.getId());
            resposta.setDescricao(transacao.getDescricao());
            resposta.setData(transacao.getData());
            resposta.setValor(transacao.getValor());
            resposta.setTipoMovimentacao(transacao.getTipoMovimentacao());
            resposta.setIdUsuario(transacao.getIdUsuario());
            resposta.setCategoriaTransacao(categoriaNome);

            resultado.add(resposta);
        }

        return resultado;
    }

    @Override
    public List<Transacao> buscarTransacoesComCategoria(String idUsuario) {
        // Monta a query para buscar todas as transações do usuário
        Criteria criteria = Criteria.where("idUsuario").is(idUsuario);
        Query query = new Query(criteria);
        List<Transacao> transacoes = mongoTemplate.find(query, Transacao.class);

        List<Transacao> resultado = new ArrayList<>();

        for (Transacao transacao : transacoes) {
            String categoriaNome = null;

            if (transacao.getIdCategoriaTransacao() != null) {
                categoriaNome = categoriaRepository.findById(transacao.getIdCategoriaTransacao())
                        .map(CategoriaTransacao::getDescricao)
                        .orElse(null);
            }

            Transacao resposta = new Transacao();
            resposta.setId(transacao.getId());
            resposta.setDescricao(transacao.getDescricao());
            resposta.setData(transacao.getData());
            resposta.setValor(transacao.getValor());
            resposta.setTipoMovimentacao(transacao.getTipoMovimentacao());
            resposta.setIdUsuario(transacao.getIdUsuario());
            resposta.setCategoriaTransacao(categoriaNome); // nome da categoria no lugar do ID

            resultado.add(resposta);
        }

        return resultado;
    }

}

