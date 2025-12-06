package com.medpro.medpro.model.dto;

import java.time.LocalDateTime;

public record AgendamentoConsulta(
        Long idPaciente,
        Long idMedico,       
        LocalDateTime dataHora) {
     
}
