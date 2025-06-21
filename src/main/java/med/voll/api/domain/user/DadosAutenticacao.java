package med.voll.api.domain.user;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(
        @NotBlank
        String username,
        @NotBlank
        String password) {
}
