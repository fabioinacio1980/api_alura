package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import med.voll.api.repository.IMedicoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class DoctorController {

    @Autowired
    private IMedicoRespository medicoRespository;

    @PostMapping
    @Transactional
    public ResponseEntity addDoctor(@RequestBody @Valid DadosCadastroMedicos dados, UriComponentsBuilder uriBuilder) {
        var medico = new Medico(dados);
        medicoRespository.save(medico);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        var dto = new DadosDetalhamentoMedico(medico);
        return ResponseEntity.created(uri).body(dto);
    }

//    @GetMapping
//    public Page<DadosListagemPaciente> getAllDoctors(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
//        return medicoRespository.findAll(pageable)
//                .map(DadosListagemPaciente::new);
//    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedicos>> getAllDoctors(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        var page =  medicoRespository.findAllByAtivoTrue(pageable)
                    .map(DadosListagemMedicos::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = medicoRespository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

//    @DeleteMapping("/{id}")
//    @Transactional
//    public void delete(@PathVariable Long id) {
//        medicoRespository.deleteById(id);
//    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        var medico = medicoRespository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = medicoRespository.getReferenceById(id);

        var dto = new DadosDetalhamentoMedico(medico);

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