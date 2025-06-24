package med.voll.api.service;

import med.voll.api.domain.consultas.Consulta;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.consultas.DadosCancelamentoConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.repository.IConsultaRepository;
import med.voll.api.repository.IMedicoRespository;
import med.voll.api.repository.IPacienteRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultas {

    @Autowired
    private IConsultaRepository consultaRepository;

    @Autowired
    private IMedicoRespository medicoRespository;

    @Autowired
    private IPacienteRespository pacienteRespository;
    public void agendarConsulta(DadosAgendamentoConsulta dados){

        //antes de tudo isso, preciso ter a implementação das regras de negócio
        if(!pacienteRespository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Id do paciente informação não encontrado");
        }

        if(dados.idMedico() != null && !medicoRespository.existsById(dados.idMedico())){
            throw new ValidacaoException("Id do medico informação não encontrado");
        }


        var paciente = pacienteRespository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);
        var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        consultaRepository.save(consulta);

    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if(dados.idMedico() !=null){
            return medicoRespository.getReferenceById(dados.idMedico());
        }

        if(dados.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatório quando o médico não for escolhido");
        }
        return medicoRespository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());

    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
}
