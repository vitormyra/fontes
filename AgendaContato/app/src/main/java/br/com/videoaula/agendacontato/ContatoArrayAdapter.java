package br.com.videoaula.agendacontato;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import br.com.videoaula.agendacontato.dominio.entidades.Contato;

/**
 * Created by vitormyra on 25/07/16.
 */

public class ContatoArrayAdapter extends ArrayAdapter<Contato> {

    private int resource = 0;
    private LayoutInflater inflater;
    private Context context;
    private String telefone;

    public ContatoArrayAdapter(Context context, int resource) {
        super(context, resource);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder viewHolder = null;
        if (convertView == null) {

            viewHolder = new ViewHolder();

            view = inflater.inflate(resource, parent, false);

            viewHolder.txtCor = (TextView) view.findViewById(R.id.txtCor);
            viewHolder.txtNome = (TextView) view.findViewById(R.id.txtNome);
            viewHolder.txtTelefone = (TextView) view.findViewById(R.id.txtTelefone);
            viewHolder.ibtLigar = (ImageButton)view.findViewById(R.id.ibtLigar);

            view.setTag(viewHolder);

            convertView = view;
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        Contato contato = getItem(position);

        if (contato.getNome().toUpperCase().startsWith("A")) {
            viewHolder.txtCor.setBackgroundColor(context.getResources().getColor(R.color.azul));
        }
        else  if (contato.getNome().toUpperCase().startsWith("B")) {
            viewHolder.txtCor.setBackgroundColor(context.getResources().getColor(R.color.vermelho));
        }
        else {
            viewHolder.txtCor.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }
        viewHolder.txtNome.setText(contato.getNome());
        viewHolder.txtTelefone.setText(contato.getTelefone());


        telefone = viewHolder.txtTelefone.getText().toString();
        RealizarLigacoes listenner_ibtLigar = new RealizarLigacoes();
        viewHolder.ibtLigar.setOnClickListener(listenner_ibtLigar);

        return view;
    }

    static class ViewHolder {
        TextView txtCor;
        TextView txtNome;
        TextView txtTelefone;
        ImageButton ibtLigar;
    }

    private class  RealizarLigacoes implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(context);
            dlg.setMessage("Realizando ligação " + telefone);
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }
}
