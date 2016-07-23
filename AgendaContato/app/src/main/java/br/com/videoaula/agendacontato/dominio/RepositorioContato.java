package br.com.videoaula.agendacontato.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.widget.*;

import org.apache.http.conn.ConnectTimeoutException;

import java.util.Date;

import br.com.videoaula.agendacontato.R;
import br.com.videoaula.agendacontato.dominio.entidades.Contato;

/**
 * Created by vitormyra on 17/07/16.
 */
public class RepositorioContato {


    private SQLiteDatabase conn;
    public RepositorioContato(SQLiteDatabase conn){
        this.conn = conn;

    }

    public void inserir(Contato contato){
        ContentValues values = new ContentValues();
        values.put(contato.NOME, contato.getNome());
        values.put(contato.TELEFONE, contato.getTelefone());
        values.put(contato.TIPOTELEFONE, contato.getTipoTelefone());
        values.put(contato.EMAIL, contato.getEmail());
        values.put(contato.TIPOEMAIL, contato.getTipoEmail());
        values.put(contato.ENDERECO, contato.getEndereco());
        values.put(contato.TIPOENDERECO, contato.getTipoEndereco());
        values.put(contato.DATASESPECIAIS, contato.getDatasEspeciais().getTime());
        values.put(contato.TIPODATASESPECIAIS, contato.getTipoDatasEspeciais());
        values.put(contato.GRUPOS, contato.getGrupos());

        conn.insertOrThrow("CONTATO", null, values);
    }


    public ArrayAdapter<Contato>buscaContatos(Context context){

        ArrayAdapter<Contato> adpContatos = new ArrayAdapter<Contato>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = conn.query("CONTATO", null, null,null, null, null, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                Contato contato = new Contato();
                contato.setId(cursor.getLong(cursor.getColumnIndex(contato.ID)));
                contato.setId(cursor.getLong(cursor.getColumnIndex(contato.NOME)));
                contato.setId(cursor.getLong(cursor.getColumnIndex(contato.TELEFONE)));
                contato.setId(cursor.getLong(cursor.getColumnIndex(contato.TIPOTELEFONE)));
                contato.setId(cursor.getLong(cursor.getColumnIndex(contato.EMAIL)));
                contato.setId(cursor.getLong(cursor.getColumnIndex(contato.TIPOTELEFONE)));
                contato.setId(cursor.getLong(cursor.getColumnIndex(contato.ENDERECO)));
                contato.setId(cursor.getLong(cursor.getColumnIndex(contato.TIPOENDERECO)));
                contato.setId(cursor.getLong(cursor.getColumnIndex(contato.DATASESPECIAIS)));
                contato.setId(cursor.getLong(cursor.getColumnIndex(contato.TIPODATASESPECIAIS)));
                contato.setId(cursor.getLong(cursor.getColumnIndex(contato.GRUPOS)));
                adpContatos.add(contato);
            }while (cursor.moveToNext());
        }

        return adpContatos;
    }
}
