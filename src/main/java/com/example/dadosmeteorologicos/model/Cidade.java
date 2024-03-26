package com.example.dadosmeteorologicos.model;

import java.util.UUID;

import lombok.Data;

@Data   
public class Cidade {
    private UUID id;
    private String nome;
    private String codigo;
    
    public Cidade(UUID id, String nome, String codigo) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
    }

}
