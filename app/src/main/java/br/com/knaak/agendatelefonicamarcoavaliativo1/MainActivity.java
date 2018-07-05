package br.com.knaak.agendatelefonicamarcoavaliativo1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button botao;
    private EditText nome;
    private EditText ddd;
    private EditText telefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botao = (Button)findViewById(R.id.btnCadastrar);

        botao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                BancoController crud = new BancoController(MainActivity.this);

                nome = (EditText)findViewById(R.id.edtNome);
                ddd = (EditText)findViewById((R.id.edtDDD));
                telefone = (EditText)findViewById(R.id.edtTelefone);

                String nomeString = nome.getText().toString();
                String dddString = ddd.getText().toString();
                String telefoneString = telefone.getText().toString();

                if(nomeString.isEmpty() || dddString.isEmpty() || telefoneString.isEmpty()){
                    final AlertDialog.Builder  msg = new AlertDialog.Builder(MainActivity.this);
                    msg. setTitle("Alerta").setMessage("Todos os campos devem estar preenchidos!")
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            nome.setText("");
                            ddd.setText("");
                            telefone.setText("");
                        }
                    }).show();
                }else {
                    String resultado = crud.insereDado(nomeString, dddString, telefoneString);
                    Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void btnListar(View view) {
        Intent intent = new Intent(this, ListarActivity.class);
        startActivity(intent);
    }
}