package br.com.giltech.agenda;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import br.com.giltech.agenda.dao.AlunoDAO;
import br.com.giltech.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    public static final int CAMERA_CAPTURA = 567;
    private FormularioHelper helper;
    File newArquivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);

        Intent intent = getIntent();
       final Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");

        if(aluno != null){
            helper.preencheFormulario(aluno);
        }
        Button botao = (Button) findViewById(R.id.formulario_botao_foto);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                File imagePath = new File(FormularioActivity.this.getFilesDir(), "images");
                if (!imagePath.exists()) imagePath.mkdirs();
                newArquivo = new File(imagePath, System.currentTimeMillis()+".jpg");
                Uri pathFotos = FileProvider.getUriForFile(FormularioActivity.this, "br.com.giltech.agenda.fileprovider", newArquivo); // a partir da api 22 é preciso utilizar fileprovider

                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intentCamera.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, pathFotos);
                startActivityForResult(intentCamera, CAMERA_CAPTURA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == CAMERA_CAPTURA){
            ImageView foto = (ImageView) findViewById(R.id.formulario_imagem);
            Bitmap bitmap = BitmapFactory.decodeFile(newArquivo.getAbsolutePath());
            bitmap = Bitmap.createScaledBitmap(bitmap,300,300, true);
            foto.setImageBitmap(bitmap);
            foto.setScaleType(ImageView.ScaleType.FIT_XY);
            foto.setTag(newArquivo.getAbsolutePath());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                Aluno aluno = helper.getAluno();
                AlunoDAO dao = new AlunoDAO(this);

                if(aluno.getId() != null){
                    dao.altera(aluno);
                }else{
                    dao.insere(aluno);
                }

                dao.close();
                Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " salvo!", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
