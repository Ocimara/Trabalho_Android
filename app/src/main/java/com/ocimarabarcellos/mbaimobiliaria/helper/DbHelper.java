package com.ocimarabarcellos.mbaimobiliaria.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_IMOVEIS";
    public static String TB_IMOVEIS = "tbImoveis";




    public DbHelper(Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TB_IMOVEIS +
                        " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " dsImovel TEXT NOT NULL, " +
                        " dsEndereco VARCHAR(150) NOT NULL, " +
                        " dsCidade VARCHAR(80) NOT NULL, " +
                        " dsUF VARCHAR(2) NOT NULL ); ";
        try {
            db.execSQL(sql);

            Log.i( "Info DB", "Sucesso na criaçao da table");

        } catch (Exception e) {
            Log.i( "Info DB", "Erro ao criar a table" + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
