package br.com.knaak.agendatelefonicamarcoavaliativo1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Vilson Knaak and Tiago Silveira on 13/04/2018.
 */

public class ListarActivity extends AppCompatActivity {

    private ListView lista;
    private Button voltar;
    private Button preferencias;
    public static final String PREFS_NAME = "MinhasPreferencias";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        BancoController crud = new BancoController(ListarActivity.this);

        final Cursor cursor = crud.carregaDados();

        String[] nomeCampos = new String[]{CriaBanco.ID, CriaBanco.NOME, CriaBanco.DDD, CriaBanco.TELEFONE};

        final int[] idViews = new int[]{R.id.idagenda, R.id.idNome, R.id.idDDD, R.id.idTelefone};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(ListarActivity.this,
                R.layout.item_lista,
                cursor,
                nomeCampos,
                idViews, 0);

        lista = (ListView) findViewById(R.id.listView);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                String codigo = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.ID));

                Intent intent = new Intent(ListarActivity.this, AlterarActivity.class);
                intent.putExtra("codigo", codigo);
                startActivity(intent);
                finish();
            }

        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                String[] sharePref = recuperaSharedPreferences();

                String ddd = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.DDD));
                String telefone = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.TELEFONE));

                Uri uri = null;
                if (ddd.equalsIgnoreCase(sharePref[0])) {
                    uri = Uri.parse("tel:" + telefone);
                } else {
                    uri = Uri.parse("tel: 0" + sharePref[1] + ddd + telefone);
                }

                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);

                return true;
            }
        });

        voltar = (Button) findViewById(R.id.btnVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ListarActivity.this, Main2Activity.class);
                startActivity(intent);

            }
        });

         preferencias = (Button)findViewById(R.id.btnpreferencias);
         preferencias.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(ListarActivity.this, PreferenciasActivity.class);
                 startActivity(intent);
             }
         });
    }

    public String[] recuperaSharedPreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        String[] sharePref = new String[2];
        sharePref[0] = settings.getString("DDD","51");
        sharePref[1] = settings.getString("COD_OPERADORA","21");

        return sharePref;
    }
}







