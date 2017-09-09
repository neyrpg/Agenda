package br.com.giltech.agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.giltech.agenda.R;
import br.com.giltech.agenda.modelo.Aluno;

/**
 * Created by Gilciney Marques on 08/09/2017.
 */

public class ListaAlunosAdapter extends BaseAdapter {

    private List<Aluno> alunos;
    private Context ctx;

    public ListaAlunosAdapter(Context ctx, List<Aluno> alunos) {
        this.alunos = alunos;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int i) {
        return alunos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return alunos.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        //TODO Implementar outra forma de viewholder
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View viewLista = convertView;
        if(viewLista == null){
            viewLista = inflater.inflate(R.layout.lista_alunos, parent, false);
        }


        TextView nome = viewLista.findViewById(R.id.lista_nome_aluno);
        TextView telefone = viewLista.findViewById(R.id.lista_telefone_aluno);
        ImageView foto = viewLista.findViewById(R.id.lista_imagem_foto);
        Aluno aluno = alunos.get(i);

        nome.setText(aluno.getNome());
        telefone.setText(aluno.getTelefone());
        if (aluno.getFoto() != null){
            Bitmap bitmap = BitmapFactory.decodeFile(aluno.getFoto());
            bitmap = Bitmap.createScaledBitmap(bitmap,300,300, true);
            foto.setImageBitmap(bitmap);
            foto.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return viewLista;
    }
}
