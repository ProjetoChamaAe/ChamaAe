package chamaae.com.br;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Normalizer;

public class ActivityCadastroPrincipal extends AppCompatActivity {

    TabHost Abas;
    EditText EdtNome;
    EditText EdtEmail;
    EditText EdtCPFCNPJ;
    EditText EdtTel,EdtTel2;
    RadioGroup rgTipo;
    RadioButton rbTipo;
    Spinner SpUF;
    EditText EdtCep,EdtCidade,EdtBairro,EdtRua,EdtNro,EdtComp;
    Button BtnBuscaCEP,BtnBuscaEND,BtnSalvar;
    String data,Cidade,Rua,Bairro,UF,Nro,CEP;
    Integer RbSel;
    AlertDialog alerta;

    String EstadosBR[] = {" ","AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RO", "RS", "RR", "SC", "SE", "SP", "TO"};
    LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_principal);

        mLocalActivityManager.dispatchCreate(savedInstanceState);

        Abas = (TabHost) findViewById(R.id.Abas);
        EdtNome   = (EditText) findViewById(R.id.EdtNome);
        EdtEmail  = (EditText) findViewById(R.id.EdtEmail);
        EdtTel    = (EditText) findViewById(R.id.EdtTel);
        EdtTel2    = (EditText) findViewById(R.id.EdtTel2);
        EdtCPFCNPJ = (EditText) findViewById(R.id.EdtCNJPCPF);

        EdtComp    = (EditText) findViewById(R.id.EdtComp);
        EdtCep       = (EditText) findViewById(R.id.EdtCep);
        EdtCidade = (EditText) findViewById(R.id.EdtCidade);
        EdtRua    = (EditText) findViewById(R.id.EdtRua);
        EdtBairro = (EditText) findViewById(R.id.EdtBairro);
        EdtNro    = (EditText) findViewById(R.id.EdtNro);

        rgTipo     = (RadioGroup) findViewById(R.id.RgTipo);
        BtnSalvar    = (Button)   findViewById(R.id.BtnSalvar);
        SpUF         = (Spinner)  findViewById(R.id.SpUf);
        BtnBuscaCEP  = (Button)   findViewById(R.id.BtnBuscarCEP);



        ArrayAdapter AdaptadorSpUF = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,EstadosBR);
        SpUF.setAdapter(AdaptadorSpUF);

        variaveis Var = new variaveis();

        Abas.setup(mLocalActivityManager);

        //CRIANDO ABAS
        TabHost.TabSpec InfPessoal  = Abas.newTabSpec(getApplicationContext().getString(R.string.TxtGerais));
        TabHost.TabSpec InfGerais   = Abas.newTabSpec(getApplicationContext().getString(R.string.TxtEndereço));

        //NOME DAS ABAS E PASSANDO CONTEUDO
        InfPessoal.setIndicator(getApplicationContext().getString(R.string.TxtGerais)).setContent(R.id.gerais);
        InfGerais.setIndicator(getApplicationContext().getString(R.string.TxtEndereço)).setContent(R.id.endereco);

        //ADICIONANDO ABAS AO MENU
        Abas.addTab(InfPessoal);
        Abas.addTab(InfGerais);

        EdtNome.requestFocus();
        EdtEmail.setEnabled(false);

        EdtNome.setText(Var.getNOME());
        EdtEmail.setText(Var.getEMAIL());
        EdtTel.setText(Var.getTEL1());
        EdtTel2.setText(Var.getTEL2());
        EdtCPFCNPJ.setText(Var.getCPFCNPJ());

        EdtComp.setText(Var.getCOMPLEMETO());
        EdtNro.setText(Var.getNUMERO());
        EdtBairro.setText(Var.getBAIRRO());
        EdtCep.setText(Var.getCEP());
        EdtCidade.setText(Var.getCIDADE());
        EdtRua.setText(Var.getRUA());

        for(int i=0; i<EstadosBR.length;i++){
            if(EstadosBR[i].equals(Var.getUF())){
                Log.i("UF",EstadosBR[i]);
                SpUF.setSelection(i);
            }
        }

        if(Var.getPRESTADOR().equals("0")){
            rgTipo.check(R.id.contratante);
        }else{
            rgTipo.check(R.id.prestador);
        }


        Var.setTEL1(EdtTel.getText().toString());
        Var.setTEL2(EdtTel2.getText().toString());


        BtnBuscaCEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PegaCEP pegaCEP = new PegaCEP();
                if(EdtCep.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),getApplicationContext().getString(R.string.MsgInformaDados),Toast.LENGTH_LONG).show();
                }else {
                    pegaCEP.execute("https://viacep.com.br/ws/" + EdtCep.getText().toString() + "/json/");
                }
            }
        });

        BtnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalvaInformacoes Si = new SalvaInformacoes();
                variaveis Var = new variaveis();
                int position;

                rgTipo     = (RadioGroup) findViewById(R.id.RgTipo);
                SpUF         = (Spinner)  findViewById(R.id.SpUf);

                int RbSel = rgTipo.getCheckedRadioButtonId();
                View radioButton = rgTipo.findViewById(RbSel);
                position = rgTipo.indexOfChild(radioButton);

                Log.i("ID",String.valueOf(String.valueOf(SpUF.getSelectedItem())));


                Si.execute(Var.getID_USER().replace("\"",""),EdtNome.getText().toString(),EdtCPFCNPJ.getText().toString(),
                           EdtEmail.getText().toString(),"null",EdtTel.getText().toString(),EdtTel2.getText().toString(),"null",String.valueOf(position),
                           EdtBairro.getText().toString(),EdtCidade.getText().toString(),EdtNro.getText().toString(),String.valueOf(SpUF.getSelectedItem()),EdtCep.getText().toString(),
                           EdtComp.getText().toString(),EdtRua.getText().toString());

            }
        });


    }

    @Override
    protected void onResume () {
        mLocalActivityManager.dispatchResume();
        super.onResume ();
    }

    @Override
    protected void onPause () {
        mLocalActivityManager.dispatchPause(isFinishing());
        super.onPause ();
    }

    @Override
    protected void onStop () {
        mLocalActivityManager.dispatchStop();
        super.onStop ();
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        mLocalActivityManager.saveInstanceState();

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

    public class PegaCEP extends AsyncTask<String, Void, String> {

        String data;
        ProgressDialog pg = new ProgressDialog(ActivityCadastroPrincipal.this);

        public String removerAcentos(String str) {
            return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pg.setMessage(getApplicationContext().getString(R.string.MsgCarregandoDados));
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
            EdtCep    = (EditText) findViewById(R.id.EdtCep);

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
                Cidade = removerAcentos(jsonObject.getString("localidade"));
                Rua    = removerAcentos(jsonObject.getString("logradouro"));
                Bairro = removerAcentos(jsonObject.getString("bairro"));
                UF     = removerAcentos(jsonObject.getString("uf"));
                CEP    = removerAcentos(jsonObject.getString("cep"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class SalvaInformacoes extends AsyncTask<String, Void, String>{

        ProgressDialog pg = new ProgressDialog(ActivityCadastroPrincipal.this);
        variaveis Var = new variaveis();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pg.setCancelable(false);
            pg.setMessage(getApplicationContext().getString(R.string.MsgGravaDados));
            pg.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {
                Log.i("LINK",(Var.getBASE_API()+Var.getALTERA_USUARIO()+params[0]+"/"+params[1]+"/"+params[2]+"/"+params[3]+"/"+params[4]+"/"+params[5]+"/"+params[6]+"/"+params[7]+"/"+params[8]+"/"+params[9]
                        +"/"+params[10]+"/"+params[11]+"/"+params[12]+"/"+params[13]+"/"+params[14]+"/"+params[15]));
                PegaJson(Var.getBASE_API()+Var.getALTERA_USUARIO()+params[0]+"/"+params[1]+"/"+params[2]+"/"+params[3]+"/"+params[4]+"/"+params[5]+"/"+params[6]+"/"+params[7]+"/"+params[8]+"/"+params[9]
                        +"/"+params[10]+"/"+params[11]+"/"+params[12]+"/"+params[13]+"/"+params[14]+"/"+params[15]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pg.dismiss();

            if(data.replace("\"","").equals("Gravado")){
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCadastroPrincipal.this);
                builder.setMessage(R.string.MsgRegistroGravado);
                builder.setIcon(RESULT_OK);
                alerta = builder.create();
                alerta.show();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCadastroPrincipal.this);
                builder.setMessage(data.replace("\"",""));
                builder.setIcon(RESULT_CANCELED);
                alerta = builder.create();
                alerta.show();
            }




        }

    }



}
