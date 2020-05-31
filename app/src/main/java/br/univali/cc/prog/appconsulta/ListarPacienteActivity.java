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

public class ListarPacienteActivity extends AppCompatActivity {
    SQLiteDatabase db;
    ListView lvPacientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_paciente);

        lvPacientes = findViewById(R.id.lvPacientes);
        listarPacientes();

        lvPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View v = lvPacientes.getChildAt(position);
                TextView tvListId = v.findViewById(R.id.tvListIdPac);
                TextView tvListnome = v.findViewById(R.id.tvListNomePac);
                TextView tvListGpSangue = v.findViewById(R.id.tvListGpSanguePac);
                TextView tvListLogrado = v.findViewById(R.id.tvListLogradouroPac);
                TextView tvListNum = v.findViewById(R.id.tvListNumeroPac);
                TextView tvListCidade = v.findViewById(R.id.tvListCidadePac);
                TextView tvListUf = v.findViewById(R.id.tvListUFPac);
                TextView tvListCelular = v.findViewById(R.id.tvListCelularPac);
                TextView tvListFixo = v.findViewById(R.id.tvListFixoPac);

                Intent i = new Intent(getApplicationContext(), EditarPacienteActivity.class);
                i.putExtra("id", tvListId.getText().toString());
                i.putExtra("nome", tvListnome.getText().toString());
                i.putExtra("grp_sanguineo", tvListGpSangue.getText().toString());
                i.putExtra("logradouro", tvListLogrado.getText().toString());
                i.putExtra("numero", tvListNum.getText().toString());
                i.putExtra("cidade", tvListCidade.getText().toString());
                i.putExtra("uf", tvListUf.getText().toString());
                i.putExtra("celular", tvListCelular.getText().toString());
                i.putExtra("fixo", tvListFixo.getText().toString());
                startActivity(i);
            }
        });
    }

    private void listarPacientes(){
        db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM paciente;");
        Cursor dadosPac  = db.rawQuery(sql.toString(), null);
        String[] from = {"_id", "nome", "grp_sanguineo", "logradouro", "numero", "cidade", "uf",
                        "celular", "fixo"};
        int[] to = {R.id.tvListIdPac, R.id.tvListNomePac, R.id.tvListGpSanguePac, R.id.tvListLogradouroPac,
                    R.id.tvListNumeroPac, R.id.tvListCidadePac, R.id.tvListUFPac, R.id.tvListCelularPac,
                    R.id.tvListFixoPac};
        SimpleCursorAdapter scAdapter=
                new SimpleCursorAdapter(getApplicationContext(), R.layout.dadospaciente, dadosPac,from, to, 0);

        lvPacientes.setAdapter(scAdapter);
        db.close();
    }
}
