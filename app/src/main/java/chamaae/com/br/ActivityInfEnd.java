package chamaae.com.br;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ActivityInfEnd extends AppCompatActivity {

    Spinner  SpUF;
    EditText EdtCep,EdtCidade,EdtBairro,EdtRua,EdtNro;
    Button   BtnBuscaCEP,BtnBuscaEND,BtnSalvar;
    String data;

    String EstadosBR[] = {" ","AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RO", "RS", "RR", "SC", "SE", "SP", "TO"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_end);

        BtnSalvar    = (Button)   findViewById(R.id.BtnSalvar);
        SpUF         = (Spinner)  findViewById(R.id.SpUf);
        BtnBuscaCEP  = (Button)   findViewById(R.id.BtnBuscarCEP);
        EdtCep       = (EditText) findViewById(R.id.EdtCep);

        ArrayAdapter AdaptadorSpUF = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,EstadosBR);
        SpUF.setAdapter(AdaptadorSpUF);

        BtnBuscaCEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PegaCEP pegaCEP = new PegaCEP();
                if(EdtCep.getText().toString().isEmpty()){
                 Toast.makeText(getApplicationContext(),"INFORME O CEP !",Toast.LENGTH_LONG).show();
                }else {
                    pegaCEP.execute("https://viacep.com.br/ws/" + EdtCep.getText().toString() + "/json/");
                }
            }
        });

        BtnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalvaInformacoes salva = new SalvaInformacoes();
                variaveis Var = new variaveis();
                salva.execute(Var.getNOME(),Var.getNOME(),Var.getEMAIL(),Var.getTEL1(),Var.getPWD(),"0","","");


             }
        });

    }

    public class PegaCEP extends AsyncTask<String, Void, String> {

        String data;
        String Cidade,Rua,Bairro,UF;
        ProgressDialog pg = new ProgressDialog(ActivityInfEnd.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pg.setMessage("CARREGANDO DADOS AGUARDE ...");
            pg.setCancelable(false);
            pg.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                TrataJson(PegaJson(strings[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            EdtCidade = (EditText) findViewById(R.id.EdtCidade);
            EdtRua    = (EditText) findViewById(R.id.EdtRua);
            EdtBairro = (EditText) findViewById(R.id.EdtBairro);

            EdtBairro.setText(Bairro);
            EdtRua.setText(Rua);
            EdtCidade.setText(Cidade);
            for(int i=0; i<EstadosBR.length;i++){
                if(EstadosBR[i].equals(UF)){
                    Log.i("UF",EstadosBR[i]);
                    SpUF.setSelection(i);
                }
            }

            pg.dismiss();

        }
        private void TrataJson(String json){

            try {
                JSONObject jsonObject = new JSONObject(json);
                Cidade = jsonObject.getString("localidade");
                Rua    = jsonObject.getString("logradouro");
                Bairro = jsonObject.getString("bairro");
                UF     = jsonObject.getString("uf");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class SalvaInformacoes extends AsyncTask<String, Void, String>{

        ProgressDialog pg = new ProgressDialog(ActivityInfEnd.this);
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
                PegaJson(Var.getBASE_API()+Var.getINSERE_USUARIO()+params[0]+","+params[1]+","+","+params[2]+","+params[3]+","+params[4]+","+params[5]+","+params[6]+","+params[7]);
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
