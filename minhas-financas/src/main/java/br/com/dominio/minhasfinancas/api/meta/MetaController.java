package br.com.dominio.minhasfinancas.api.meta;

import br.com.dominio.minhasfinancas.api.meta.request.AtualizarParcialMetaRequest;
import br.com.dominio.minhasfinancas.api.meta.request.CriarMetaRequest;
import br.com.dominio.minhasfinancas.api.meta.response.BuscarMetaResponse;
import br.com.dominio.minhasfinancas.api.meta.response.CriarMetaResponse;
import br.com.dominio.minhasfinancas.domain.Meta;
import br.com.dominio.minhasfinancas.mapper.MetaMapper;
import br.com.dominio.minhasfinancas.service.MetaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.junit.platform.commons.function.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meta")
public class MetaController {

    @Autowired
    MetaMapper metaMapper;

    @Autowired
    MetaService metaService;

    @PostMapping
    public ResponseEntity<?> criar(
            @RequestBody @Valid CriarMetaRequest metaRequest,
            @AuthenticationPrincipal Jwt jwt){
        String idUsuario = jwt.getSubject();
        Meta meta = metaMapper.fromToMeta(metaRequest);
        meta.setIdUsuario(idUsuario);
        try {
            Meta metaSalva = metaService.salvar(meta);
            CriarMetaResponse metaResponse = metaMapper.fromToCriarMetaResponse(metaSalva);
            return new ResponseEntity<>(metaResponse, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Meta já existente");
        }
    }

    @PostMapping("/lote")
    public ResponseEntity<?> criarMultiplas(
            @RequestBody @Valid List<CriarMetaRequest> criarMetaRequestListList,
            @AuthenticationPrincipal Jwt jwt) {
        String idUsuario = jwt.getSubject();
        List<Meta> metas = metaMapper.fromToMetaList(criarMetaRequestListList);
        metas.forEach(m -> m.setIdUsuario(idUsuario));
        try {
            List<Meta> metasCriadas = metaService.salvarTodos(metas);
            List<CriarMetaResponse> metasResponse = metaMapper.fromToCriarMetaResponseList(metasCriadas);
            return new ResponseEntity<>(metasResponse, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alguma das metas enviadas já existe no banco");
        }
    }

    @GetMapping("/{mes}/{ano}")
    public ResponseEntity<?> listaMetas(
            @PathVariable @NotNull Integer mes,
            @PathVariable @NotNull Integer ano,
            @AuthenticationPrincipal Jwt jwt){
        String idUsuario = jwt.getSubject();
        List<Meta> metasBuscadas = metaService.buscarTodosMesAno(idUsuario, mes, ano);
        List<BuscarMetaResponse> metasResponse = metaMapper.fromtoBuscarMetaResponseList(metasBuscadas);
        return new ResponseEntity<>(metasResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(
            @PathVariable @NotNull String id,
            @AuthenticationPrincipal Jwt jwt){
        try {
            metaService.deletar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deletado com sucesso.");
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping
    public ResponseEntity<?> atualizarParcial(
            @RequestBody @Valid AtualizarParcialMetaRequest atualizarParcialMetaRequest,
            @AuthenticationPrincipal Jwt jwt
    ){
        Meta metaAtualizada = metaMapper.fromToMeta(atualizarParcialMetaRequest);
        return new ResponseEntity<>(metaService.atualizarParcial(metaAtualizada), HttpStatus.OK);
    }
}
