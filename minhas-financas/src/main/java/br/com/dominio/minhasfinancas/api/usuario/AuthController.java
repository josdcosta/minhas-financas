package br.com.dominio.minhasfinancas.api.usuario;

import br.com.dominio.minhasfinancas.api.usuario.request.CadastroUsuarioRequest;
import br.com.dominio.minhasfinancas.api.usuario.request.LoginUsuarioRequest;
import br.com.dominio.minhasfinancas.api.usuario.response.CadastroUsuarioResponse;
import br.com.dominio.minhasfinancas.api.usuario.response.LoginUsuarioResponse;
import br.com.dominio.minhasfinancas.domain.Usuario;
import br.com.dominio.minhasfinancas.mapper.UsuarioMapper;
import br.com.dominio.minhasfinancas.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioMapper usuarioMapper;


    @Autowired
    private AuthService authService;

    @Value("${api.key.interno}")
    private String apiKeyInterno;

    @PostMapping("/cadastrar")
    public CadastroUsuarioResponse cadastrarUsuario(
            @RequestBody @Valid CadastroUsuarioRequest cadastroUsuarioRequest,
            @RequestHeader(value = "x-api-key", required = false) String apiKey){

        Usuario usuario = usuarioMapper.fromToUsuario(cadastroUsuarioRequest);

        List<String> roles = new ArrayList<>();
        if (apiKeyInterno.equals(apiKey)) {
            roles.add("ADMIN");
        } else {
            roles.add("USER");
        }
        usuario.setRoles(roles);

        Usuario usuarioSalvo = authService.criar(usuario);

        return usuarioMapper.fromToCadastroResponse(usuarioSalvo);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Valid LoginUsuarioRequest loginUsuarioRequest){

       Usuario usuario =  usuarioMapper.fromToUsuario(loginUsuarioRequest);

        try {
            LoginUsuarioResponse loginUsuarioResponse = usuarioMapper.fromToUsuarioResponse(
                    authService.logar(usuario));
            return new ResponseEntity<>(loginUsuarioResponse,HttpStatus.OK);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
