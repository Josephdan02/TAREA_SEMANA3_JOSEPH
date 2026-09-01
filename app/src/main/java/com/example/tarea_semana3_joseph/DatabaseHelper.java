package com.example.tarea_semana3_joseph;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;
import android.database.Cursor;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ProgramaEscolar.db";
    private static final int DATABASE_VERSION = 1;

    // Tabla de eventos
    private static final String TABLE_EVENTOS = "eventos";

    private static final String EVENTO_ID = "id";
    private static final String EVENTO_DIA = "dia";
    private static final String EVENTO_HORA = "hora";
    private static final String EVENTO_NOMBRE = "nombre";
    private static final String EVENTO_LUGAR = "lugar";

    // Tabla de fragmentos
    private static final String TABLE_FRAGMENTOS = "fragmentos";

    private static final String FRAGMENTO_ID = "id";
    private static final String FRAGMENTO_TITULO = "titulo";
    private static final String FRAGMENTO_AUTOR = "autor";
    private static final String FRAGMENTO_TEXTO = "texto";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // Crear tabla eventos
        String crearEventos = "CREATE TABLE " + TABLE_EVENTOS + " (" +
                EVENTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EVENTO_DIA + " TEXT, " +
                EVENTO_HORA + " TEXT, " +
                EVENTO_NOMBRE + " TEXT, " +
                EVENTO_LUGAR + " TEXT)";

        db.execSQL(crearEventos);


        // Crear tabla fragmentos
        String crearFragmentos = "CREATE TABLE " + TABLE_FRAGMENTOS + " (" +
                FRAGMENTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FRAGMENTO_TITULO + " TEXT, " +
                FRAGMENTO_AUTOR + " TEXT, " +
                FRAGMENTO_TEXTO + " TEXT)";

        db.execSQL(crearFragmentos);


        // Datos iniciales
        insertarEventosIniciales(db);
        insertarFragmentosIniciales(db);
    }


    // =====================================================
    // INSERTAR DATOS INICIALES
    // =====================================================

    private void insertarEventosIniciales(SQLiteDatabase db) {

        ContentValues evento = new ContentValues();

        evento.put(EVENTO_DIA, "Lunes");
        evento.put(EVENTO_HORA, "09:00");
        evento.put(EVENTO_NOMBRE, "Ceremonia de inauguración");
        evento.put(EVENTO_LUGAR, "Patio principal");
        db.insert(TABLE_EVENTOS, null, evento);


        evento.clear();

        evento.put(EVENTO_DIA, "Martes");
        evento.put(EVENTO_HORA, "10:00");
        evento.put(EVENTO_NOMBRE, "Concurso de lectura");
        evento.put(EVENTO_LUGAR, "Biblioteca");
        db.insert(TABLE_EVENTOS, null, evento);


        evento.clear();

        evento.put(EVENTO_DIA, "Miércoles");
        evento.put(EVENTO_HORA, "11:00");
        evento.put(EVENTO_NOMBRE, "Presentación artística");
        evento.put(EVENTO_LUGAR, "Auditorio");
        db.insert(TABLE_EVENTOS, null, evento);


        evento.clear();

        evento.put(EVENTO_DIA, "Jueves");
        evento.put(EVENTO_HORA, "14:00");
        evento.put(EVENTO_NOMBRE, "Feria cultural");
        evento.put(EVENTO_LUGAR, "Patio principal");
        db.insert(TABLE_EVENTOS, null, evento);


        evento.clear();

        evento.put(EVENTO_DIA, "Viernes");
        evento.put(EVENTO_HORA, "15:00");
        evento.put(EVENTO_NOMBRE, "Clausura de la semana cultural");
        evento.put(EVENTO_LUGAR, "Auditorio");
        db.insert(TABLE_EVENTOS, null, evento);
    }


    private void insertarFragmentosIniciales(SQLiteDatabase db) {

        ContentValues fragmento = new ContentValues();

        fragmento.put(FRAGMENTO_TITULO, "El valor de la amistad");
        fragmento.put(FRAGMENTO_AUTOR, "Autor anónimo");
        fragmento.put(FRAGMENTO_TEXTO,
                "La amistad es uno de los valores más importantes " +
                        "porque nos permite compartir, ayudar y aprender " +
                        "junto a otras personas.");

        db.insert(TABLE_FRAGMENTOS, null, fragmento);


        fragmento.clear();

        fragmento.put(FRAGMENTO_TITULO, "La importancia de aprender");
        fragmento.put(FRAGMENTO_AUTOR, "Autor anónimo");
        fragmento.put(FRAGMENTO_TEXTO,
                "Aprender nos permite descubrir nuevas ideas, " +
                        "desarrollar nuestras capacidades y prepararnos " +
                        "para enfrentar los desafíos de la vida.");

        db.insert(TABLE_FRAGMENTOS, null, fragmento);
    }


    // =====================================================
    // INSERTAR EVENTO
    // =====================================================

    public boolean insertarEvento(String dia, String hora,
                                  String nombre, String lugar) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put(EVENTO_DIA, dia);
        valores.put(EVENTO_HORA, hora);
        valores.put(EVENTO_NOMBRE, nombre);
        valores.put(EVENTO_LUGAR, lugar);

        long resultado = db.insert(TABLE_EVENTOS, null, valores);

        return resultado != -1;
    }


    // =====================================================
    // CONSULTAR EVENTOS
    // =====================================================

    public Cursor obtenerEventos() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM " + TABLE_EVENTOS +
                        " ORDER BY id ASC",
                null
        );
    }


    // =====================================================
    // ACTUALIZAR EVENTO
    // =====================================================

    public boolean actualizarEvento(int id, String dia, String hora,
                                    String nombre, String lugar) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put(EVENTO_DIA, dia);
        valores.put(EVENTO_HORA, hora);
        valores.put(EVENTO_NOMBRE, nombre);
        valores.put(EVENTO_LUGAR, lugar);

        int resultado = db.update(
                TABLE_EVENTOS,
                valores,
                EVENTO_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        return resultado > 0;
    }


    // =====================================================
    // ELIMINAR EVENTO
    // =====================================================

    public boolean eliminarEvento(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int resultado = db.delete(
                TABLE_EVENTOS,
                EVENTO_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        return resultado > 0;
    }


    // =====================================================
    // CONSULTAR FRAGMENTOS
    // =====================================================

    public Cursor obtenerFragmentos() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM " + TABLE_FRAGMENTOS +
                        " ORDER BY id ASC",
                null
        );
    }


    // =====================================================
    // INSERTAR FRAGMENTO
    // =====================================================

    public boolean insertarFragmento(String titulo,
                                     String autor,
                                     String texto) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put(FRAGMENTO_TITULO, titulo);
        valores.put(FRAGMENTO_AUTOR, autor);
        valores.put(FRAGMENTO_TEXTO, texto);

        long resultado = db.insert(
                TABLE_FRAGMENTOS,
                null,
                valores
        );

        return resultado != -1;
    }


    // =====================================================
    // ACTUALIZAR FRAGMENTO
    // =====================================================

    public boolean actualizarFragmento(int id,
                                       String titulo,
                                       String autor,
                                       String texto) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put(FRAGMENTO_TITULO, titulo);
        valores.put(FRAGMENTO_AUTOR, autor);
        valores.put(FRAGMENTO_TEXTO, texto);

        int resultado = db.update(
                TABLE_FRAGMENTOS,
                valores,
                FRAGMENTO_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        return resultado > 0;
    }


    // =====================================================
    // ELIMINAR FRAGMENTO
    // =====================================================

    public boolean eliminarFragmento(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int resultado = db.delete(
                TABLE_FRAGMENTOS,
                FRAGMENTO_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        return resultado > 0;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRAGMENTOS);

        onCreate(db);
    }
}