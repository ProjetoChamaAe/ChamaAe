package chamaae.com.br;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ActivityInfPessoais extends AppCompatActivity {

    EditText EdtNome;
    EditText EdtSenha,EdtConfirmaSenha;
    EditText EdtEmail;
    EditText EdtTel;
    Button BtnSalvar;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_pessoais);

        EdtNome   = (EditText) findViewById(R.id.EdtNome);
        EdtSenha  = (EditText) findViewById(R.id.EdtSenha);
        EdtEmail  = (EditText) findViewById(R.id.EdtEmail);
        EdtTel    = (EditText) findViewById(R.id.EdtTel);
        EdtConfirmaSenha = (EditText) findViewById(R.id.EdtConfimaSenha);
        BtnSalvar = (Button)   findViewById(R.id.BtnSalvarCli);

        BtnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalvaInformacoes salva = new SalvaInformacoes();

                if(!EdtSenha.getText().toString().equals(EdtConfirmaSenha.getText().toString())){
                    Toast.makeText(getApplicationContext(),"AS SENHAS NAO CONFEREM !",Toast.LENGTH_LONG).show();
                }else {
                    salva.execute(EdtNome.getText().toString(),EdtEmail.getText().toString(), EdtTel.getText().toString(), EdtSenha.getText().toString(), "0", "", "");

                }
            }
        });
    }


    public class SalvaInformacoes extends AsyncTask<String, Void, String> {

        ProgressDialog pg = new ProgressDialog(ActivityInfPessoais.this);
        variaveis Var = new variaveis();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pg.setCancelable(false);
            pg.setMessage("GRAVANDO INFORMAÇÕES AGUARDE ...");
            pg.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {
                PegaJson(Var.getBASE_API()+Var.getINSERE_USUARIO()+params[0]+"/"+params[1]+"/"+params[2]+"/"+params[3]+"/"+params[4]+"/"+params[5]+"/"+params[6]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pg.dismiss();
        }

    }

    private String PegaJson(String lnk) throws IOException {
        data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(lnk);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            Log.d("Error while downloading", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }

        Log.i("JSON",data);
        return data;

    }



}
