package med.voll.api.domain.consultas.validacoes.agendamento;

import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.repository.IValidadorAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamentoClinica implements IValidadorAgendamentoConsulta {
    public void validar(DadosAgendamentoConsulta dados){

        var dataConsulta = dados.data();

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);

        var antesAbertura = dataConsulta.getHour() < 7;

        var depoisEnderrametno = dataConsulta.getHour() > 18;

        if(domingo || antesAbertura || depoisEnderrametno){
            throw new ValidacaoException("Consulta fora do horário de expediente");
        }
    }
}
