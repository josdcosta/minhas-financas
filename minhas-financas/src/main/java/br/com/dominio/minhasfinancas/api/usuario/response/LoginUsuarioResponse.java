package br.com.dominio.minhasfinancas.api.usuario.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginUsuarioResponse {
    private String id;
    private String token;
}
