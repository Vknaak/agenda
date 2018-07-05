package br.com.knaak.agendatelefonicamarcoavaliativo1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PreferenciasActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MinhasPreferencias";
    private EditText edtCodigo_cidade;
    private EditText edtCodigo_operadora;
    private Button salvar;
    private Button lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        salvar = findViewById(R.id.btnsalvar);

        edtCodigo_cidade = findViewById(R.id.edtCodigo_cidade);
        edtCodigo_operadora = findViewById(R.id.edtCodigo_operadora);

        lista = (Button) findViewById(R.id.btnlista);
        lista.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PreferenciasActivity.this, ListarActivity.class);
                startActivity(intent);


            }
        });

        recuperarDados();
    }

    public void salvar(View view){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("DDD", edtCodigo_cidade.getText().toString());
        editor.putString("COD_OPERADORA", edtCodigo_operadora.getText().toString());

        editor.commit();

        Toast.makeText(getApplicationContext(), "Registro inserido com sucesso!", Toast.LENGTH_LONG).show();
    }

    public void recuperarDados(){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        edtCodigo_cidade.setText(settings.getString("DDD",""));
        edtCodigo_operadora.setText(settings.getString("COD_OPERADORA",""));
    }

}

