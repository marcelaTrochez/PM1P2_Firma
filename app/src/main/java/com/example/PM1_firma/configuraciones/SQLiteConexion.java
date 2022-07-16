package com.example.PM1_firma.configuraciones;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteConexion extends SQLiteOpenHelper{

    public SQLiteConexion(Context context, String dbname, SQLiteDatabase.CursorFactory factory, int version){
        super(context,dbname,factory,version);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(TablaImagenes.CreateTableImagenes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(TablaImagenes.DropTableImagenes);
            onCreate(db);
    }


}
