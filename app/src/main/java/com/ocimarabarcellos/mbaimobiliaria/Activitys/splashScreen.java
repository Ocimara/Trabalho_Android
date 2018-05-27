package com.ocimarabarcellos.mbaimobiliaria.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ocimarabarcellos.mbaimobiliaria.R;


public class splashScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        animar();
    }

    private void animar() {

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.animacao_splash);
        anim.reset();

        ImageView iv = (ImageView) findViewById(R.id.ivLogo);
        if (iv != null) {
            iv.clearAnimation();
            iv.startAnimation(anim);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // Após o tempo definido irá executar a próxima tela
                //Intent intent = new Intent(splashScreen.this, MenuActivity.class);
                Intent intent = new Intent(splashScreen.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                splashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}
