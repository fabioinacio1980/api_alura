package med.voll.api.domain.consultas.validacoes.agendamento;

import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.repository.IConsultaRepository;
import med.voll.api.repository.IValidadorAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements IValidadorAgendamentoConsulta {

    @Autowired
    private IConsultaRepository consultaRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var medicoPossuiOutraConsultaNoMesmoHorario = consultaRepository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(dados));

        if(medicoPossuiOutraConsultaNoMesmoHorario){
            throw new ValidacaoException("Médico já possui outra consulta agendada no mesmo horário");
        }
    }
}
