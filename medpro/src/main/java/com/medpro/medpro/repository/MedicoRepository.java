package com.medpro.medpro.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.medpro.medpro.model.entity.Medico;

public interface MedicoRepository  extends JpaRepository<Medico, Long> {

    @Query("""
        SELECT m FROM Medico m 
        WHERE m.ativo = true 
        AND m.id NOT IN (
            SELECT c.medico.id FROM Consulta c WHERE c.dataHora = :dataHora
        )
    """)
    List<Medico> buscarMedicosDisponiveis(LocalDateTime dataHora);

    Page<Medico> findAllByAtivoTrue(Pageable paginacao);
    
}
