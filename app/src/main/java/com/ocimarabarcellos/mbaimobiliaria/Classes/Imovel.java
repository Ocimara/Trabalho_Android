package com.ocimarabarcellos.mbaimobiliaria.Classes;

import java.io.Serializable;

public class Imovel implements Serializable {
    private long id;
    private String dsImovel;
    private String dsEndereco;
    private String dsCidade;
    private String dsUF;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDsImovel() {
        return dsImovel;
    }

    public void setDsImovel(String dsImovel) {
        this.dsImovel = dsImovel;
    }

    public String getDsEndereco() {
        return dsEndereco;
    }

    public void setDsEndereco(String dsEndereco) {
        this.dsEndereco = dsEndereco;
    }

    public String getDsCidade() {
        return dsCidade;
    }

    public void setDsCidade(String dsCidade) {
        this.dsCidade = dsCidade;
    }

    public String getDsUF() {
        return dsUF;
    }

    public void setDsUF(String dsUF) {
        this.dsUF = dsUF;
    }






}
