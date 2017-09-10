package br.com.giltech.agenda;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.giltech.agenda.modelo.Prova;


public class DetalheProvaFragment extends Fragment {

    TextView materia;
    TextView data;
    ListView listaConteudo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhe_prova, container, false);
        materia = (TextView) view.findViewById(R.id.detalhe_prova_materia);
        data = (TextView) view.findViewById(R.id.detalhe_data_materia);
        listaConteudo = (ListView) view.findViewById(R.id.detalhe_conteudo_materia);
        Bundle parametros = getArguments();
        if(parametros != null){
            Prova prova = (Prova) parametros.getSerializable("prova");
            populaFragmentDetalheProva(prova);
        }


        return view;
    }

    public void populaFragmentDetalheProva(Prova prova) {
        materia.setText(prova.getMateria());
        data.setText(prova.getData());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, prova.getConteudo());
        listaConteudo.setAdapter(adapter);
    }

}
