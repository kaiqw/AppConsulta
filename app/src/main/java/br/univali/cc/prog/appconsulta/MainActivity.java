package br.univali.cc.prog.appconsulta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    Spinner spOpMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spOpMain = findViewById(R.id.spOpcoesMain);

        String[] opcoesMain = new String[] {
                "-SELECIONE-",
                "Médico",
                "Paciente",
                "Consulta"
        };
        ArrayAdapter<String> spArrayAdapterMain =
                new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, opcoesMain);
        spOpMain.setAdapter(spArrayAdapterMain);

        criarBD();

        Button clickAdicionar = findViewById(R.id.btn_Adicionar);
        clickAdicionar.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String nomeOpcaoMain = spOpMain.getSelectedItem().toString();
              if (nomeOpcaoMain.equals("Médico")){
                  Intent i = new Intent(getApplicationContext(), AddMedicoActivity.class);
                  startActivity(i);
              }
              else if (nomeOpcaoMain.equals("Paciente")){
                  Intent i = new Intent(getApplicationContext(), AddPacienteActivity.class);
                  startActivity(i);
              }else if (nomeOpcaoMain.equals("Consulta") ){
                  Intent i = new Intent(getApplicationContext(), AddConsultaActivity.class);
                  startActivity(i);
              }
            }
        }));

        Button clickListar = findViewById(R.id.btn_Listar);
        clickListar.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomeOpcaoMain = spOpMain.getSelectedItem().toString();
                if (nomeOpcaoMain.equals("Médico")){
                    Intent i = new Intent(getApplicationContext(), ListarMedicoActivity.class);
                    startActivity(i);
                }
                else if (nomeOpcaoMain.equals("Paciente")){
                    Intent i = new Intent(getApplicationContext(), ListarPacienteActivity.class);
                    startActivity(i);
                }else if (nomeOpcaoMain.equals("Consulta") ){
                    Intent i = new Intent(getApplicationContext(), ListarConsultaActivity.class);
                    startActivity(i);
                }
            }
        }));

    }

    private void criarBD(){
        db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS medico(");
        sql.append("id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sql.append("nome VARCHAR(50), ");
        sql.append("crm VARCHAR(50), ");
        sql.append("logradouro VARCHAR(100), ");
        sql.append("numero MEDIUMINT(8), ");
        sql.append("cidade VARCHAR(30), ");
        sql.append("uf VARCHAR(2), ");
        sql.append("celular VARCHAR(20), ");
        sql.append("fixo VARCHAR(20)");
        sql.append(");");

        sql.append("CREATE TABLE IF NOT EXISTS paciente(");
        sql.append("id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sql.append("nome VARCHAR(50), ");
        sql.append("grp_sanguineo TINYINT(1), ");
        sql.append("logradouro VARCHAR(100), ");
        sql.append("numero MEDIUMINT(8), ");
        sql.append("cidade VARCHAR(30), ");
        sql.append("uf VARCHAR(2), ");
        sql.append("celular VARCHAR(20), ");
        sql.append("fixo VARCHAR(20)");
        sql.append(");");

        sql.append("CREATE TABLE IF NOT EXISTS consulta (");
        sql.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sql.append("paciente_id INTEGER NOT NULL, ");
        sql.append("medico_id INTEGER NOT NULL, ");
        sql.append("data_hora_inicio DATETIME, ");
        sql.append("data_hora_fim DATETIME, ");
        sql.append("observacao VARCHAR(200), ");
        sql.append("FOREIGN KEY(paciente_id) REFERENCES paciente(id), ");
        sql.append("FOREIGN KEY(medico_id) REFERENCES medico(id)");
        sql.append(");");

        try {
            String[] queries  = sql.toString().split(";");
            for (String query : queries){
                db.execSQL(query);
            }
            Toast.makeText(getApplicationContext(), "Banco de Dados criado com sucesso!", Toast.LENGTH_LONG).show();

        } catch (SQLException e){
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        db.close();
    }
}
