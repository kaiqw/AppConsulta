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

public class EditarPacienteActivity extends AppCompatActivity {
    SQLiteDatabase db;
    EditText etNome;
    Spinner spGpSangue;
    EditText etLogradouro;
    EditText etNumero;
    EditText etCidade;
    Spinner spUf;
    EditText etCelular;
    EditText etFixo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_paciente);

        etNome = findViewById(R.id.etNomePaciente);
        spGpSangue = findViewById(R.id.spTipoSangue);
        etLogradouro = findViewById(R.id.etLogradouroPac);
        etNumero = findViewById(R.id.etNumeroPac);
        etCidade = findViewById(R.id.etCidadePac);
        spUf = findViewById(R.id.spUfPac);
        etCelular = findViewById(R.id.etCelularPac);
        etFixo = findViewById(R.id.etFixoPac);

        String[] opGrupoSang = new String[]{
                "- Escolha uma opção -",
                "A+", "A-", "B+", "B-",
                "AB+", "AB-", "O+", "O-"

        };
        ArrayAdapter<String> gpGSangArrayAdap =
                new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, opGrupoSang);
        spGpSangue.setAdapter(gpGSangArrayAdap);

        String[] spUFpac = new String[] {
                "- SELECIONE -",
                "RO", "AC", "AM", "RR", "PA",
                "AP", "TO", "MA", "PI", "CE",
                "RN", "PB", "PE", "AL", "SE",
                "BA", "MG", "ES", "RJ", "SP",
                "PR", "SC", "RS", "MS", "MT",
                "GO", "DF"
        };
        ArrayAdapter<String> spArrayAdapUFpac =
                new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, spUFpac);
        spUf.setAdapter(spArrayAdapUFpac);

        Intent valores = getIntent();
        etNome.setText(valores.getStringExtra("nome"));
        String gpSangueExtra = valores.getStringExtra("grp_sanguineo");
        etLogradouro.setText(valores.getStringExtra("logradouro"));
        etNumero.setText(valores.getStringExtra("numero"));
        etCidade.setText(valores.getStringExtra("cidade"));
        String ufExtra = valores.getStringExtra("uf");
        etCelular.setText(valores.getStringExtra("celular"));
        etFixo.setText(valores.getStringExtra("fixo"));

        int aux = 0;
        for (String gp: opGrupoSang){
            if(gp.equals(gpSangueExtra)){
                break;
            }
            aux++;
        }
        spGpSangue.setSelection(aux);

        int aux2 = 0;
        for (String uf: spUFpac){
            if(uf.equals(ufExtra)){
                break;
            }
            aux2++;
        }
        spUf.setSelection(aux2);

        final String id = valores.getStringExtra("id");

        Button clickEditarPac = findViewById(R.id.btnEditarPac);
        clickEditarPac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarBD(id);
            }
        });

        Button clickExcluirPac = findViewById(R.id.btnExcluirPac);
        clickExcluirPac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirBD(id);
            }
        });
    }

    private void salvarBD(String id){
        String nome = etNome.getText().toString().trim();
        String gpSangue = spGpSangue.getSelectedItem().toString();
        String logradouro = etLogradouro.getText().toString().trim();
        String numero = etNumero.getText().toString().trim();
        String cidade = etCidade.getText().toString().trim();
        String uf = spUf.getSelectedItem().toString();
        String celular = etCelular.getText().toString().trim();
        String fixo = etFixo.getText().toString().trim();

        if(nome.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, informe o Nome!", Toast.LENGTH_LONG).show();
        }else if (gpSangue.equals("- Escolha uma opção -")){
            Toast.makeText(getApplicationContext(), "Por favor, Selecione o Grupo Sanguíneo!", Toast.LENGTH_LONG).show();
        }else if (logradouro.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, Informe o Logradouro!", Toast.LENGTH_LONG).show();
        }else if (numero.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, Informe o Número!", Toast.LENGTH_LONG).show();
        }else if (cidade.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, Informe a Cidade!", Toast.LENGTH_LONG).show();
        }else if (uf.equals("- SELECIONE -")){
            Toast.makeText(getApplicationContext(), "Por favor, Selecione a UF!", Toast.LENGTH_LONG).show();
        }else if (celular.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, Informe o Celular!", Toast.LENGTH_LONG).show();
        }else if (fixo.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, Informe o Telefone Fixo!", Toast.LENGTH_LONG).show();
        }else {
            db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
            StringBuilder sql = new StringBuilder();

            sql.append("UPDATE aluno SET ");
            sql.append("nome= ' " + nome + " ' , ");
            sql.append("grp_sanguineo= ' " + gpSangue + " ' , ");
            sql.append("logradouro= ' " + logradouro + " ' , ");
            sql.append("numero= ' " + numero + " ' , ");
            sql.append("cidade= ' " + cidade + " ' , ");
            sql.append("uf= ' " + uf + " ' , ");
            sql.append("celular= ' " + celular + " ' , ");
            sql.append("fixo= ' " + fixo + " ' , ");
            sql.append("WHERE id = " + id + " ; ");

            try {
                db.execSQL(sql.toString());
                Toast.makeText(getApplicationContext(), "Paciente atualizado", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            Intent i = new Intent(getApplicationContext(), ListarPacienteActivity.class);
            startActivity(i);
            db.close();
        }
    }

    private void excluirBD(String id){
        db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM paciente");
        sql.append("WHERE id= " +id+ ";");

        try {
            db.execSQL(sql.toString());
            Toast.makeText(getApplicationContext(), "Paciente excluído", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Intent i = new Intent(getApplicationContext(), ListarPacienteActivity.class);
        startActivity(i);
        db.close();
    }
}
