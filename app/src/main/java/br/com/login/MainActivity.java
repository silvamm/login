package br.com.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    SignInButton btnGoogle;
    FirebaseAuth mAuth;
    GoogleSignInApi mGoogleSignInClient;
    private final int RC_SIGN_IN = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btEntrar = findViewById(R.id.btEntrar);
        btnGoogle = findViewById(R.id.btnGoogle);

        btEntrar.setOnClickListener(b ->
        {
            EditText inputEmail = findViewById(R.id.inputUsuario);
            EditText inputSenha = findViewById(R.id.inputSenha);

            efetuarLogin(inputEmail, inputSenha);

        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = (GoogleSignInApi) GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


//
//    private void signIn() {
//
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }


    public void efetuarLogin(EditText inputEmail, EditText inputSenha){

        inputEmail.setError(null);
        inputSenha.setError(null);

        String email = inputEmail.getText().toString();
        String senha = inputSenha.getText().toString();

        boolean temErro = false;
        View campoComErro = null;

        if (TextUtils.isEmpty(senha)) {
            inputSenha.setError(getString(R.string.campoObrigatorio));
            campoComErro = inputSenha;
            temErro = true;
        }

        if (TextUtils.isEmpty(email)) {
            inputEmail.setError(getString(R.string.campoObrigatorio));
            campoComErro = inputEmail;
            temErro = true;
        } else if (!emailValido(email)) {
            inputEmail.setError(getString(R.string.emailInvalido));
            campoComErro = inputEmail;
            temErro = true;
        }

        if (temErro) {
            campoComErro.requestFocus();
        } else {
            verificarCredenciais(email, senha);

        }


    }

    private void verificarCredenciais(String email, String senha){

        if(email.equals("admin@stos.mobi") && senha.equals("123456")){
                       alert("Logado com sucesso");
        }else{
            alert("E-mail ou senha incorretos");
        }
    }

    private boolean emailValido(String email) {
        boolean temPontoDepoisDoArroba = false;
        boolean temArroba = email.contains("@");
        if(temArroba){
            String depoisDoArroba = email.substring(email.lastIndexOf("@"));
            temPontoDepoisDoArroba = depoisDoArroba.contains(".");
        }
        return temArroba && temPontoDepoisDoArroba;
    }

    private void alert(String mensagem){
        Toast toast = Toast.makeText(this, mensagem, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0,0);
        toast.show();

    }

}
