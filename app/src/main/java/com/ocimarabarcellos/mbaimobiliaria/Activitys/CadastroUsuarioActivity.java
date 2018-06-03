package com.ocimarabarcellos.mbaimobiliaria.Activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ocimarabarcellos.mbaimobiliaria.Classes.Usuario;
import com.ocimarabarcellos.mbaimobiliaria.DAO.ConfigFireBase;
import com.ocimarabarcellos.mbaimobiliaria.R;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText edtCadEmail;
    private EditText edtCadSenha1;
    private EditText edtCadSenha2;
    private EditText edtCadNome;
    private Button btnCadastrar;
    private Button btnCancelar;
    ProgressDialog progressDialog;

    private FirebaseAuth autenticacao;
    private FirebaseDatabase database;
    private DatabaseReference referencia;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        edtCadEmail = (EditText) findViewById(R.id.edtCadEmail);
        edtCadSenha1 = (EditText) findViewById(R.id.edtCadSenha1);
        edtCadSenha2 = (EditText) findViewById(R.id.edtCadSenha2);
        edtCadNome = (EditText) findViewById(R.id.edtCadNome);

        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);

        LinearLayout CadUsu = findViewById(R.id.CadUsu);
        CadUsu.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(v.getWindowToken(), 0);

                return true;
            }
        });


        btnCadastrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuilder erroCampos = new StringBuilder();

                if (edtCadEmail.getText().toString().equals(""))
                {
                    erroCampos.append(getString(R.string.msg_campo_vazio_email));
                }
                else if (edtCadSenha1.getText().toString().equals(""))
                {
                    erroCampos.append(getString(R.string.msg_campo_vazio_senha));
                }
                else if (edtCadSenha2.getText().toString().equals(""))
                {
                    erroCampos.append(getString(R.string.msg_campo_vazio_conf_senha));
                }
                else if (edtCadNome.getText().toString().equals(""))
                {
                    erroCampos.append(getString(R.string.msg_campo_vazio_nome));
                }

                if (erroCampos.toString().equals("")) {
                    if (edtCadSenha1.getText().toString().equals(edtCadSenha2.getText().toString())) {
                        usuario = new Usuario();
                        usuario.setEmail(edtCadEmail.getText().toString());
                        usuario.setSenha(edtCadSenha1.getText().toString());
                        usuario.setNome(edtCadNome.getText().toString());

                        CadastrarUsuario();

                    } else {
                        Toast.makeText(CadastroUsuarioActivity.this, getString(R.string.error_senha_corresponde), Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(CadastroUsuarioActivity.this, erroCampos.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CadastroUsuarioActivity.this, LoginActivity.class));
                finish();

            }
        });

    }

    private void CadastrarUsuario() {
        showProgressDialog();
        autenticacao = ConfigFireBase.getFireBaseAuth();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                   if (insereUsuario(usuario))
                   {
                       usuario.setEmail(edtCadEmail.getText().toString());
                       usuario.setSenha(edtCadSenha1.getText().toString());
                       Iniciar();
                       closeProgressDialog();
                   }
                } else {
                    String erroExcecao = "";

                    try {
                        throw task.getException();

                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = getString(R.string.error_senha_caracteres);

                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = getString(R.string.error_email_invalido);

                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = getString(R.string.error_email_cadastrado);

                    } catch (Exception e) {
                        erroExcecao = getString(R.string.error_efetuar_cadastro);
                        e.printStackTrace();
                    }
                    closeProgressDialog();
                    Toast.makeText(CadastroUsuarioActivity.this, "Erro:" + erroExcecao, Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    private boolean insereUsuario(Usuario usuario) {

        try {
            referencia = ConfigFireBase.getFireBase().child("usuarios");
            referencia.push().setValue(usuario);
            Toast.makeText(CadastroUsuarioActivity.this, getString(R.string.sucesso_cadastro), Toast.LENGTH_LONG).show();
            return true;

        } catch (Exception e) {
            Toast.makeText(CadastroUsuarioActivity.this, getString(R.string.error_gravar_cadastro), Toast.LENGTH_LONG).show();
            return false;
        }

    }

    public void Iniciar() {

        startActivity(new Intent(this, PrincipalActivity.class));
        finish();
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(CadastroUsuarioActivity.this);
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