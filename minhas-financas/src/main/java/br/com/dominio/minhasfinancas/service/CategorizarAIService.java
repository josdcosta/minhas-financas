package br.com.dominio.minhasfinancas.service;

import br.com.dominio.minhasfinancas.domain.CategoriaTransacao;
import br.com.dominio.minhasfinancas.domain.Transacao;
import br.com.dominio.minhasfinancas.exception.CategorizarAIException;
import br.com.dominio.minhasfinancas.repository.CategoriaTransacaoRepository;
import com.azure.ai.inference.ChatCompletionsClient;
import com.azure.ai.inference.ChatCompletionsClientBuilder;
import com.azure.ai.inference.models.ChatCompletions;
import com.azure.ai.inference.models.ChatCompletionsOptions;
import com.azure.ai.inference.models.ChatRequestMessage;
import com.azure.ai.inference.models.ChatRequestUserMessage;
import com.azure.core.credential.AzureKeyCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategorizarAIService {

    @Value("${github.token}")
    private String key;

    @Value("${azure.ai.endpoint}")
    private String endpoint;

    @Value("${azure.ai.model}")
    private String model;

    @Autowired
    private CategoriaTransacaoRepository categoriaGastoRepository;

    public List<Transacao> categorizar(List<Transacao> transacaoList) {

        // Obtendo todas as categorias com ID e descrição
        List<CategoriaTransacao> categoriasList = categoriaGastoRepository.findAll();

        // Mapeia descrição -> CategoriaTransacao
        Map<String, CategoriaTransacao> mapaDescricaoCategoria = categoriasList.stream()
                .collect(Collectors.toMap(CategoriaTransacao::getDescricao, cat -> cat));

        // Lista de descrições de categorias
        List<String> descricoesCategorias = categoriasList.stream()
                .map(CategoriaTransacao::getDescricao)
                .collect(Collectors.toList());

        // Lista de descrições das transações
        List<String> descricoesTransacoes = transacaoList.stream()
                .map(Transacao::getDescricao)
                .collect(Collectors.toList());

        ChatCompletionsClient client = new ChatCompletionsClientBuilder()
                .endpoint(endpoint)
                .credential(new AzureKeyCredential(key))
                .buildClient();

        List<ChatRequestMessage> messages = new ArrayList<>();
        messages.add(new ChatRequestUserMessage("Veja minhas categorias abaixo:"));
        messages.addAll(
                descricoesCategorias.stream()
                        .map(ChatRequestUserMessage::new)
                        .collect(Collectors.toList())
        );

        messages.add(new ChatRequestUserMessage("Com base nas minhas categorias acima, categorize as descrições a seguir, retornando apenas uma lista simples, com uma categoria por linha, na mesma ordem das descrições."));
        messages.add(new ChatRequestUserMessage("Sem comentários, sem JSON, apenas a lista de categorias."));
        messages.addAll(
                descricoesTransacoes.stream()
                        .map(ChatRequestUserMessage::new)
                        .collect(Collectors.toList())
        );

        ChatCompletionsOptions options = new ChatCompletionsOptions(messages);
        options.setModel(model);

        ChatCompletions result = client.complete(options);
        String resposta = result.getChoices().get(0).getMessage().getContent();

        List<String> categoriasRespondidas = Arrays.stream(resposta.split("\\r?\\n"))
                .map(String::trim)
                .collect(Collectors.toList());

        if (categoriasRespondidas.size() != transacaoList.size()) {
            throw new CategorizarAIException("O número de categorias retornado não corresponde ao número de descrições.");
        }

        for (String categoria : categoriasRespondidas) {
            if (!mapaDescricaoCategoria.containsKey(categoria)) {
                throw new CategorizarAIException("Categoria inválida encontrada: " + categoria);
            }
        }

        // Atribui o ID da categoria à transação
        for (int i = 0; i < transacaoList.size(); i++) {
            Transacao transacao = transacaoList.get(i);
            CategoriaTransacao categoria = mapaDescricaoCategoria.get(categoriasRespondidas.get(i));
            transacao.setIdCategoriaTransacao(categoria.getId());
        }

        return transacaoList;
    }

}
