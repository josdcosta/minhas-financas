package br.com.dominio.minhasfinancas.api.categoria;
import br.com.dominio.minhasfinancas.api.categoria.request.AtualizarDescricaoCategoriaTransacaoRequest;
import br.com.dominio.minhasfinancas.api.categoria.request.CriarCategoriaTransacaoRequest;
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
    public ResponseEntity<List<?>> criarTodos(@RequestBody @Valid List<CriarCategoriaTransacaoRequest> criarCategoriaGastoRequestList){

        List<CategoriaTransacao> categoriaTransacaoList = categoriaTransacaoService.criarTodos(categoriaTransacaoMapper.fromToCategoriaTransacaoList(criarCategoriaGastoRequestList));
        return new ResponseEntity<>(categoriaTransacaoMapper.fromToCriarCategoriaTransacaoResponseList(categoriaTransacaoList), HttpStatus.CREATED);
    }

    @GetMapping
    public List<CategoriaTransacao> buscarTodos(){
        return categoriaTransacaoService.buscarTodos();
    }

    @DeleteMapping("{idCategoria}")
    public void deletar(
            @PathVariable String idCategoria){
        categoriaTransacaoService.deletar(idCategoria);
    }

    @PatchMapping
    public CategoriaTransacao atualizarDescricao(
            @RequestBody AtualizarDescricaoCategoriaTransacaoRequest atualizarDescricaoCategoriaTransacaoRequest){
        CategoriaTransacao categoriaTransacao = categoriaTransacaoMapper.
                fromToCategoriaTransacao(atualizarDescricaoCategoriaTransacaoRequest);
        return categoriaTransacaoService.atualizarDescricao(categoriaTransacao);
    }
}
