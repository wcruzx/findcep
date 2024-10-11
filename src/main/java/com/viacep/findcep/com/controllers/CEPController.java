package com.viacep.findcep.com.controllers;

import com.viacep.findcep.com.dtos.CEPRequest;
import com.viacep.findcep.com.dtos.CEPResponse;
import com.viacep.findcep.com.models.CEP;
import com.viacep.findcep.com.services.CEPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class CEPController {

    @Autowired
    private CEPService cepService;

    @PostMapping("/consulta-endereco")
    public ResponseEntity<?> consultarEndereco(@RequestBody CEPRequest request) {
        CEP endereco = cepService.consultarCEP(request.getCep());
        if (endereco == null || endereco.getCep() == null) {
            return ResponseEntity.status(404).body("CEP não encontrado");
        }

        double frete = cepService.calcularFrete(endereco.getUf());

        CEPResponse response = new CEPResponse(endereco, frete);
        return ResponseEntity.ok(response);
    }
}

