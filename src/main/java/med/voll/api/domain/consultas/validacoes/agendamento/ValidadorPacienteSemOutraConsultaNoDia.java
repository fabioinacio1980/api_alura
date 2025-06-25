package med.voll.api.domain.consultas.validacoes.agendamento;

import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.repository.IConsultaRepository;
import med.voll.api.repository.IValidadorAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements IValidadorAgendamentoConsulta {

    @Autowired
    private IConsultaRepository consultaRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);
        var pacientePossuiOutraConsultaNoDia = consultaRepository.existisByPacienteIdAndDataBetween(dados.idPaciente());
        if(pacientePossuiOutraConsultaNoDia){
            throw new ValidacaoException("Paciente já possui outra consulta agendada no dia");
        }
    }
}
