package com.sala.facil.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "sala")
public class Sala implements Serializable {

    @Id
    @Column(name = "id_sala")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "departamento")
    private String departamento;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "status")
    private int status; // CONFERIR SE É BOOLEAN

    public Sala() {
    }

    public Sala(long id_sala, String nome, String departamento, String descricao, int status) {
        this.id = id_sala;
        this.nome = nome;
        this.departamento = departamento;
        this.descricao = descricao;
        this.status = status;
    }

    public long getId_sala() {
        return id;
    }

    public void setId_sala(long id_sala) {
        this.id = id_sala;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
