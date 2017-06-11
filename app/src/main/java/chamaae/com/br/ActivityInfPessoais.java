package chamaae.com.br;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ActivityInfPessoais extends AppCompatActivity {

    EditText EdtNome;
    EditText EdtSenha;
    EditText EdtEmail;
    EditText EdtTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_pessoais);

        variaveis Var = new variaveis();

        EdtNome   = (EditText) findViewById(R.id.EdtNome);
        EdtSenha  = (EditText) findViewById(R.id.EdtSenha);
        EdtEmail  = (EditText) findViewById(R.id.EdtEmail);
        EdtTel    = (EditText) findViewById(R.id.EdtTel);

        Var.setEMAIL(EdtEmail.getText().toString());
        Var.setPWD(EdtSenha.getText().toString());
        Var.setNOME(EdtNome.getText().toString());
        Var.setTEL1(EdtTel.getText().toString());




    }
}
