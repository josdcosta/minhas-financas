package br.com.dominio.minhasfinancas.api.meta;

import br.com.dominio.minhasfinancas.api.meta.request.AtualizarParcialMetaRequest;
import br.com.dominio.minhasfinancas.api.meta.request.CriarMetaRequest;
import br.com.dominio.minhasfinancas.api.meta.response.BuscarMetasResponse;
import br.com.dominio.minhasfinancas.api.meta.response.CriarMetaResponse;
import br.com.dominio.minhasfinancas.domain.Meta;
import br.com.dominio.minhasfinancas.mapper.MetaMapper;
import br.com.dominio.minhasfinancas.service.MetaService;
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
            @RequestBody CriarMetaRequest metaRequest,
            @AuthenticationPrincipal Jwt jwt){
        String idUsuario = jwt.getSubject();
        Meta meta = metaMapper.fromToMeta(metaRequest);
        meta.setIdUsuario(idUsuario);
        CriarMetaResponse criarMetaResponse = metaMapper.fromToCriarMetaResponse(metaService.salvar(meta));
        return new ResponseEntity<>(criarMetaResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{mes}/{ano}")
    public ResponseEntity<?> listaMetas(
            @PathVariable Integer mes,
            @PathVariable Integer ano,
            @AuthenticationPrincipal Jwt jwt){
        String idUsuario = jwt.getSubject();

        List<Meta> metaList = metaService.BuscarTodos(idUsuario, mes, ano);

        List<BuscarMetasResponse> metasResponseList = metaMapper.fromtoBuscarMetaResponseList(metaList);

        return new ResponseEntity<>(metasResponseList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(
            @PathVariable String id,
            @AuthenticationPrincipal Jwt jwt){
        try {
            metaService.deletar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deletado com sucesso.");
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping
    public ResponseEntity<?> atualizarParcial(
            @RequestBody AtualizarParcialMetaRequest atualizarParcialMetaRequest,
            @AuthenticationPrincipal Jwt jwt
    ){
        Meta meta = metaMapper.fromToMeta(atualizarParcialMetaRequest);
        return new ResponseEntity<>(metaService.atualizarParcial(meta), HttpStatus.OK);
    }
}
