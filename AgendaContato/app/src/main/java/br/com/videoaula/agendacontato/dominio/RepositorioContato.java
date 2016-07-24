package br.com.videoaula.agendacontato.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import java.util.Date;
import java.util.StringTokenizer;

import br.com.videoaula.agendacontato.dominio.entidades.Contato;

/**
 * Created by vitormyra on 17/07/16.
 */
public class RepositorioContato {


    private SQLiteDatabase conn;

    public RepositorioContato(SQLiteDatabase conn) {
        this.conn = conn;

    }

    private ContentValues preencheContentValues(Contato contato){

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

        return values;
    }

    public void excluir(long id){
        conn.delete("CONTATO", "_id = ? ", new String[]{String.valueOf(id)});
    }

    public void alterar(Contato contato) {
            ContentValues values = preencheContentValues(contato);
            conn.update("CONTATO", values, "_id = ? ", new String[]{String.valueOf(contato.getId())});
    }

    public void inserir(Contato contato) {
            ContentValues values = preencheContentValues(contato);
            conn.insertOrThrow("CONTATO", null, values);
    }


    public ArrayAdapter<Contato> buscaContatos(Context context) {

        ArrayAdapter<Contato> adpContatos = new ArrayAdapter<Contato>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = conn.query("CONTATO", null, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Contato contato = new Contato();
                contato.setId(cursor.getLong(cursor.getColumnIndex(contato.ID)));
                contato.setNome(cursor.getString(cursor.getColumnIndex(contato.NOME)));
                contato.setTelefone(cursor.getString(cursor.getColumnIndex(contato.TELEFONE)));
                contato.setTipoTelefone(cursor.getString(cursor.getColumnIndex(contato.TIPOTELEFONE)));
                contato.setEmail(cursor.getString(cursor.getColumnIndex(contato.EMAIL)));
                contato.setTipoEmail(cursor.getString(cursor.getColumnIndex(contato.TIPOTELEFONE)));
                contato.setEndereco(cursor.getString(cursor.getColumnIndex(contato.ENDERECO)));
                contato.setTipoEndereco(cursor.getString(cursor.getColumnIndex(contato.TIPOENDERECO)));
                contato.setDatasEspeciais(new Date(cursor.getLong(cursor.getColumnIndex(contato.DATASESPECIAIS))));
                contato.setTipoDatasEspeciais(cursor.getString(cursor.getColumnIndex(contato.TIPODATASESPECIAIS)));
                contato.setGrupos(cursor.getString(cursor.getColumnIndex(contato.GRUPOS)));
                adpContatos.add(contato);
            } while (cursor.moveToNext());
        }

        return adpContatos;
    }
}
