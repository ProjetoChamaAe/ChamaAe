package chamaae.com.br;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResolvingResultCallbacks;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import static com.facebook.Profile.getCurrentProfile;

public class ActivityLogin extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    LoginButton   loginButton;
    SignInButton  loginButtonGoogle;
    Button      BtnCadastro,BtnLogin;
    ImageButton ImgBtnFace;
    EditText    EdtLogin,EdtSenha;

    String TipoLogin,data,NomeF,EmailF,IdF;

    private GoogleApiClient ApiClient;

    CallbackManager callbackManager;
    SharedPreferences.Editor editor;
    SharedPreferences config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.toolbar));
        //getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("");

        editor = getSharedPreferences("CONFIG", MODE_PRIVATE).edit();
        config = getSharedPreferences("CONFIG", MODE_PRIVATE);

        TipoLogin = config.getString("TIPO_LOGIN",null);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButtonGoogle = (SignInButton) findViewById(R.id.signin);
        BtnLogin    = (Button)      findViewById(R.id.BtnLogin);
        BtnCadastro = (Button)      findViewById(R.id.BtnCadastro);
        EdtLogin    = (EditText)  findViewById(R.id.EdtLogin);
        EdtSenha    = (EditText)  findViewById(R.id.EdtSenha);

        EdtLogin.requestFocus();

        BtnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent ChamaCad = new Intent(ActivityLogin.this,ActivityCadastroPrincipal.class);
                Intent ChamaCad = new Intent(ActivityLogin.this,ActivityInfPessoais.class);
                startActivity(ChamaCad);
            }
        });

        //LOGIN FACEBOOK
        loginButton.setReadPermissions(Arrays.asList("public_profile","email", "user_birthday","user_location"));
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager,new FacebookCallback<LoginResult>(){
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("LOGIN_FACE",loginResult.toString());
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),new GraphRequest.GraphJSONObjectCallback(){
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            NomeF  = object.getString("name");
                            EmailF = object.getString("email");
                            IdF    = object.getString("id");
                            Log.i("JSON_face",object.toString());

                            TipoLogin = "FACEBOOK";
                            Login login = new Login();
                            login.execute(IdF,NomeF.toUpperCase(),EmailF);

                            editor.putString("TIPO_LOGIN","FACEBOOK");
                            editor.commit();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,location");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.i("ERRO_FACE",error.toString());
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();

            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TipoLogin = "FACEBOOK";
            }
        });

        //LOGIN GOOGLE
        GoogleSignInOptions LoginOpcoes = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail().build();
        ApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,LoginOpcoes).build();
        loginButtonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(ApiClient);
                startActivityForResult(signInIntent, 1);
                TipoLogin = "GOOGLE";
                editor.putString("TIPO_LOGIN","GOOGLE");
                editor.commit();
            }
        });

        //LOGIN NORMAL
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login login = new Login();
                if(EdtLogin.getText().toString().isEmpty() || EdtSenha.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"INFORME OS DADOS DO SEU LOGIN !",Toast.LENGTH_SHORT).show();
                }else{
                    login.execute(EdtLogin.getText().toString(), EdtLogin.getText().toString(), EdtSenha.getText().toString());
                    TipoLogin = "NORMAL";
                    editor.putString("TIPO_LOGIN","NORMAL");
                    editor.commit();
                }
            }
        });

    }

    public void StatusLoginGoogle (GoogleSignInResult resultado){
        GoogleLogin Inf = new GoogleLogin();
        Log.i("RESULTADO", String.valueOf(resultado.getStatus()));
        if(resultado.isSuccess()){
            GoogleSignInAccount conta = resultado.getSignInAccount();
            Inf.setNome(conta.getDisplayName());
            Inf.setEmail(conta.getEmail());
            Inf.setToken(conta.getIdToken());
            Inf.setFoto(conta.getPhotoUrl());
            Inf.setId(conta.getId());

            Login log = new Login();
            log.execute(Inf.getId(),Inf.getToken(),Inf.getNome().toUpperCase(),Inf.getEmail());

            Log.i("ID",Inf.getId());

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(),"ERRO : "+ connectionResult.getErrorMessage()+"\n"+"CODIGO DO ERRO : "+String.valueOf(connectionResult.getErrorCode()),Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> login = Auth.GoogleSignInApi.silentSignIn(ApiClient);
        if(login.isDone()){
            GoogleSignInResult resultado = login.get();
            StatusLoginGoogle(resultado);
            Log.i("JA LOGOU","LOGADO");//EXECUTAR SELECT API

        }else{
            login.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    StatusLoginGoogle(googleSignInResult);
                    Log.i("NAO LOGOU","NAO LOGADO");
                }
            } );
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(TipoLogin.equals("GOOGLE")){
            if (requestCode == 1) {
                GoogleSignInResult resultado = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                StatusLoginGoogle(resultado);
            }
        }else if(TipoLogin.equals("FACEBOOK")){
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ApiClient.stopAutoManage(this);
        ApiClient.disconnect();
    }

    public class Login extends AsyncTask<String, Void, String> {

        ProgressDialog pg = new ProgressDialog(ActivityLogin.this);
        variaveis Var = new variaveis();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pg.setCancelable(false);
            pg.setMessage("FAZENDO LOGIN AGUARDE ...");
            pg.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if(TipoLogin.equals("NORMAL")) {
                    PegaJson(Var.getBASE_API() + Var.getLOGIN_USUARIO() + params[0].toUpperCase() + "/" + params[1] + "/" + params[2]);
                }else if(TipoLogin.equals("GOOGLE")){
                    PegaJson(Var.getBASE_API() + Var.getLOGIN_USUARIO_GOOGLE() + params[0] + "/" + params[1] + "/" + params[2] + "/" + params[3]);
                }else{
                    PegaJson(Var.getBASE_API() + Var.getLOGIN_USUARIO_FACEBOOK() + params[0] + "/" + params[1] + "/" + params[2]);
                }
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
