package br.com.dominio.minhasfinancas.repository;

import br.com.dominio.minhasfinancas.domain.CategoriaTransacao;
import br.com.dominio.minhasfinancas.domain.Meta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public class CategoriaTransacaoCustomRepositoryImpl implements CategoriaTransacaoCustomRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public CategoriaTransacao atualizarById(String id, String descricao) {
        Query query = new Query(Criteria.where("_id").is(id));

        Update update = new Update()
                .set("descricao", descricao);

        return mongoTemplate.findAndModify(
                query,
                update,
                FindAndModifyOptions.options().returnNew(true),
                CategoriaTransacao.class
        );
    }
}

