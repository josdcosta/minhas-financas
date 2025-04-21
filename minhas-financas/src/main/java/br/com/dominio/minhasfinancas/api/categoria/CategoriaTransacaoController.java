package br.com.dominio.minhasfinancas.api.categoria;
import br.com.dominio.minhasfinancas.api.categoria.request.AtualizarDescricaoCategoriaTransacaoRequest;
import br.com.dominio.minhasfinancas.api.categoria.request.CriarCategoriaTransacaoRequest;
import br.com.dominio.minhasfinancas.api.categoria.response.CriarCategoriaTransacaoResponse;
import br.com.dominio.minhasfinancas.domain.CategoriaTransacao;
import br.com.dominio.minhasfinancas.mapper.CategoriaTransacaoMapper;
import br.com.dominio.minhasfinancas.service.CategoriaTransacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria-transacao")
public class CategoriaTransacaoController {

    @Autowired
    CategoriaTransacaoMapper categoriaTransacaoMapper;

    @Autowired
    CategoriaTransacaoService categoriaTransacaoService;

    @PostMapping
    public ResponseEntity<?> criar(CriarCategoriaTransacaoRequest criarCategoriaTransacaoRequest){
        CategoriaTransacao categoriaTransacao = categoriaTransacaoMapper.fromToCategoriaTransacao(criarCategoriaTransacaoRequest);
        CategoriaTransacao categoriaTransacaoSalva = categoriaTransacaoService.criar(categoriaTransacao);
        CriarCategoriaTransacaoResponse categoriaTransacaoResponse = categoriaTransacaoMapper.fromToCriarCategoriaTransacaoResponse(categoriaTransacaoSalva);
        return new ResponseEntity<>(categoriaTransacaoResponse, HttpStatus.CREATED);
    }

    @PostMapping("/lote")
    public ResponseEntity<List<?>> criarTodos(
            @RequestBody @Valid List<CriarCategoriaTransacaoRequest> criarCategoriaGastoRequestList
    ) {
        List<CategoriaTransacao> categorias = categoriaTransacaoMapper
                .fromToCategoriaTransacaoList(criarCategoriaGastoRequestList);

        List<CategoriaTransacao> categoriasCriadas = categoriaTransacaoService
                .criarTodos(categorias);

        List<CriarCategoriaTransacaoResponse> categoriasResponse = categoriaTransacaoMapper
                .fromToCriarCategoriaTransacaoResponseList(categoriasCriadas);

        return new ResponseEntity<>(categoriasResponse, HttpStatus.CREATED);
    }


    @GetMapping
    public List<CategoriaTransacao> buscarTodos(){
        return categoriaTransacaoService.buscarTodos();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(
            @PathVariable String id){
        try{
            categoriaTransacaoService.deletar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Excluído com sucesso!");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado.");
        }
    }

    @PatchMapping
    public CategoriaTransacao atualizarDescricao(
            @RequestBody @Valid AtualizarDescricaoCategoriaTransacaoRequest request
    ) {
        CategoriaTransacao categoriaTransacao = categoriaTransacaoMapper
                .fromToCategoriaTransacao(request);

        return categoriaTransacaoService
                .atualizarDescricao(categoriaTransacao);
    }

}
