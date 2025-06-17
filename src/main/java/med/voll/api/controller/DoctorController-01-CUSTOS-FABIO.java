package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.DadosCadastroMedicos;
import med.voll.api.medico.DadosListagemMedicos;
import med.voll.api.medico.Medico;
import med.voll.api.repository.IMedicoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class DoctorController {

    @Autowired
    private IMedicoRespository medicoRespository;

    @PostMapping
    @Transactional
    public void addDoctor(@RequestBody @Valid DadosCadastroMedicos dados) {
        medicoRespository.save(new Medico(dados));
    }

    @GetMapping
    public Page<DadosListagemMedicos> getAllDoctors(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        return medicoRespository.findAll(pageable)
                .map(DadosListagemMedicos::new);
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
 */