package med.voll.api.domain.consultas.validacoes.agendamento;

import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.repository.IPacienteRespository;
import med.voll.api.repository.IValidadorAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements IValidadorAgendamentoConsulta {

    @Autowired
    private IPacienteRespository pacienteRespository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var pacienteEstaAtivo = pacienteRespository.findAtivoById(dados.idPaciente());
        if(!pacienteEstaAtivo){
            throw new ValidacaoException("Consulta não pode ser agendada com o paciente.");
        }
    }
}
