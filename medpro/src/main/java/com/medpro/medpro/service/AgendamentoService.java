package com.medpro.medpro.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medpro.medpro.model.dto.AgendamentoConsulta;
import com.medpro.medpro.model.entity.Consulta;
import com.medpro.medpro.model.entity.Medico;
import com.medpro.medpro.repository.ConsultaRepository;
import com.medpro.medpro.repository.MedicoRepository;
import com.medpro.medpro.repository.PacienteRepository;

@Service
public class AgendamentoService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public Consulta agendar(AgendamentoConsulta dto) {

        var paciente = pacienteRepository.findById(dto.idPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        if (!paciente.getAtivo()) {
            throw new RuntimeException("Paciente inativo");
        }

        // Regra 1: horário da clínica
        validarHorarioFuncionamento(dto.data_hora());

        // Regra 2: antecedência mínima de 30 minutos
        if (dto.data_hora().isBefore(LocalDateTime.now().plusMinutes(30))) {
            throw new RuntimeException("Consulta deve ser agendada com 30 minutos de antecedência");
        }

        // Regra 3: paciente já possui consulta no mesmo dia
        LocalDate data = dto.dataHora().toLocalDate();
        LocalDateTime inicioDia = data.atStartOfDay();
        LocalDateTime fimDia = data.atTime(23, 59);
        
        if (consultaRepository.existsByPacienteIdAndDataHoraBetween(
                dto.idPaciente(), inicioDia, fimDia)) {
            throw new RuntimeException("Paciente já possui consulta nesse dia");
        }

        Medico medicoEscolhido;

        // MÉDICO OPCIONAL
        if (dto.idMedico() == null) {
            medicoEscolhido = escolherMedicoAutomaticamente(dto.dataHora());
        } else {
            medicoEscolhido = medicoRepository.findById(dto.idMedico())
                    .orElseThrow(() -> new RuntimeException("Médico não encontrado"));

            if (!medicoEscolhido.getAtivo()) {
                throw new RuntimeException("Médico inativo");
            }

            // Regra 4: médico já tem consulta na hora
            if (consultaRepository.existsByMedicoIdAndDataHora(medicoEscolhido.getId(), dto.dataHora())) {
                throw new RuntimeException("Médico já possui consulta nesse horário");
            }
        }

        // Criar consulta
        var novaConsulta = new Consulta();
        novaConsulta.setPaciente(paciente);
        novaConsulta.setMedico(medicoEscolhido);
        novaConsulta.setDataHora(dto.dataHora());

        return consultaRepository.save(novaConsulta);
    }

    private void validarHorarioFuncionamento(LocalDateTime dataHora) {
        var diaSemana = dataHora.getDayOfWeek();
        var hora = dataHora.getHour();

        if (diaSemana == DayOfWeek.SUNDAY)
            throw new RuntimeException("A clínica não funciona aos domingos");

        if (hora < 7 || hora >= 19)
            throw new RuntimeException("Horário fora do funcionamento (07:00 às 19:00)");
    }

    private Medico escolherMedicoAutomaticamente(LocalDateTime dataHora) {
        List<Medico> medicosDisponiveis = medicoRepository.buscarMedicosDisponiveis(dataHora);

        if (medicosDisponiveis.isEmpty()) {
            throw new RuntimeException("Nenhum médico disponível para esse horário");
        }

        Collections.shuffle(medicosDisponiveis); // escolha aleatória
        return medicosDisponiveis.get(0);
    }
}
