package com.example.tarea_semana3_joseph;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProgramaActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    LinearLayout contenedorEventos;
    TextView btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_programa);

        contenedorEventos = findViewById(R.id.contenedorEventos);
        btnVolver = findViewById(R.id.btnVolver);

        databaseHelper = new DatabaseHelper(this);

        // BOTÓN VOLVER
        btnVolver.setOnClickListener(v -> {
            finish();
        });

        mostrarEventos();
    }


    private void mostrarEventos() {

        Cursor cursor = databaseHelper.obtenerEventos();

        while (cursor.moveToNext()) {

            String dia = cursor.getString(
                    cursor.getColumnIndexOrThrow("dia")
            );

            String hora = cursor.getString(
                    cursor.getColumnIndexOrThrow("hora")
            );

            String nombre = cursor.getString(
                    cursor.getColumnIndexOrThrow("nombre")
            );

            String lugar = cursor.getString(
                    cursor.getColumnIndexOrThrow("lugar")
            );


            // TARJETA PRINCIPAL

            LinearLayout tarjeta = new LinearLayout(this);

            tarjeta.setOrientation(LinearLayout.VERTICAL);
            tarjeta.setPadding(20, 20, 20, 20);
            tarjeta.setBackgroundResource(R.drawable.evento_card);
            tarjeta.setElevation(4);

            LinearLayout.LayoutParams parametrosTarjeta =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

            parametrosTarjeta.setMargins(0, 0, 0, 18);

            tarjeta.setLayoutParams(parametrosTarjeta);


            // FILA SUPERIOR

            LinearLayout filaSuperior = new LinearLayout(this);

            filaSuperior.setOrientation(LinearLayout.HORIZONTAL);
            filaSuperior.setGravity(Gravity.CENTER_VERTICAL);


            // DÍA

            TextView textoDia = new TextView(this);

            textoDia.setText(dia.toUpperCase());
            textoDia.setTextColor(
                    getResources().getColor(R.color.azul_oscuro)
            );
            textoDia.setTextSize(15);
            textoDia.setTypeface(null, Typeface.BOLD);

            LinearLayout.LayoutParams parametrosDia =
                    new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1
                    );

            textoDia.setLayoutParams(parametrosDia);


            // HORA

            TextView textoHora = new TextView(this);

            textoHora.setText("🕐 " + hora);
            textoHora.setTextColor(
                    getResources().getColor(R.color.azul_principal)
            );
            textoHora.setTextSize(15);
            textoHora.setTypeface(null, Typeface.BOLD);
            textoHora.setGravity(Gravity.END);


            filaSuperior.addView(textoDia);
            filaSuperior.addView(textoHora);


            // NOMBRE

            TextView textoNombre = new TextView(this);

            textoNombre.setText(nombre);
            textoNombre.setTextColor(
                    getResources().getColor(R.color.negro)
            );
            textoNombre.setTextSize(19);
            textoNombre.setTypeface(null, Typeface.BOLD);

            LinearLayout.LayoutParams parametrosNombre =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

            parametrosNombre.setMargins(0, 18, 0, 10);

            textoNombre.setLayoutParams(parametrosNombre);


            // LUGAR

            TextView textoLugar = new TextView(this);

            textoLugar.setText("📍 " + lugar);
            textoLugar.setTextColor(
                    getResources().getColor(R.color.gris_texto)
            );
            textoLugar.setTextSize(15);


            // AGREGAR A TARJETA

            tarjeta.addView(filaSuperior);
            tarjeta.addView(textoNombre);
            tarjeta.addView(textoLugar);


            // AGREGAR TARJETA

            contenedorEventos.addView(tarjeta);
        }

        cursor.close();
    }
}