package com.ocimarabarcellos.mbaimobiliaria.Classes;

public class Usuario {
    private int id;
    private String email;
    private String senha;
    private String nome;
    private Boolean conectado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public Boolean getConectado() {
        return conectado;
    }

    public void setConectado(Boolean conectado) {

        this.conectado = conectado;
    }


}
