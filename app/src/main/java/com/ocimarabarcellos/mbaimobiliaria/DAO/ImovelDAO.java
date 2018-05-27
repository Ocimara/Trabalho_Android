package com.ocimarabarcellos.mbaimobiliaria.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ocimarabarcellos.mbaimobiliaria.Classes.Imovel;
import com.ocimarabarcellos.mbaimobiliaria.helper.DbHelper;
import com.ocimarabarcellos.mbaimobiliaria.helper.IntImovelDAO;

import java.util.ArrayList;
import java.util.List;

public class ImovelDAO implements IntImovelDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase ler;

    public ImovelDAO(Context context) {
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        ler = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Imovel imovel) {
        try {

            ContentValues cv = new ContentValues();

            cv.put("dsImovel", imovel.getDsImovel());
            cv.put("dsEndereco", imovel.getDsEndereco());
            cv.put("dsCidade", imovel.getDsCidade());
            cv.put("dsUF", imovel.getDsUF());

            escreve.insert(DbHelper.TB_IMOVEIS, null, cv);
            Log.i("INFO", "IMOVEL SALVO COM SUCESSO!");

        } catch (Exception e ) {

            Log.i("INFO", "ERRO AO SALVAR O IMOVEL");
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Imovel imovel) {
        try {

            ContentValues cv = new ContentValues();
            cv.put("dsImovel", imovel.getDsImovel());
            cv.put("dsEndereco", imovel.getDsEndereco());
            cv.put("dsCidade", imovel.getDsCidade());
            cv.put("dsUF", imovel.getDsUF());

            String[] args = {String.valueOf(imovel.getId())};


            escreve.update(DbHelper.TB_IMOVEIS, cv, "id=?", args);
            Log.i("INFO", "IMOVEL ATUALIZADO COM SUCESSO!");

        } catch (Exception e ) {

            Log.i("INFO", "ERRO AO ATUALIZAR O IMOVEL");
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar(Imovel imovel) {
        try {

            String[] args = {String.valueOf(imovel.getId())};

            escreve.delete(DbHelper.TB_IMOVEIS, "id=?", args);
            Log.i("INFO", "IMOVEL REMOVIDO COM SUCESSO!");

        } catch (Exception e ) {

            Log.i("INFO", "ERRO AO REMOVER O IMOVEL");
            return false;
        }
        return true;
    }

    @Override
    public List<Imovel> listar() {

        List<Imovel> imoveis = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TB_IMOVEIS + " ;";
        Cursor c = ler.rawQuery(sql, null);

        while (c.moveToNext()) {
            Imovel imov =  new Imovel();

            Long id = c.getLong(c.getColumnIndex("id"));
            String dsImov = c.getString(c.getColumnIndex("dsImovel"));
            String dsEnd = c.getString(c.getColumnIndex("dsEndereco"));
            String dsCid = c.getString(c.getColumnIndex("dsCidade"));
            String dsUF = c.getString(c.getColumnIndex("dsUF"));

            imov.setId(id);
            imov.setDsImovel(dsImov);
            imov.setDsEndereco(dsEnd);
            imov.setDsCidade(dsCid);
            imov.setDsUF(dsUF);

            imoveis.add(imov);
        }

        return imoveis;
    }
}
