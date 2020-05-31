package br.univali.cc.prog.appconsulta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLClientInfoException;

public class AddConsultaActivity extends AppCompatActivity {
    SQLiteDatabase db;
    EditText etIdPaciente;
    EditText etIdMedico;
    EditText etDataHoraInicio;
    EditText etDataHoraFim;
    EditText etObserv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_consulta);

        etIdPaciente = findViewById(R.id.etIDPaciente);
        etIdMedico = findViewById(R.id.etIDMedico);
        etDataHoraInicio = findViewById(R.id.etHorarioInicio);
        etDataHoraFim = findViewById(R.id.etHorarioFim);
        etObserv = findViewById(R.id.etObs);

        Button btnSalvarCons = findViewById(R.id.btnSalvarConsulta);
        btnSalvarCons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarBD();
            }
        });

    }
    private void salvarBD(){
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

            sql.append("INSERT INTO consulta(paciente_id, medico_id, data_hora_inicio, data_hora_fim, observacao) VALUES(");
            sql.append(idPaciente + ",");
            sql.append(idMedico + ",");
            sql.append(" ' " + dataHoraIni + " ' ,");
            sql.append(" ' " + dataHoraFim + " ',");
            sql.append(" ' " + observacao + " ' ");
            sql.append(");");

            try {
                db.execSQL(sql.toString());
                Toast.makeText(getApplicationContext(), "Consulta inserida com sucesso!", Toast.LENGTH_LONG).show();
            }catch (SQLException e){
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG);
            }
            etIdPaciente.setText("");
            etIdMedico.setText("");
            etDataHoraInicio.setText("");
            etDataHoraFim.setText("");
            etObserv.setText("");
            db.close();
        }
    }
}
