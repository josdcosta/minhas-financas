package br.com.dominio.minhasfinancas.repository;

import br.com.dominio.minhasfinancas.domain.CategoriaTransacao;
import br.com.dominio.minhasfinancas.domain.Meta;
import br.com.dominio.minhasfinancas.domain.Transacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;

public class MetaCustomRepositoryImpl implements MetaCustomRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CategoriaTransacaoRepository categoriaRepository;

    @Override
    public List<Meta> buscarMetasComCategoria(String idUsuario) {
        Query query = new Query(Criteria.where("idUsuario").is(idUsuario));

        List<Meta> metaList = mongoTemplate.find(query, Meta.class);

        List<Meta> resultado = new ArrayList<>();

        for (Meta meta : metaList) {
            String categoriaNome = null;

            if (meta.getIdCategoriaTransacao() != null) {
                categoriaNome = categoriaRepository.findById(meta.getIdCategoriaTransacao())
                        .map(CategoriaTransacao::getDescricao)
                        .orElse(null);
            }

            Meta resposta = new Meta();
            resposta.setId(meta.getId());
            resposta.setDescricao(meta.getDescricao());
            resposta.setMeta(meta.getMeta());
            resposta.setIdUsuario(meta.getIdUsuario());
            resposta.setCategoriaTransacao(categoriaNome);
            resposta.setIdCategoriaTransacao(meta.getIdCategoriaTransacao());

            resultado.add(resposta);
        }

        return resultado;
    }


    @Override
    public Meta atualizarParcial(Meta meta) {
        Query query = new Query(Criteria.where("_id").is(meta.getId()));
        Update update = new Update();

        if (meta.getDescricao() != null && !meta.getDescricao().isEmpty()) {
            update.set("descricao", meta.getDescricao());
        }

        if (meta.getMeta() != null) {
            update.set("meta", meta.getMeta());
        }

        if (update.getUpdateObject().isEmpty()) {
            return mongoTemplate.findOne(query, Meta.class);
        }

        return mongoTemplate.findAndModify(
                query,
                update,
                FindAndModifyOptions.options().returnNew(true),
                Meta.class
        );
    }
}
