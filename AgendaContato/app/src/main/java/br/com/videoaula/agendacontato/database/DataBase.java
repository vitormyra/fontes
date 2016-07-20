package br.com.videoaula.agendacontato.database;

/**
 * Created by vitormyra on 17/07/16.
 */

import android.content.Context;
import  android.database.sqlite.*;

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context){
        super(context, "AGENDA", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptSQL.getCreateContato());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
