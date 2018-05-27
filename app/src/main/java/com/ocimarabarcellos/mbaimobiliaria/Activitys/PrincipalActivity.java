package com.ocimarabarcellos.mbaimobiliaria.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.ocimarabarcellos.mbaimobiliaria.Fragments.ImoveisFragment;
import com.ocimarabarcellos.mbaimobiliaria.Fragments.VersaoFragment;
import com.ocimarabarcellos.mbaimobiliaria.R;

public class PrincipalActivity extends AppCompatActivity {

    private TextView mTextMessage;


    private FirebaseAuth autenticacao;
    private FrameLayout frameLayout;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:

                    ImoveisFragment imoveisFragment = new ImoveisFragment();
                    fragmentTransaction.replace(R.id.fragment_container, imoveisFragment);
                    fragmentTransaction.commit();

                    return true;

                case R.id.navigation_dashboard:
                    VersaoFragment versaoFragment = new VersaoFragment();

                    fragmentTransaction.replace(R.id.fragment_container, versaoFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        autenticacao = FirebaseAuth.getInstance();

        frameLayout = findViewById(R.id.fragment_container);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
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

    private void  deslogarUsuario(){
        autenticacao.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();

    }

}
