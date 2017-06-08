package chamaae.com.br;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
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

import java.util.Arrays;

import static com.facebook.Profile.getCurrentProfile;

public class ActivityLogin extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    LoginButton   loginButton;
    SignInButton  loginButtonGoogle;
    Button      BtnCadastro,BtnLogin;
    ImageButton ImgBtnFace;
    EditText    EdtLogin,EdtSenha;


    String TipoLogin;

    private GoogleApiClient ApiClient;

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.toolbar));
        //getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("");

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButtonGoogle = (SignInButton) findViewById(R.id.signin);
        BtnLogin    = (Button)      findViewById(R.id.BtnLogin);
        BtnCadastro = (Button)      findViewById(R.id.BtnCadastro);

        BtnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ChamaCad = new Intent(ActivityLogin.this,ActivityCadastroPrincipal.class);
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
                            String Nome  = object.getString("name");
                            String Email = object.getString("email");
                            Log.i("JSON_face",object.toString());
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
            public void onClick(View view) {
                TipoLogin = "FACEBOOK";
            }
        });

        //LGGIN GOOGLE
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
            Log.i("NOME LOGIN",conta.getDisplayName());
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
            Log.i("JA LOGOU","LOGADO");
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

}
