package med.voll.api.controller;

import med.voll.api.medico.DadosCadastroMedicos;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @PostMapping
    public void addDoctor(@RequestBody DadosCadastroMedicos dadosCadastroMedicos) {
        System.out.println(dadosCadastroMedicos.endereco().cep());

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