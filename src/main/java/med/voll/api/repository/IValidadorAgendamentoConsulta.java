package med.voll.api.repository;

import med.voll.api.domain.consultas.DadosAgendamentoConsulta;

public interface IValidadorAgendamentoConsulta {

    void validar(DadosAgendamentoConsulta dados);
}
