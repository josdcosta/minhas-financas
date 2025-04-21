package br.com.dominio.minhasfinancas.mapper;

import br.com.dominio.minhasfinancas.api.usuario.request.CadastroUsuarioRequest;
import br.com.dominio.minhasfinancas.api.usuario.request.LoginUsuarioRequest;
import br.com.dominio.minhasfinancas.api.usuario.response.CadastroUsuarioResponse;
import br.com.dominio.minhasfinancas.api.usuario.response.LoginUsuarioResponse;
import br.com.dominio.minhasfinancas.domain.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario fromToUsuario(LoginUsuarioRequest loginUsuarioRequest) {
        return Usuario.builder()
                .cpf(loginUsuarioRequest.getCpf())
                .senha(loginUsuarioRequest.getSenha())
                .build();
    }

    public LoginUsuarioResponse fromToUsuarioResponse(Usuario usuario) {
        return LoginUsuarioResponse.builder()
                .id(usuario.getId())
                .token(usuario.getToken())
                .build();
    }

    public Usuario fromToUsuario(CadastroUsuarioRequest cadastroUsuarioRequest) {
        return Usuario.builder()
                .nome(cadastroUsuarioRequest.getNome())
                .email(cadastroUsuarioRequest.getEmail())
                .cpf(cadastroUsuarioRequest.getCpf())
                .dataNascimento(cadastroUsuarioRequest.getDataNascimento())
                .senha(cadastroUsuarioRequest.getSenha())
                //.roles(cadastroUsuarioRequest.getRoles())
                .build();
    }

    public CadastroUsuarioResponse fromToCadastroResponse(Usuario usuario) {
        return CadastroUsuarioResponse.builder()
                .id(usuario.getId())
                .token(usuario.getToken())
                .build();
    }

}
