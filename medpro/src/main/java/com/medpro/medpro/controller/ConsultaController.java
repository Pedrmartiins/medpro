package com.medpro.medpro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medpro.medpro.model.dto.AgendamentoConsulta;
import com.medpro.medpro.service.AgendamentoService;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping("/agendar")
    public ResponseEntity<?> agendar(@RequestBody AgendamentoConsulta dto) {
        var consulta = agendamentoService.agendar(dto);
        return ResponseEntity.ok(consulta);
    }
}
