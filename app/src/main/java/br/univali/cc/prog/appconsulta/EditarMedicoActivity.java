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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditarMedicoActivity extends AppCompatActivity {
    SQLiteDatabase db;
    EditText etNome;
    EditText etCRM;
    EditText etLogradouro;
    EditText etNumero;
    EditText etCidade;
    Spinner spUF;
    EditText etCelular;
    EditText etFixo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_medico);

        etNome = findViewById(R.id.etNomeMedico);
        etCRM = findViewById(R.id.etCRM);
        etLogradouro = findViewById(R.id.etLogradouroMed);
        etNumero = findViewById(R.id.etNumeroMed);
        etCidade = findViewById(R.id.etCidadeMed);
        spUF = findViewById(R.id.spUfMed);
        etCelular = findViewById(R.id.etCelularMed);
        etFixo = findViewById(R.id.etFixoMed);

        String[] spUFmed = new String[] {
                "Escolha uma opção",
                "RO", "AC", "AM", "RR", "PA",
                "AP", "TO", "MA", "PI", "CE",
                "RN", "PB", "PE", "AL", "SE",
                "BA", "MG", "ES", "RJ", "SP",
                "PR", "SC", "RS", "MS", "MT",
                "GO", "DF"
        };
        ArrayAdapter<String> spArrayAdapUF =
                new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, spUFmed);
        spUF.setAdapter(spArrayAdapUF);

        Intent valores = getIntent();
        etNome.setText(valores.getStringExtra("nome"));
        etCRM.setText(valores.getStringExtra("crm"));
        etLogradouro.setText(valores.getStringExtra("logradouro"));
        etNumero.setText(valores.getStringExtra("numero"));
        etCidade.setText(valores.getStringExtra("cidade"));
        String ufExtra = valores.getStringExtra("uf");

        int aux = 0;
        for (String u : spUFmed){
            if(u.equals(ufExtra)){
                break;
            }
            aux ++;
        }
        spUF.setSelection(aux);
        final String id = valores.getStringExtra("id");

        Button clickEditarMedico = findViewById(R.id.btnEditarMed);
        clickEditarMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarBD(id);
            }
        });

        Button clickExcluirMed = findViewById(R.id.btnExcluirMed);
        clickExcluirMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirBD(id);
            }
        });
    }

    private void salvarBD(String id){
        String nome = etNome.getText().toString().trim();
        String crm = etCRM.getText().toString().trim();
        String logradouro = etLogradouro.getText().toString().trim();
        String numero = etNumero.getText().toString().trim();
        String cidade = etCidade.getText().toString().trim();
        String uf = spUF.getSelectedItem().toString();
        String celular = etCelular.getText().toString().trim();
        String fixo = etFixo.getText().toString().trim();

        if (nome.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, informe o Nome!", Toast.LENGTH_LONG).show();
        }else if(crm.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, informe o CRM!", Toast.LENGTH_LONG).show();
        }else if(logradouro.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, informe o Logradouro!", Toast.LENGTH_LONG).show();
        }else if(numero.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, informe o Número!", Toast.LENGTH_LONG).show();
        }else if(cidade.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, informe a Cidade!", Toast.LENGTH_LONG).show();
        }else if(uf.equals("Escolha uma opção")) {
            Toast.makeText(getApplicationContext(), "Por favor, Selecione a UF!", Toast.LENGTH_LONG).show();
        }else if(celular.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, informe o telefone Celular!", Toast.LENGTH_LONG).show();
        }else if(fixo.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, informe o telefone Fixo!", Toast.LENGTH_LONG).show();
        }else {
            db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
            StringBuilder sql = new StringBuilder();

            sql.append("UPDATE medico SET ");
            sql.append("nome = ' " + nome + " ' ,");
            sql.append("crm = ' " + crm + " ' ,");
            sql.append("logradouro = ' " + logradouro + " ' ,");
            sql.append("numero = ' " + numero + " ' ,");
            sql.append("cidade = ' " + cidade + " ' ,");
            sql.append("uf = ' " + uf + " ' ,");
            sql.append("celular = ' " + celular + " ' ,");
            sql.append("fixo = ' " + fixo + " ' ,");
            sql.append("nome = ' " + nome + " ' ,");
            sql.append("WHERE id = " + id + " ; ");

            try {
                db.execSQL(sql.toString());
                Toast.makeText(getApplicationContext(), "Médico atualizado", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            Intent i = new Intent(getApplicationContext(), ListarMedicoActivity.class);
            startActivity(i);
            db.close();
        }
    }

    private void excluirBD(String id){
        db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM medico ");
        sql.append("WHERE id = " + id + ";");

        try {
            db.execSQL(sql.toString());
            Toast.makeText(getApplicationContext(),"Médico excluído com successo", Toast.LENGTH_LONG).show();
        }catch (SQLException e){
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Intent i = new Intent(getApplicationContext(), ListarMedicoActivity.class);
        startActivity(i);
        db.close();
    }
}
