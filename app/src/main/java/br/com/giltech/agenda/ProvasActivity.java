package br.com.giltech.agenda;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Date;

import br.com.giltech.agenda.modelo.Prova;

public class ProvasActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = supportFragmentManager.beginTransaction();
        tx.replace(R.id.frame_principal, new ListaProvasFragment());
        int currentOrientation = getResources().getConfiguration().orientation; // verifica a orientação. Forma diferente do ensinado no curso.
        if(currentOrientation == Configuration.ORIENTATION_LANDSCAPE){
            tx.replace(R.id.frame_secundario, new DetalheProvaFragment());
        }
        tx.commit();
    }


    public void selecionaProva(Prova prova) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        int currentOrientation = getResources().getConfiguration().orientation; // verifica a orientação. Forma diferente do ensinado no curso.
        if(currentOrientation == Configuration.ORIENTATION_PORTRAIT){
            FragmentTransaction tx = supportFragmentManager.beginTransaction();

            DetalheProvaFragment detalheProvaFragment = new DetalheProvaFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("prova",prova);

            detalheProvaFragment.setArguments(bundle);

            tx.replace(R.id.frame_principal, detalheProvaFragment);
            tx.addToBackStack(null);
            tx.commit();
        } else {
            DetalheProvaFragment fragmentDetalheProva = (DetalheProvaFragment) supportFragmentManager.findFragmentById(R.id.frame_secundario);
            fragmentDetalheProva.populaFragmentDetalheProva(prova);
        }

    }
}
