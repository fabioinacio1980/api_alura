package med.voll.api.medico;

import med.voll.api.medico.Medico;

public record DadosListagemMedicos(
        Long id,
        String nome,
        String email,
        String crm) {

    public DadosListagemMedicos(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm());
    }
}
