package com.example.tarea_semana3_joseph;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    LinearLayout btnPrograma;
    LinearLayout btnFragmentos;
    LinearLayout btnGestionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnPrograma = findViewById(R.id.btnPrograma);
        btnFragmentos = findViewById(R.id.btnFragmentos);

        btnPrograma.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    ProgramaActivity.class
            );

            startActivity(intent);
        });

        btnGestionar = findViewById(R.id.btnGestionar);

        btnGestionar.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    GestionarProgramaActivity.class
            );

            startActivity(intent);
        });


        btnFragmentos.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    FragmentoActivity.class
            );

            startActivity(intent);
        });
    }
}