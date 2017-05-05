package chamaae.com.br;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ActivityLogin extends AppCompatActivity {

    Button      BtnCadastro,BtnLogin;
    ImageButton ImgBtnFace;
    EditText    EdtLogin,EdtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BtnLogin    = (Button)      findViewById(R.id.BtnLogin);
        BtnCadastro = (Button)      findViewById(R.id.BtnCadastro);
        ImgBtnFace  = (ImageButton) findViewById(R.id.ImgBtnFace);

        BtnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ChamaCad = new Intent(ActivityLogin.this,ActivityCadastroPrincipal.class);
                startActivity(ChamaCad);
            }
        });


    }


}
