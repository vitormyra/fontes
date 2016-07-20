package br.com.videoaula.agendacontato;

import android.app.DatePickerDialog;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.videoaula.agendacontato.database.DataBase;
import br.com.videoaula.agendacontato.dominio.RepositorioContato;
import br.com.videoaula.agendacontato.dominio.entidades.Contato;


public class ActCadContatos extends AppCompatActivity {

    private EditText edtNome;
    private EditText edtTelefone;
    private EditText edtEmail;
    private EditText edtTipoDatasEspeciais;
    private EditText edtEndereco;
    private EditText edtGrupos;

    private Spinner spnTipoEmail;
    private Spinner spnTipoTelefone;
    private Spinner spnTipoEndereco;
    private Spinner spnTipoDatasEspeciais;

    private ArrayAdapter<String> adpTipoEmail;
    private ArrayAdapter<String> adpTipoTelefone;
    private ArrayAdapter<String> adpTipoEndereco;
    private ArrayAdapter<String> adpTipoDatasEspeciais;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepositorioContato repositorioContato;
    private Contato contato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cad_contatos);

        edtNome = (EditText)findViewById(R.id.edtNome);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtEndereco = (EditText)findViewById(R.id.edtEndereco);
        edtTipoDatasEspeciais = (EditText)findViewById(R.id.edtDatasEspeciais);
        edtGrupos = (EditText)findViewById(R.id.edtGrupos);
        edtTelefone = (EditText)findViewById(R.id.edtTelefone);

        spnTipoEmail = (Spinner)findViewById(R.id.spnTipoEmail);
        spnTipoTelefone = (Spinner)findViewById(R.id.spnTipoTelefone);
        spnTipoEndereco = (Spinner)findViewById(R.id.spnTipoEndereco);
        spnTipoDatasEspeciais = (Spinner)findViewById(R.id.spnTipoDatasEspeciais);

        adpTipoEmail = new ArrayAdapter<String>(ActCadContatos.this, android.R.layout.simple_spinner_item);
        adpTipoEmail.setDropDownViewResource(android.R.layout.simple_spinner_item);

        adpTipoTelefone = new ArrayAdapter<String>(ActCadContatos.this, android.R.layout.simple_spinner_item);
        adpTipoTelefone.setDropDownViewResource(android.R.layout.simple_spinner_item);

        adpTipoEndereco = new ArrayAdapter<String>(ActCadContatos.this, android.R.layout.simple_spinner_item);
        adpTipoEndereco.setDropDownViewResource(android.R.layout.simple_spinner_item);

        adpTipoDatasEspeciais = new ArrayAdapter<String>(ActCadContatos.this, android.R.layout.simple_spinner_item);
        adpTipoDatasEspeciais.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spnTipoEmail.setAdapter(adpTipoEmail);
        spnTipoTelefone.setAdapter(adpTipoTelefone);
        spnTipoEndereco.setAdapter(adpTipoEndereco);
        spnTipoDatasEspeciais.setAdapter(adpTipoDatasEspeciais);

        adpTipoEmail.add("Casa");
        adpTipoEmail.add("Trabalho");
        adpTipoEmail.add("Outros");

        adpTipoTelefone.add("Celular");
        adpTipoTelefone.add("Trabalho");
        adpTipoTelefone.add("Casa");
        adpTipoTelefone.add("Principal");
        adpTipoTelefone.add("Fax trabalho");
        adpTipoTelefone.add("Fax casa");
        adpTipoTelefone.add("Pager");
        adpTipoTelefone.add("Outros");

        adpTipoEndereco.add("Casa");
        adpTipoEndereco.add("Trabalho");
        adpTipoEndereco.add("Outros");

        adpTipoDatasEspeciais.add("Aniversário");
        adpTipoDatasEspeciais.add("Data comemorativa");
        adpTipoDatasEspeciais.add("Outros");

        ExibeDataListener listener = new ExibeDataListener();

        edtTipoDatasEspeciais.setOnClickListener(listener);
        edtTipoDatasEspeciais.setOnFocusChangeListener(listener);

        contato = new Contato();

        try{
            dataBase = new DataBase(ActCadContatos.this);
            conn = dataBase.getWritableDatabase();

            repositorioContato = new RepositorioContato(conn);
        }
        catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(ActCadContatos.this);
            dlg.setMessage("Erro ao criar o banco: " + ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_act_contato, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.mni_acao1:
                if(contato == null){
                    inserir();
                }
                finish();
                break;
            case R.id.mni_acao2:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void inserir(){
        try{
        contato.setNome(edtNome.getText().toString());
        contato.setTelefone(edtTelefone.getText().toString());
        contato.setEmail(edtEmail.getText().toString());
        contato.setEndereco(edtEndereco.getText().toString());

        contato.setGrupos(edtGrupos.getText().toString());


        contato.setTipoTelefone(String.valueOf(spnTipoTelefone.getSelectedItemPosition()));
        contato.setTipoEndereco(String.valueOf(spnTipoEndereco.getSelectedItemPosition()));
        contato.setTipoEmail(String.valueOf(spnTipoEmail.getSelectedItemPosition()));
        contato.setTipoDatasEspeciais(String.valueOf(spnTipoDatasEspeciais.getSelectedItemPosition()));

        repositorioContato.inserir(contato);
        }
        catch (Exception ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(ActCadContatos.this);
            dlg.setMessage("Erro ao inserir os dados: " + ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }

    private void exibeData(){
        Calendar calendar = Calendar.getInstance();

        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog dlg = new DatePickerDialog(ActCadContatos.this, new SelecionaDataListener(), ano, mes, dia);
        dlg.show();
    }

    private class ExibeDataListener implements View.OnClickListener, View.OnFocusChangeListener{

        @Override
        public void onClick(View view) {
            exibeData();
        }

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if(hasFocus){
                exibeData();
            }
        }
    }
    private class SelecionaDataListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            Date data = calendar.getTime();
            DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
            String dt = format.format(data);

            edtTipoDatasEspeciais.setText(dt);


            contato.setDatasEspeciais(data);
        }
    }
}
