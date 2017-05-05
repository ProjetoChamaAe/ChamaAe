package chamaae.com.br;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ActivityInfEnd extends AppCompatActivity {

    Spinner  SpUF;
    EditText EdtCep,EdtCidade,EdtUF,EdtRua,EdtNro;
    Button   BtnBuscaCEP,BtnBuscaEND,BtnSalvar;

    String EstadosBR[] = {" ","AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RO", "RS", "RR", "SC", "SE", "SP", "TO"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_end);

        SpUF = (Spinner) findViewById(R.id.SpUf);

        ArrayAdapter AdaptadorSpUF = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,EstadosBR);
        SpUF.setAdapter(AdaptadorSpUF);

    }
}
