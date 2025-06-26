package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import med.voll.api.repository.IPacienteRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private IPacienteRespository pacienteRespository;

    @PostMapping
    @Transactional
    public ResponseEntity addPaciente(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
        var paciente = new Paciente(dados);
        pacienteRespository.save(paciente);

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        var dto = new DadosDetalhamentoPaciente(paciente);
        return ResponseEntity.created(uri).body(dto);
    }

//    @GetMapping
//    public Page<DadosListagemPaciente> getAllDoctors(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
//        return medicoRespository.findAll(pageable)
//                .map(DadosListagemPaciente::new);
//    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> getAllDoctors(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        var page =  pacienteRespository.findAllByAtivoTrue(pageable)
                .map(DadosListagemPaciente::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        var paciente = pacienteRespository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosAtualizacaoPaciente(paciente));
    }

//    @DeleteMapping("/{id}")
//    @Transactional
//    public void delete(@PathVariable Long id) {
//        medicoRespository.deleteById(id);
//    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        var paciente = pacienteRespository.getReferenceById(id);
        paciente.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity detalhar(@PathVariable Long id) {
        var paciente = pacienteRespository.getReferenceById(id);

        var dto = new DadosDetalhamentoPaciente(paciente);

        return ResponseEntity.ok(dto);
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