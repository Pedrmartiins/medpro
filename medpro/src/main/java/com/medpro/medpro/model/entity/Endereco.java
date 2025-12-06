package com.medpro.medpro.model.entity;

import com.medpro.medpro.model.dto.DadosEndereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Endereco {

    private String logradouro;
    private String bairro;
    private String cep;
    private String cidade;
    private String uf;
    private String numero;
    private String complemento;

public Endereco(DadosEndereco endereco) {
    this.logradouro = endereco.logradouro();
    this.bairro = endereco.bairro();
    this.cep = endereco.cep();
    this.cidade = endereco.cidade();
    this.uf = endereco.uf();
    this.numero = endereco.numero();
    this.complemento = endereco.complemento();


}
public void atualizarInformacoes(DadosEndereco dados) {
    if(dados.logradouro() != null){
        if(dados.logradouro().isBlank())
        throw new IllegalArgumentException("logradouro não pode ser nulo");
        else
            this.logradouro = dados.logradouro();
    }
     if(dados.bairro() != null){
        if(dados.bairro().isBlank())
        throw new IllegalArgumentException("bairro não pode ser nulo");
        else
            this.bairro = dados.bairro();
    }
     if(dados.cep() != null){
        if(dados.cep().isBlank())
        throw new IllegalArgumentException("cep não pode ser nulo");
        else
            this.cep = dados.cep();
    }
     if(dados.cidade() != null){
        if(dados.cidade().isBlank())
        throw new IllegalArgumentException("cidade não pode ser nulo");
        else
            this.cidade = dados.cidade();
    }
     if(dados.uf() != null){
        if(dados.uf().isBlank())
        throw new IllegalArgumentException("uf não pode ser nulo");
        else
            this.uf = dados.uf();
    }
     if(dados.numero() != null){
        if(dados.numero().isBlank())
        throw new IllegalArgumentException("numero não pode ser nulo");
        else
            this.numero = dados.numero();
    }
     if(dados.complemento() != null){
        if(dados.complemento().isBlank())
        throw new IllegalArgumentException("complemento não pode ser nulo");
        else
            this.complemento = dados.complemento();
    }

}

}
