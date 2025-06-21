package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.user.DadosAutenticacao;
import med.voll.api.domain.user.User;
import med.voll.api.repository.IUserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUserRespository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody @Valid DadosAutenticacao dados) {
        var user = new User();
        user.setUsername(dados.username());
        user.setPassword(passwordEncoder.encode(dados.password()));

        repository.save(user);

        return ResponseEntity.ok().build();
    }
}