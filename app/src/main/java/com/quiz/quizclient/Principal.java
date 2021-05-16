package com.quiz.quizclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import br.com.bloder.magic.view.MagicButton;

public class Principal extends AppCompatActivity {

    MagicButton btngoogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        btngoogle = (MagicButton) findViewById(R.id.mgbtn);
        btngoogle.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    public void InicioSesion(View view){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }
    public void Registro(View view){
        Intent i = new Intent(this, Registro.class);
        startActivity(i);
    }

}