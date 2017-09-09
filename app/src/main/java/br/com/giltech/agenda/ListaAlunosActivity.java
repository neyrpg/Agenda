package br.com.giltech.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.giltech.agenda.adapter.ListaAlunosAdapter;
import br.com.giltech.agenda.converter.AlunoConversor;
import br.com.giltech.agenda.dao.AlunoDAO;
import br.com.giltech.agenda.modelo.Aluno;
import br.com.giltech.agenda.task.AlunosTask;
import br.com.giltech.agenda.util.ConnectionUtil;


public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        listaAlunos = (ListView) findViewById(R.id.lista_alunos);

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);
                changeFormulario(aluno);
            }
        });

        Button novoAluno = (Button) findViewById(R.id.novo_aluno);
        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });

        registerForContextMenu(listaAlunos);
    }

    private void changeFormulario(Aluno aluno) {
        Intent intentVaiProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
        intentVaiProFormulario.putExtra("aluno", aluno);
        startActivity(intentVaiProFormulario);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Toast.makeText(ListaAlunosActivity.this, "Deletar o aluno " + aluno.getNome(), Toast.LENGTH_SHORT).show();

                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.deleta(aluno);
                dao.close();

                carregaLista();
                return false;
            }
        });
        MenuItem editar = menu.add("Editar");
        editar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                changeFormulario(aluno);
                return false;
            }
        });

        MenuItem siteAluno = menu.add("Site");
        //Intent intentSite = new Intent(ListaAlunosActivity.this, Browser.class); Intent explicita
        Intent intentSite = new Intent(Intent.ACTION_VIEW);//intent implicita
        String alunoSite = aluno.getSite();
        if (!alunoSite.startsWith("http://")) {
            alunoSite = "http://" + alunoSite;
        }

        intentSite.setData(Uri.parse(alunoSite));
        siteAluno.setIntent(intentSite);//outra forma de fazer

        MenuItem mensagem = menu.add("Mensagem");
        Intent intentMensagem = new Intent(Intent.ACTION_VIEW);
        intentMensagem.setData(Uri.parse("sms:" + aluno.getTelefone()));//URI são utilizadas pelo ANDROID para identificar a ação a ser tomada
        mensagem.setIntent(intentMensagem);

        MenuItem itemLocalizacao = menu.add("Localização");
        Intent intentLocalizacao = new Intent(Intent.ACTION_VIEW);
        intentLocalizacao.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        itemLocalizacao.setIntent(intentLocalizacao);

        final MenuItem ligar = menu.add("Ligar");
        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 123); // O valor 123 é utiliza na sobrescrita do método onRequestPermissionsResult
                    // onde posso capturar e tratar as permissões que o usuário aceitou.
                } else {
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(intentLigar);
                }
                return false;
            }
        });

    }

    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        //ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, R.layout.lista_alunos, alunos);
        ListaAlunosAdapter adapter = new ListaAlunosAdapter(this, alunos);
        listaAlunos.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_lista_alunos:
                AlunosTask alunosTask = new AlunosTask(this);
                alunosTask.execute();

                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
