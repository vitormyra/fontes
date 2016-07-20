package br.com.videoaula.agendacontato.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.widget.*;

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
        values.put("NOME", contato.getNome());
        values.put("TELEFONE", contato.getTelefone());
        values.put("TIPOTELEFONE", contato.getTipoTelefone());
        values.put("EMAIL", contato.getEmail());
        values.put("TIPOEMAIL", contato.getTipoEmail());
        values.put("ENDERECO", contato.getEndereco());
        values.put("TIPOENDERECO", contato.getTipoEndereco());
        values.put("DATASESPECIAIS", contato.getDatasEspeciais().getTime());
        values.put("TIPODATASESPECIAIS", contato.getTipoDatasEspeciais());
        values.put("GRUPOS", contato.getGrupos());

        conn.insertOrThrow("CONTATO", null, values);
    }


    public ArrayAdapter<Contato>buscaContatos(Context context){

        ArrayAdapter<Contato> adpContatos = new ArrayAdapter<Contato>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = conn.query("CONTATO", null, null,null, null, null, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                Contato contato = new Contato();
                contato.setNome(cursor.getString(1));
                contato.setTelefone(cursor.getString(2));
                contato.setTipoTelefone(cursor.getString(3));
                contato.setEmail(cursor.getString(4));
                contato.setTipoEmail(cursor.getString(5));
                contato.setEndereco(cursor.getString(6));
                contato.setTipoEndereco(cursor.getString(7));
                contato.setDatasEspeciais(new Date(cursor.getLong(8)));
                contato.setTipoDatasEspeciais(cursor.getString(9));
                contato.setGrupos(cursor.getString(10));
                adpContatos.add(contato);
            }while (cursor.moveToNext());
        }

        return adpContatos;
    }
}
