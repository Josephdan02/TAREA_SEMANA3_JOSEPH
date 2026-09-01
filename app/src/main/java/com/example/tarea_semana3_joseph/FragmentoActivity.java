package com.example.tarea_semana3_joseph;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FragmentoActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    LinearLayout contenedorFragmentos;
    TextView btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragmento);

        contenedorFragmentos =
                findViewById(R.id.contenedorFragmentos);

        btnVolver = findViewById(R.id.btnVolver);

        databaseHelper = new DatabaseHelper(this);

        // BOTÓN VOLVER
        btnVolver.setOnClickListener(v -> {
            finish();
        });

        mostrarFragmentos();
    }


    private void mostrarFragmentos() {

        Cursor cursor = databaseHelper.obtenerFragmentos();

        while (cursor.moveToNext()) {

            String titulo = cursor.getString(
                    cursor.getColumnIndexOrThrow("titulo")
            );

            String autor = cursor.getString(
                    cursor.getColumnIndexOrThrow("autor")
            );

            String texto = cursor.getString(
                    cursor.getColumnIndexOrThrow("texto")
            );


            TextView fragmento = new TextView(this);

            fragmento.setText(
                    titulo +
                            "\n\nAutor: " + autor +
                            "\n\n" + texto
            );

            fragmento.setTextSize(18);
            fragmento.setPadding(20, 20, 20, 30);

            contenedorFragmentos.addView(fragmento);
        }

        cursor.close();
    }
}