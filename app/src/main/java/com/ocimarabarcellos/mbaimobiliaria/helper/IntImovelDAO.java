package com.ocimarabarcellos.mbaimobiliaria.helper;

import com.ocimarabarcellos.mbaimobiliaria.Classes.Imovel;

import java.util.List;

public interface IntImovelDAO {

    public boolean salvar(Imovel imovel);
    public boolean atualizar(Imovel imovel);
    public boolean deletar(Imovel imovel);
    public List<Imovel> listar();




}
