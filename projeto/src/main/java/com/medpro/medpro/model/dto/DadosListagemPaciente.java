package com.medpro.medpro.model.dto;

import com.medpro.medpro.model.entity.Paciente;

public record DadosListagemPaciente(String nome, String email, String telefone, String cpf) {

    public DadosListagemPaciente(Paciente paciente){
    this(paciente.getNome(), paciente.getEmail(),paciente.getTelefone(), paciente.getCpf());
    
}
}
