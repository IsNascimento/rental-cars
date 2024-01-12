package org.example.persistence.model;

import java.util.List;

public class Car {

    private Long id;
    private String modelo;
    private String ano;
    private Integer qtdPassageiros;
    private Integer km;
    private String fabricante;
    private Long vlrDiaria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public Integer getQtdPassageiros() {
        return qtdPassageiros;
    }

    public void setQtdPassageiros(Integer qtdPassageiros) {
        this.qtdPassageiros = qtdPassageiros;
    }

    public Integer getKm() {
        return km;
    }

    public void setKm(Integer km) {
        this.km = km;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Long getVlrDiaria() {
        return vlrDiaria;
    }

    public void setVlrDiaria(Long vlrDiaria) {
        this.vlrDiaria = vlrDiaria;
    }
}
