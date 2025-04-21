package br.com.dominio.minhasfinancas.api.transacao;

import br.com.dominio.minhasfinancas.api.transacao.request.CriarTransacaoRequest;
import br.com.dominio.minhasfinancas.api.transacao.response.BuscarTransacaoResponse;
import br.com.dominio.minhasfinancas.domain.Transacao;
import br.com.dominio.minhasfinancas.exception.CategorizarAIException;
import br.com.dominio.minhasfinancas.exception.SalvarListaTransacaoException;
import br.com.dominio.minhasfinancas.exception.TransacoesNaoEncontradasException;
import br.com.dominio.minhasfinancas.mapper.TransacaoMapper;
import br.com.dominio.minhasfinancas.service.CategorizarAIService;
import br.com.dominio.minhasfinancas.service.TransacaoService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private static final Logger log = LoggerFactory.getLogger(TransacaoController.class);

    @Autowired
    private CategorizarAIService categorizarAIService;

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private TransacaoMapper transacaoMapper;

    @PostMapping
    public ResponseEntity<?> salvarTransacoes(
            @RequestBody List<CriarTransacaoRequest> criarTransacaoRequestList,
            @AuthenticationPrincipal Jwt jwt) {

        List<Transacao> transacaoList = transacaoMapper.fromToTransacaoList(criarTransacaoRequestList);
        String idUsuario = jwt.getSubject();
        transacaoList.forEach(transacao -> transacao.setIdUsuario(idUsuario));

        try {
            transacaoList = categorizarAIService.categorizar(transacaoList);
            transacaoService.salvarTodos(transacaoList);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Criado com sucesso");
        } catch (SalvarListaTransacaoException | CategorizarAIException e) {
            log.error("Erro ao salvar lista de transações", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao salvar transações: " + e.getMessage());
        } catch (Exception e) {
            log.error("Erro inesperado ao salvar transações", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao salvar transações.");
        }
    }

    @GetMapping
    public ResponseEntity<?> listarTransacoes(
            @AuthenticationPrincipal Jwt jwt){

        String idUsuario = jwt.getSubject();
        try{
            List<Transacao> transacaoList = transacaoService.buscarTodos(idUsuario);
            List<BuscarTransacaoResponse> transacaoResponseList = transacaoMapper
                    .fromToBuscarTransacaoResponseList(transacaoList);
            return new ResponseEntity<>(transacaoResponseList, HttpStatus.OK);
        } catch (TransacoesNaoEncontradasException e){
            log.error("Erro ao buscar transações", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao buscar transações: " + e.getMessage());
        } catch (Exception e){
            log.error("Erro inesperado ao salvar transações", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao salvar transações.");
        }

    }
}
