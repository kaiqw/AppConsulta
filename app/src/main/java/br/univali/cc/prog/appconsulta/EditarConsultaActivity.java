package br.univali.cc.prog.appconsulta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditarConsultaActivity extends AppCompatActivity {
    SQLiteDatabase db;
    EditText etIdPaciente;
    EditText etIdMedico;
    EditText etDataHoraInicio;
    EditText etDataHoraFim;
    EditText etObserv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_consulta);

        etIdPaciente = findViewById(R.id.etIDPaciente);
        etIdMedico = findViewById(R.id.etIDMedico);
        etDataHoraInicio = findViewById(R.id.etHorarioInicio);
        etDataHoraFim = findViewById(R.id.etHorarioFim);
        etObserv = findViewById(R.id.etObs);

        Intent valores  = getIntent();
        etIdPaciente.setText(valores.getStringExtra("paciente_id"));
        etIdMedico.setText(valores.getStringExtra("medico_id"));
        etDataHoraInicio.setText(valores.getStringExtra("data_hora_inicio"));
        etDataHoraFim.setText(valores.getStringExtra("data_hora_fim"));
        etObserv.setText(valores.getStringExtra("observacao"));
        final String id = valores.getStringExtra("_id");

        Button clickEditarConsulta = findViewById(R.id.btnEditarCons);
        clickEditarConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarBD(id);
            }
        });

        Button clickExcluirConsulta = findViewById(R.id.btnExcluirCons);
        clickExcluirConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirBD(id);
            }
        });
    }

    private void salvarBD(String id){
        String idPaciente = etIdPaciente.getText().toString().trim();
        String idMedico = etIdMedico.getText().toString().trim();
        String dataHoraIni = etDataHoraInicio.getText().toString().trim();
        String dataHoraFim = etDataHoraFim.getText().toString().trim();
        String observacao = etObserv.getText().toString().trim();

        if(idPaciente.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, Informe o ID do Paciente!", Toast.LENGTH_LONG).show();
        }else if(idMedico.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, Informe o ID do Médico!", Toast.LENGTH_LONG).show();
        }else if(dataHoraIni.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, Informe a Data e Horário de Início!", Toast.LENGTH_LONG).show();
        }else if(dataHoraFim.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, Informe a Data e Horário do Fim!", Toast.LENGTH_LONG).show();
        }else if(observacao.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, Digite uma Observação!", Toast.LENGTH_LONG).show();
        }else{
            db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
            StringBuilder sql = new StringBuilder();

            sql.append("UPDATE aluno SET ");
            sql.append("paciente_id = '" + idPaciente + "', ");
            sql.append("medico_id = " + idMedico + ", ");
            sql.append("data_hora_inicio = '" + dataHoraIni + "' ");
            sql.append("data_hora_fim = '" + dataHoraFim + "' ");
            sql.append("observacao = '" + observacao + "' ");
            sql.append("WHERE _id = " + id + ";");

            try {
                db.execSQL(sql.toString());
                Toast.makeText(getApplicationContext(), "Consulta atualizada", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            Intent i = new Intent(getApplicationContext(), ListarConsultaActivity.class);
            startActivity(i);
            db.close();
        }
    }

    private void excluirBD(String id) {
        db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM consulta ");
        sql.append("WHERE _id = " + id + ";");
        try {
            db.execSQL(sql.toString());
            Toast.makeText(getApplicationContext(), "Consulta excluída", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Intent i = new Intent(getApplicationContext(), ListarConsultaActivity.class);
        startActivity(i);
        db.close();
    }
}
