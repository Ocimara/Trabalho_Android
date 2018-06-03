package com.ocimarabarcellos.mbaimobiliaria.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtLogin =  (EditText) findViewById(R.id.edtEmail);
        edtSenha =  (EditText) findViewById(R.id.edtSenha);
        btnLogin = (Button) findViewById(R.id.btnLogin);



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
                        validaLogin();
                    } else {
                        Toast.makeText(LoginActivity.this, getString(R.string.msg_email_senha), Toast.LENGTH_LONG).show();
                    }

                }

            });
        }



    }


    private void validaLogin(){
        showProgressDialog();
        autenticacao = ConfigFireBase.getFireBaseAuth();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail().toString(), usuario.getSenha().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Iniciar();
                    Toast.makeText(LoginActivity.this, getString(R.string.sucesso_login), Toast.LENGTH_LONG).show();
                    closeProgressDialog();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, getString(R.string.error_usu_senha), Toast.LENGTH_LONG).show();
                    closeProgressDialog();

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

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Aguarde...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }


    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }




}
