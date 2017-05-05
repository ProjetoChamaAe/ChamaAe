package chamaae.com.br;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class ActivityCadastroPrincipal extends AppCompatActivity {

    TabHost Abas;
    LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_principal);

        mLocalActivityManager.dispatchCreate(savedInstanceState);

        Abas = (TabHost) findViewById(R.id.Abas);
        Abas.setup(mLocalActivityManager);

        //CRIANDO ABAS
        TabHost.TabSpec InfPessoal  = Abas.newTabSpec("GERAIS");
        TabHost.TabSpec InfGerais   = Abas.newTabSpec("ENDEREÇO");

        //NOME DAS ABAS E PASSANDO CONTEUDO
        InfPessoal.setIndicator("GERAIS").setContent(new Intent().setClass(this,ActivityInfPessoais.class));
        InfGerais.setIndicator("ENDEREÇO").setContent(new Intent().setClass(this,ActivityInfEnd.class));

        //ADICIONANDO ABAS AO MENU
        Abas.addTab(InfPessoal);
        Abas.addTab(InfGerais);

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
        mLocalActivityManager.dispatchStop ();
        super.onStop ();
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        mLocalActivityManager.saveInstanceState ();

    }


}
