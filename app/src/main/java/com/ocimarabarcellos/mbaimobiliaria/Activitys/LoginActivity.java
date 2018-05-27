package com.ocimarabarcellos.mbaimobiliaria.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ocimarabarcellos.mbaimobiliaria.Classes.Usuario;
import com.ocimarabarcellos.mbaimobiliaria.DAO.ConfigFireBase;
import com.ocimarabarcellos.mbaimobiliaria.R;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private EditText edtLogin;
    private EditText edtSenha;
    private Button btnLogin;
    private CheckBox cbManterConectado;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLogin =  (EditText) findViewById(R.id.edtEmail);
        edtSenha =  (EditText) findViewById(R.id.edtSenha);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        cbManterConectado = (CheckBox) findViewById(R.id.cbManterConectado);


        if (usuarioLogado()) {
            Iniciar();
        }
        else {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!edtLogin.getText().toString().equals("") && !edtSenha.getText().toString().equals("")) {
                        usuario = new Usuario();

                        usuario.setEmail(edtLogin.getText().toString());
                        usuario.setSenha(edtSenha.getText().toString());
                        usuario.setConectado(cbManterConectado.isChecked());
                        validaLogin();

                    } else {
                        Toast.makeText(LoginActivity.this, "Preencha os campos de E-mail e Senha", Toast.LENGTH_LONG).show();
                    }
                }

            });
        }

    }

    private void validaLogin(){
        autenticacao = ConfigFireBase.getFireBaseAuth();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail().toString(), usuario.getSenha().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    //if (cbManterConectado.isChecked()) {
                      //  Preferencias pref = new Preferencias(LoginActivity.this);
                       // pref.salvarUsuarioPreferencias(usuario.getEmail(), usuario.setSenha());
                    //}

                    Iniciar();



                    Toast.makeText(LoginActivity.this, "Login efetuado com sucesso!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Usuário ou Senha inválidos! Tente novamente!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void Iniciar() {

        startActivity(new Intent(this, PrincipalActivity.class));
        finish();
    }


    public void Cadastre(View v) {

        startActivity(new Intent(this, CadastroUsuarioActivity.class));
        finish();
    }

    public Boolean usuarioLogado() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            return true;
        }
        else {

            return false;
        }

    }


}
