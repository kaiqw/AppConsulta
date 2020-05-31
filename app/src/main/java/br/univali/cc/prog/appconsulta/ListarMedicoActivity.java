package br.univali.cc.prog.appconsulta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ListarMedicoActivity extends AppCompatActivity {
    SQLiteDatabase db;
    ListView lvMedicos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_medico);

        lvMedicos = findViewById(R.id.lvMedicos);

        listarMedicos();

        lvMedicos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View v = lvMedicos.getChildAt(position);
                TextView tvListID = v.findViewById(R.id.tvListID);
                TextView tvListNome = v.findViewById(R.id.tvListNome);
                TextView tvListCrm = v.findViewById(R.id.tvListCRM);
                TextView tvListLograd = v.findViewById(R.id.tvListLogradouro);
                TextView tvListNumero = v.findViewById(R.id.tvListNumero);
                TextView tvListCidade = v.findViewById(R.id.tvListCidade);
                TextView tvListUf = v.findViewById(R.id.tvListUF);
                TextView tvListCelular = v.findViewById(R.id.tvListCelular);
                TextView tvListfixo = v.findViewById(R.id.tvListFixo);

                Intent i = new Intent(getApplicationContext(), EditarMedicoActivity.class);
                i.putExtra("id", tvListID.getText().toString());
                i.putExtra("nome", tvListNome.getText().toString());
                i.putExtra("crm", tvListCrm.getText().toString());
                i.putExtra("logradouro", tvListLograd.getText().toString());
                i.putExtra("numero", tvListNumero.getText().toString());
                i.putExtra("cidade", tvListCidade.getText().toString());
                i.putExtra("uf", tvListUf.getText().toString());
                i.putExtra("celular", tvListCelular.getText().toString());
                i.putExtra("fixo", tvListfixo.getText().toString());
                startActivity(i);
            }
        });
    }

    private void listarMedicos(){
        db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM medico;");

        Cursor dadosMedico = db.rawQuery(sql.toString(), null);
        String[] from = {"id", "nome", "crm", "logradouro", "numero", "cidade", "uf", "celular", "fixo"};
        int[] to = {R.id.tvListID, R.id.tvListNome, R.id.tvListCRM, R.id.tvListLogradouro, R.id.tvListNumero,
                R.id.tvListCidade, R.id.tvListUF, R.id.tvListCelular, R.id.tvListFixo};

        SimpleCursorAdapter scAdapter =
                new SimpleCursorAdapter(getApplicationContext(), R.layout.dadosmedico, dadosMedico, from, to, 0);

        lvMedicos.setAdapter(scAdapter);
        db.close();
    }
}
