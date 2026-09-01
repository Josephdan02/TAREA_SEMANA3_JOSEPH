package com.example.tarea_semana3_joseph;

import android.database.Cursor;
import android.os.Bundle;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class GestionarProgramaActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    LinearLayout contenedorEventos;

    EditText txtDia;
    EditText txtHora;
    EditText txtNombre;
    EditText txtLugar;

    Button btnAgregar;
    TextView btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gestionar_programa);

        btnVolver = findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(v -> {
            finish();
        });

        databaseHelper = new DatabaseHelper(this);

        contenedorEventos = findViewById(R.id.contenedorEventos);

        txtDia = findViewById(R.id.txtDia);
        txtHora = findViewById(R.id.txtHora);
        txtNombre = findViewById(R.id.txtNombre);
        txtLugar = findViewById(R.id.txtLugar);

        btnAgregar = findViewById(R.id.btnAgregar);

        btnAgregar.setOnClickListener(v -> {

            agregarEvento();

        });

        mostrarEventos();
    }


    private void agregarEvento() {

        String dia = txtDia.getText().toString().trim();
        String hora = txtHora.getText().toString().trim();
        String nombre = txtNombre.getText().toString().trim();
        String lugar = txtLugar.getText().toString().trim();


        if (dia.isEmpty() ||
                hora.isEmpty() ||
                nombre.isEmpty() ||
                lugar.isEmpty()) {

            Toast.makeText(
                    this,
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        boolean resultado =
                databaseHelper.insertarEvento(
                        dia,
                        hora,
                        nombre,
                        lugar
                );


        if (resultado) {

            Toast.makeText(
                    this,
                    "Evento agregado correctamente",
                    Toast.LENGTH_SHORT
            ).show();

            txtDia.setText("");
            txtHora.setText("");
            txtNombre.setText("");
            txtLugar.setText("");

            mostrarEventos();

        } else {

            Toast.makeText(
                    this,
                    "No se pudo agregar el evento",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }


    private void mostrarEventos() {

        contenedorEventos.removeAllViews();

        Cursor cursor = databaseHelper.obtenerEventos();

        while (cursor.moveToNext()) {

            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow("id")
            );

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


            LinearLayout tarjeta = new LinearLayout(this);

            tarjeta.setOrientation(LinearLayout.VERTICAL);

            tarjeta.setPadding(20, 20, 20, 20);

            tarjeta.setBackgroundResource(
                    R.drawable.evento_card
            );

            tarjeta.setElevation(4);


            LinearLayout.LayoutParams parametros =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

            parametros.setMargins(0, 0, 0, 18);

            tarjeta.setLayoutParams(parametros);


            TextView textoEvento = new TextView(this);

            textoEvento.setText(
                    dia.toUpperCase() +
                            "  •  " + hora +
                            "\n\n" +
                            nombre +
                            "\n\n📍 " + lugar
            );

            textoEvento.setTextSize(17);

            textoEvento.setTextColor(
                    getResources().getColor(R.color.negro)
            );


            // BOTÓN EDITAR

            Button btnEditar = new Button(this);

            btnEditar.setText("Editar");

            btnEditar.setOnClickListener(v -> {

                editarEvento(
                        id,
                        dia,
                        hora,
                        nombre,
                        lugar
                );

            });


            // BOTÓN ELIMINAR

            Button btnEliminar = new Button(this);

            btnEliminar.setText("Eliminar");

            btnEliminar.setOnClickListener(v -> {

                eliminarEvento(id);

            });


            tarjeta.addView(textoEvento);
            tarjeta.addView(btnEditar);
            tarjeta.addView(btnEliminar);

            contenedorEventos.addView(tarjeta);
        }

        cursor.close();
    }


    private void editarEvento(
            int id,
            String dia,
            String hora,
            String nombre,
            String lugar) {

        txtDia.setText(dia);
        txtHora.setText(hora);
        txtNombre.setText(nombre);
        txtLugar.setText(lugar);

        btnAgregar.setText("Guardar cambios");

        btnAgregar.setOnClickListener(v -> {

            boolean resultado =
                    databaseHelper.actualizarEvento(
                            id,
                            txtDia.getText().toString().trim(),
                            txtHora.getText().toString().trim(),
                            txtNombre.getText().toString().trim(),
                            txtLugar.getText().toString().trim()
                    );

            if (resultado) {

                Toast.makeText(
                        this,
                        "Evento actualizado",
                        Toast.LENGTH_SHORT
                ).show();

                btnAgregar.setText("Agregar evento");

                txtDia.setText("");
                txtHora.setText("");
                txtNombre.setText("");
                txtLugar.setText("");

                btnAgregar.setOnClickListener(v2 -> agregarEvento());

                mostrarEventos();

            }
        });
    }


    private void eliminarEvento(int id) {

        boolean resultado =
                databaseHelper.eliminarEvento(id);

        if (resultado) {

            Toast.makeText(
                    this,
                    "Evento eliminado",
                    Toast.LENGTH_SHORT
            ).show();

            mostrarEventos();

        } else {

            Toast.makeText(
                    this,
                    "No se pudo eliminar",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}