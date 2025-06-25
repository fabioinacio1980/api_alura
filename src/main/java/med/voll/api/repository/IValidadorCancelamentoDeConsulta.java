package med.voll.api.repository;

import med.voll.api.domain.consultas.DadosCancelamentoConsulta;

public interface IValidadorCancelamentoDeConsulta {

    void validar(DadosCancelamentoConsulta dados);
}
