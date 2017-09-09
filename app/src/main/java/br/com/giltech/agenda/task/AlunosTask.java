package br.com.giltech.agenda.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import br.com.giltech.agenda.converter.AlunoConversor;
import br.com.giltech.agenda.dao.AlunoDAO;
import br.com.giltech.agenda.modelo.Aluno;
import br.com.giltech.agenda.util.ConnectionUtil;

/**
 * Created by Gilciney Marques on 09/09/2017.
 */

public class AlunosTask extends AsyncTask<Aluno, Object, String> {
    Context context;

    public AlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
      Toast.makeText(context, "Enviando dados", Toast.LENGTH_LONG).show();

    }

    @Override
    protected String doInBackground(Aluno[] objects) {


        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();
        AlunoConversor conversor = new AlunoConversor();
        String json = conversor.converteJson(alunos);

        ConnectionUtil util = new ConnectionUtil();
        String resposta =util.post(json);
       // Toast.makeText(context, "Enviando notas ..."+resposta, Toast.LENGTH_LONG).show();
        return resposta;
    }

    @Override
    protected void onPostExecute(String o) {
        Toast.makeText(context, "Enviando notas ..."+o, Toast.LENGTH_LONG).show();
    }
}
