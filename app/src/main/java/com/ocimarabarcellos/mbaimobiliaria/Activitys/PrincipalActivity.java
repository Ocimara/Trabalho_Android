package com.ocimarabarcellos.mbaimobiliaria.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.ocimarabarcellos.mbaimobiliaria.Classes.Imovel;
import com.ocimarabarcellos.mbaimobiliaria.DAO.ImovelDAO;
import com.ocimarabarcellos.mbaimobiliaria.Fragments.ImoveisFragment;
import com.ocimarabarcellos.mbaimobiliaria.Fragments.VersaoFragment;
import com.ocimarabarcellos.mbaimobiliaria.R;

import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private FrameLayout frameLayout;
    private List<Imovel> lstImoveis = new ArrayList<>();



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_imoveis:

                    ImoveisFragment imoveisFragment = new ImoveisFragment();
                    fragmentTransaction.replace(R.id.fragment_container, imoveisFragment);
                    fragmentTransaction.commit();

                    return true;

                case R.id.navigation_versao:
                    VersaoFragment versaoFragment = new VersaoFragment();

                    fragmentTransaction.replace(R.id.fragment_container, versaoFragment);
                    fragmentTransaction.commit();

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        autenticacao = FirebaseAuth.getInstance();

        frameLayout = findViewById(R.id.fragment_container);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ImoveisFragment imoveisFragment = new ImoveisFragment();
        fragmentTransaction.replace(R.id.fragment_container, imoveisFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        carregarImoveis();

        MenuItem item = (MenuItem) menu.findItem(R.id.action_share);
        ShareActionProvider shareAction = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        Intent shareIntent = new Intent(Intent.ACTION_SEND)
                .putExtra(Intent.EXTRA_TEXT, carregarImoveis())
                .setType("text/plain");
        shareAction.setShareIntent(shareIntent);





        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_sair) {
            deslogarUsuario();
        }

        return super.onOptionsItemSelected(item);
    }

    private void deslogarUsuario() {
        autenticacao.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();

    }

    public String carregarImoveis(){

        StringBuilder strImov = new StringBuilder();

        //Listar Imoveis
        ImovelDAO imovelDAO = new ImovelDAO(getApplicationContext());
        lstImoveis = imovelDAO.listar();

        strImov.append(getString(R.string.app_name) + " \n");
        strImov.append("---------------------------------\n");

        for(int i = 0; i < lstImoveis.size(); i++)
        {
            Log.i("Imovel" + i,lstImoveis.get(i).getDsCidade());
            strImov.append(lstImoveis.get(i).getDsCidade() + " - " + lstImoveis.get(i).getDsUF() + "\n");
            strImov.append(lstImoveis.get(i).getDsEndereco()+ "\n");
            strImov.append(lstImoveis.get(i).getDsImovel()+ "\n");
            strImov.append("---------------------------------\n");
        }

        return strImov.toString();

    }

}
