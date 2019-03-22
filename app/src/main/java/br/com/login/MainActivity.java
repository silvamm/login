package br.com.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btEntrar = findViewById(R.id.btEntrar);

        btEntrar.setOnClickListener(b ->
        {
            EditText inputEmail = findViewById(R.id.inputUsuario);
            EditText inputSenha = findViewById(R.id.inputSenha);

            efetuarLogin(inputEmail, inputSenha);

        });

    }


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
