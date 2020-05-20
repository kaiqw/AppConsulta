package br.univali.cc.prog.appconsulta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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
                "Médico",
                "Paciente",
                "Consulta"
        };

        ArrayAdapter<String> spArrayAdapterMain =
                new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, opcoesMain);
        spOpMain.setAdapter(spArrayAdapterMain);

        criarBD();
    }

    private void criarBD(){
        db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXITS medico(");
        sql.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sql.append("nome VARCHAR(50), ");
        sql.append("crm VARCHAR(50), ");
        sql.append("logradouro VARCHAR(100), ");
        sql.append("numero MEDIUMINT(8), ");
        sql.append("cidade VARCHAR(30), ");
        sql.append("uf VARCHAR(2), ");
        sql.append("celular VARCHAR(20), ");
        sql.append("fixo VARCHAR(20)");
        sql.append(")");

        sql.append("CREATE TABLE IF NOT EXITS paciente(");
        sql.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sql.append("nome VARCHAR(50), ");
        sql.append("grp_sanguineo TINYINT(1), ");
        sql.append("logradouro VARCHAR(100), ");
        sql.append("numero MEDIUMINT(8), ");
        sql.append("cidade VARCHAR(30), ");
        sql.append("uf VARCHAR(2), ");
        sql.append("celular VARCHAR(20), ");
        sql.append("fixo VARCHAR(20)");
        sql.append(")");

        sql.append("CREATE TABLE IF NOT EXITS consulta(");
        sql.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sql.append("paciente_id INTEGER NOT NULL, ");
        sql.append("medico_id INTEGER NOT NULL, ");
        sql.append("data DATE, ");
        sql.append("hora_inicio TIME, ");
        sql.append("hora_fim TIME, ");
        sql.append("observacao VARCHAR(200), ");
        sql.append("FOREING KEY(paciente_id) REFERENCES paciente(id), ");
        sql.append("FOREING KEY(medico_id) REFERENCES medico(id)");
        sql.append(")");

        try {
            db.execSQL(sql.toString());
            Toast.makeText(getApplicationContext(), "BD CRIADO COM SUCESSO", Toast.LENGTH_LONG).show();
        } catch (SQLException e){
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        db.close();
    }
}
