package br.com.videoaula.agendacontato;

import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import android.view.*;
import android.content.*;

import android.database.sqlite.*;
import android.database.*;

import br.com.videoaula.agendacontato.database.DataBase;
import br.com.videoaula.agendacontato.dominio.RepositorioContato;
import br.com.videoaula.agendacontato.dominio.entidades.Contato;

public class ActContato extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    private ImageButton ibtAdicionar;
    private EditText edtPesquisa;
    private ListView lstContatos;
    private ArrayAdapter<Contato>adpContatos;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepositorioContato repositorioContato;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_act_contato, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_contato);

        ibtAdicionar = (ImageButton)findViewById(R.id.ibtAdicionar);
        edtPesquisa = (EditText)findViewById(R.id.edtPesquisa);
        lstContatos = (ListView)findViewById(R.id.lstContatos);


        ibtAdicionar.setOnClickListener(ActContato.this);
        lstContatos.setOnItemClickListener(ActContato.this);

        try{
            dataBase = new DataBase(ActContato.this);
            conn = dataBase.getWritableDatabase();

            repositorioContato = new RepositorioContato(conn);

            adpContatos = repositorioContato.buscaContatos(ActContato.this);

            lstContatos.setAdapter(adpContatos);
        }
        catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(ActContato.this);
            dlg.setMessage("Erro ao criar o banco: " + ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }

    @Override
    public void onClick(View view) {

        Intent it = new Intent(ActContato.this, ActCadContatos.class);
        startActivityForResult(it, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        adpContatos = repositorioContato.buscaContatos(ActContato.this);

        lstContatos.setAdapter(adpContatos);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Contato contato = adpContatos.getItem(position);

        Intent it = new Intent(ActContato.this, ActCadContatos.class);
        it.putExtra("CONTATO", contato);
        startActivityForResult(it, 0);
    }
}
