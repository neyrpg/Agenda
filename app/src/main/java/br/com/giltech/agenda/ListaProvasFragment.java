package br.com.giltech.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

import br.com.giltech.agenda.modelo.Prova;

/**
 * Created by Gilciney Marques on 10/09/2017.
 */

public class ListaProvasFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_provas, container, false);

        Prova portugues = new Prova("Português", "01/05/2017", Arrays.asList("Pronome","verbo","teste"));
        Prova matematica = new Prova("Matemática", "10/10/2001", Arrays.asList("Equações","Trigonometria","cálculo"));


        final ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(view.getContext(), android.R.layout.simple_list_item_1,Arrays.asList(portugues,matematica));

        ListView listView = (ListView) view.findViewById(R.id.provas_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Prova prova = (Prova) adapterView.getItemAtPosition(i);
                Toast.makeText(view.getContext(), "Clicou na prova ="+prova, Toast.LENGTH_SHORT).show();
                ProvasActivity provasActivity = (ProvasActivity) getActivity();
                provasActivity.selecionaProva(prova);
            }
        });


        return view;
    }
}
