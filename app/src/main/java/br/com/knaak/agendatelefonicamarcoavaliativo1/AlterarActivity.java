package br.com.knaak.agendatelefonicamarcoavaliativo1;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Vilson Knaak and Tiago Silveira on 13/04/2018.
 */

public class AlterarActivity extends AppCompatActivity {

    private EditText nome;
    private EditText ddd;
    private EditText telefone;
    private Button alterar;
    private Button deletar;
    private Cursor cursor;
    private BancoController crud;
    private String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);

        codigo = this.getIntent().getStringExtra("codigo");

        crud = new BancoController(this);

        nome = (EditText)findViewById(R.id.edtNome);
        ddd = (EditText)findViewById(R.id.edtDDD);
        telefone = (EditText)findViewById(R.id.edtTelefone);

        alterar = (Button)findViewById(R.id.btnAlterar);


        cursor = crud.carregaDadoById(Integer.parseInt(codigo));
        nome.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.NOME)));
        ddd.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.DDD)));
        telefone.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.TELEFONE)));

        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                crud.alteraRegistro(Integer.parseInt(codigo),
                        nome.getText().toString(),
                        ddd.getText().toString(),
                        telefone.getText().toString());

                Intent intent = new Intent(AlterarActivity.this, ListarActivity.class);
                startActivity(intent);
                finish();

            }
        });

        deletar = (Button)findViewById(R.id.btnDeletar);

        deletar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder delete = new AlertDialog.Builder(AlterarActivity.this);
                delete.setTitle("Deletando Registro")
                        .setMessage("Você tem certeza que quer excluir este registro?")
                            .setPositiveButton("Sim",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                crud.deletaRegistro(Integer.parseInt(codigo));

                                Intent intent = new Intent(AlterarActivity.this, ListarActivity.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton("Não", null).show();

            }
        });


    }

}
