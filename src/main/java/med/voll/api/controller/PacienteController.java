package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.paciente.DadosAtualizacaoPaciente;
import med.voll.api.paciente.DadosCadastroPaciente;
import med.voll.api.paciente.DadosListagemPaciente;
import med.voll.api.paciente.Paciente;
import med.voll.api.repository.IPacienteRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private IPacienteRespository pacienteRespository;

    @PostMapping
    @Transactional
    public void addDoctor(@RequestBody @Valid DadosCadastroPaciente dados) {
        pacienteRespository.save(new Paciente(dados));
    }

//    @GetMapping
//    public Page<DadosListagemPaciente> getAllDoctors(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
//        return medicoRespository.findAll(pageable)
//                .map(DadosListagemPaciente::new);
//    }

    @GetMapping
    public Page<DadosListagemPaciente> getAllPacientes(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        return pacienteRespository.findAllByAtivoTrue(pageable)
                .map(DadosListagemPaciente::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        var paciente = pacienteRespository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
    }

//    @DeleteMapping("/{id}")
//    @Transactional
//    public void delete(@PathVariable Long id) {
//        medicoRespository.deleteById(id);
//    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable Long id) {
        var paciente = pacienteRespository.getReferenceById(id);
        paciente.excluir();
    }
}


/*
{
"nome": "Rodrigo Ferreira",
"email": "rodrigo.ferreira@voll.med",
"crm": "123456",
"especialidade": "ortopedia",
"endereco": {
    "logradouro": "rua 1",
    "bairro": "bairro",
    "cep": "12345678",
    "cidade": "Brasilia",
    "uf": "DF",
    "numero": "1",
    "complemento": "complemento"
    }
}

{
"nome": "Fábio Cristhiano Inacio",
"email": "fabio.inacio@voll.med",
"telefone":"44991188587",
"crm": "10105",
"especialidade": "CARDIOLOGIA",
"endereco": {
    "logradouro": "Rua Marfim",
    "bairro": "Parque dos Ipes",
    "cep": "85440000",
    "cidade": "Ubirata",
    "uf": "Pr",
    "numero": "158",
    "complemento": "complemento"
    }
}
 */