package br.com.giltech.agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.giltech.agenda.modelo.Prova;

public class DetalheProvaActivity extends AppCompatActivity {

    Prova prova;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_prova);

        prova = (Prova) getIntent().getSerializableExtra("prova");

        TextView materia = (TextView) findViewById(R.id.detalhe_prova_materia);
        TextView data = (TextView) findViewById(R.id.detalhe_data_materia);
        ListView listaConteudo = (ListView) findViewById(R.id.detalhe_conteudo_materia);

        materia.setText(prova.getMateria());
        data.setText(prova.getData());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, prova.getConteudo());

        listaConteudo.setAdapter(adapter);


    }
}
