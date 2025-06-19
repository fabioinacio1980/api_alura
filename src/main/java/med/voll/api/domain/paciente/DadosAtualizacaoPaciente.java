package med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco
        ) {


        public DadosAtualizacaoPaciente(Paciente paciente) {
                this(paciente.getId(), paciente.getNome(), paciente.getTelefone(), new DadosEndereco(
                                paciente.getEndereco().getLogradouro(),
                                paciente.getEndereco().getBairro(),
                                paciente.getEndereco().getCep(),
                                paciente.getEndereco().getCidade(),
                                paciente.getEndereco().getUf(),
                                paciente.getEndereco().getNumero(),
                                paciente.getEndereco().getComplemento()
                        )
                );
        }
}
