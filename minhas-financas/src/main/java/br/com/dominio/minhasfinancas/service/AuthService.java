package br.com.dominio.minhasfinancas.service;

import br.com.dominio.minhasfinancas.api.usuario.response.LoginUsuarioResponse;
import br.com.dominio.minhasfinancas.domain.Usuario;
import br.com.dominio.minhasfinancas.repository.UsuarioRepository;
import br.com.dominio.minhasfinancas.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario criar(Usuario usuario){
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        String token = jwtUtil.generateToken(usuario.getId(), usuario.getRoles());
        usuario.setToken(token);
        try{
           return usuarioRepository.save(usuario);
        } catch(Exception e){
            throw new RuntimeException("Não foi possível criar a conta de usuário");
        }
    }

    public Usuario logar(Usuario request){
        Usuario usuario = usuarioRepository.findByCpf(request.getCpf());

        if (passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            //String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRoles());
            String token = jwtUtil.generateToken(usuario.getId(), usuario.getRoles());
            usuario.setToken(token);
            return usuario;
        } else {
            throw new RuntimeException("Credenciais inválidas");
        }
    }

}
