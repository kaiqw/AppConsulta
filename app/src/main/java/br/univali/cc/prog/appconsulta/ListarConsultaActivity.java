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

public class ListarConsultaActivity extends AppCompatActivity {
    SQLiteDatabase db;
    ListView lvConsultas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_consulta);

        lvConsultas = findViewById(R.id.lvConsultas);

        listarConsultas();

        lvConsultas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View v = lvConsultas.getChildAt(position);
                TextView tvListId = v.findViewById(R.id.tvListIDConsulta);
                TextView tvListId_pac = v.findViewById(R.id.tvListId_pac);
                TextView tvListId_med = v.findViewById(R.id.tvListid_med);
                TextView tvListDataHini = v.findViewById(R.id.tvListDataHoraIni);
                TextView tvListDataHfim = v.findViewById(R.id.tvListDataHoraFim);
                TextView tvListObs = v.findViewById(R.id.tvListObs);

                Intent i = new Intent(getApplicationContext(), EditarConsultaActivity.class);
                i.putExtra("_id", tvListId.getText().toString());
                i.putExtra("paciente_id", tvListId_pac.getText().toString());
                i.putExtra("medico_id", tvListId_med.getText().toString());
                i.putExtra("data_hora_inicio", tvListDataHini.getText().toString());
                i.putExtra("data_hora_fim", tvListDataHfim.getText().toString());
                i.putExtra("observacao", tvListObs.getText().toString());
                startActivity(i);
            }
        });
    }
    private void listarConsultas () {
        db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM consulta;");
        Cursor dadosConsul = db.rawQuery(sql.toString(), null);
        String[] from = {"_id", "paciente_id", "medico_id", "data_hora_inicio", "data_hora_fim",
                "observacao"};

        int[] to = {R.id.tvListIDConsulta, R.id.tvListId_pac, R.id.tvListid_med, R.id.tvListDataHoraIni,
                R.id.tvListDataHoraFim, R.id.tvListObs};

        SimpleCursorAdapter scAdapter =
                new SimpleCursorAdapter(getApplicationContext(), R.layout.dadosconsultas, dadosConsul, from, to, 0);

        lvConsultas.setAdapter(scAdapter);
        db.close();
    }



}
