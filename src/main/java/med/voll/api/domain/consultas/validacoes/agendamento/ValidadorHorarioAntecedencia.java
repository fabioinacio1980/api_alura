package med.voll.api.domain.consultas.validacoes.agendamento;

import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.repository.IValidadorAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;

@Component
public class ValidadorHorarioAntecedencia implements IValidadorAgendamentoConsulta {
    public void validar(DadosAgendamentoConsulta dados) {

        var dataConsulta = dados.data();

        var agora = LocalDate.now();

        var diferencaEmMinutos = Duration.between(dataConsulta, agora).toMinutes();

        if (diferencaEmMinutos < 30) {
            throw new ValidacaoException("Consulta deve ser agendada com antecedencia de 30 minutos");
        }
    }
}
