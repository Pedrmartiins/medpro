package com.medpro.medpro.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.medpro.medpro.model.entity.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    boolean existsByPacienteIdAndDataHoraBetween(Long idPaciente, LocalDateTime start, LocalDateTime end);

    boolean existsByMedicoIdAndDataHora(Long idMedico, LocalDateTime data_hora);

    @Query("SELECT c FROM Consulta c WHERE c.dataHora = :dataHora")
    List<Consulta> findByDataHora(LocalDateTime dataHora);
}
