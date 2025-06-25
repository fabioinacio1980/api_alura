package med.voll.api.domain.consultas.validacoes.agendamento;

import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.repository.IMedicoRespository;
import med.voll.api.repository.IValidadorAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements IValidadorAgendamentoConsulta {

    @Autowired
    private IMedicoRespository medicoRespository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        //escolha do médico opcional
        if(dados.idMedico() == null){
            return;
        }
        var medicoEstaAtivo = medicoRespository.findAtivoById(dados.idMedico());
        if(!medicoEstaAtivo){
            throw new ValidacaoException("Consulta não pode ser agendada com o médico.");
        }
    }
}
