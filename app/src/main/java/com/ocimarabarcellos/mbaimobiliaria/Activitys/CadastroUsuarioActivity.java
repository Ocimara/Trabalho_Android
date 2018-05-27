package com.ocimarabarcellos.mbaimobiliaria.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtCadSenha1.getText().toString().equals(edtCadSenha2.getText().toString())) {
                    usuario = new Usuario();
                    usuario.setEmail(edtCadEmail.getText().toString());
                    usuario.setSenha(edtCadSenha1.getText().toString());
                    usuario.setNome(edtCadNome.getText().toString());

                    CadastrarUsuario();

                } else {
                    Toast.makeText(CadastroUsuarioActivity.this, "As senhas não correspondem!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void CadastrarUsuario() {
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
                   }
                } else {
                    String erroExcecao = "";

                    try {
                        throw task.getException();

                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Senha inválida! Senha deverá conter no mínimo 8 caracteres com letras e numeros!";

                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "E-mail inválido!";

                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "E-mail já cadastrado!";

                    } catch (Exception e) {
                        erroExcecao = "Erro ao efetuar cadastrado!";
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroUsuarioActivity.this, "Erro:" + erroExcecao, Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    private boolean insereUsuario(Usuario usuario) {

        try {
            referencia = ConfigFireBase.getFireBase().child("usuarios");
            referencia.push().setValue(usuario);
            Toast.makeText(CadastroUsuarioActivity.this, "Usuario cadastrado com sucesso!", Toast.LENGTH_LONG).show();
            return true;

        } catch (Exception e) {
            Toast.makeText(CadastroUsuarioActivity.this, "Erro ao gravar Usuario!", Toast.LENGTH_LONG).show();
            return false;
        }

    }

    public void Iniciar() {

        startActivity(new Intent(this, PrincipalActivity.class));
        finish();
    }

}