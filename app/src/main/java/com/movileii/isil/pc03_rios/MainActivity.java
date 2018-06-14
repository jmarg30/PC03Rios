package com.movileii.isil.pc03_rios;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnInsertar, btnMostrar, btnEliminar, btnnuevo;
    private EditText txtDireccion, txtPisos;
    private TextView txtUsuario;

    String sqlbd = "", nombretabla = "persona", nombrebd = "BD000007";
    int cod = 0;

    SQLiteDatabase basededatos = null;
    String codigocad = "", opcion = "", codigo = "", nombre = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsertar = (Button) findViewById(R.id.BTNINSERTAR);
        btnMostrar = (Button) findViewById(R.id.BTNMOSTRAR);
        btnEliminar = (Button) findViewById(R.id.BTNELIMINAR);
        btnnuevo = (Button) findViewById(R.id.BTNNUEVO);

        txtDireccion = (EditText) findViewById(R.id.txtDireccion);
        txtPisos = (EditText) findViewById(R.id.txtPisos);
        txtUsuario = (TextView)findViewById(R.id.txtUsuario);
        CrearBD();

        Intent obj = getIntent();
        Bundle objcodigocad = obj.getExtras();
        nombre = (String)objcodigocad.getString("nombre");
        opcion = (String)objcodigocad.getString("op");

        txtUsuario.setText("Hola: " + nombre);

        switch (Integer.parseInt(opcion)) {
            case 1: {
                ActivarBotones(false, true, false, true);
                LimpiarCampos();
                break;
            }
            case 2: {
                ActivarBotones(true, true, true, true);
                LimpiarCampos();
                codigocad = (String) objcodigocad.get("codigo");
                cargarCampos(Integer.parseInt(codigocad));

                break;
            }
        }
    }

    public void Nuevo(View v) {
        ActivarBotones(true, true, false, true);
    }

    public void LimpiarCampos() {
        txtDireccion.setText("");
        txtPisos.setText("");
        txtDireccion.requestFocus();
    }


    public void ActivarBotones(boolean e1, boolean e2, boolean e3, boolean e4) {
        btnInsertar.setEnabled(e1);
        btnMostrar.setEnabled(e2);
        btnEliminar.setEnabled(e3);
        btnnuevo.setEnabled(e4);
    }

    public void CrearBD() {
        sqlbd = "create table  if not  exists " +
                nombretabla + " ( codigo  integer  primary key  autoincrement ," +
                "direccion  text not null , pisos  text not  null);";
        try {
            basededatos = openOrCreateDatabase(nombrebd, MODE_PRIVATE, null);
            basededatos.execSQL(sqlbd);
        } catch (Exception e) {

        }
    }

    public long Insertar(String dir, String pis) {
        long estado = 0;
        try {
            ContentValues valores = new ContentValues();
            valores.put("direccion", dir);
            valores.put("pisos", pis);
            estado = basededatos.insert(nombretabla, null, valores);
        } catch (Exception e) {
            estado = 0;
        }
        return estado;
    }

    public void Ejecutar(View v) {

        Intent obj = getIntent();
        Bundle objcodigocad = obj.getExtras();
        opcion = (String) objcodigocad.getString("op");

        if (Integer.parseInt(opcion) == 2) {
            Actualizar();
            Toast.makeText(getApplicationContext(), "Registro  Guardado", Toast.LENGTH_LONG).show();
            txtDireccion.setText("");
            txtPisos.setText("");
            txtDireccion.requestFocus();
        } else {
            String direccion = txtDireccion.getText().toString();
            String pisos = txtPisos.getText().toString();
            long i = 0;
            i = Insertar(direccion, pisos);
            if (i == 0) {
                Toast.makeText(getApplicationContext(), "Registro no insertado", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Registro insertado", Toast.LENGTH_LONG).show();
                txtDireccion.setText("");
                txtPisos.setText("");
                txtDireccion.requestFocus();
            }
        }
    }

    public void MostrarDatos(View v) {
        basededatos.close();
        Intent objeto = new Intent(MainActivity.this, ReporteActivity.class);
        objeto.putExtra("nombre", nombre);
        startActivity(objeto);

    }


    public void cargarCampos(int codigo) {
        Cursor c;

        String nombre, apellido, dni;

        try {
            c = basededatos.rawQuery(" SELECT *  FROM persona where codigo=" + codigo, null);

            if (c.moveToNext()) {

                cod = c.getInt(0);
                txtDireccion.setText(c.getString(1));
                txtPisos.setText(c.getString(2));
            }

        } catch (Exception e) {

        }

    }

    public long EliminarRegistro(int codigo) {
        long estado = 0;
        try {

            estado = estado = basededatos.delete(nombretabla, "codigo=?", new String[]{String.valueOf(codigo)});

        } catch (Exception e) {
            estado = 0;
        }
        return estado;

    }


    public void Eliminar(View v) {
        long i = 0;
        i = EliminarRegistro(cod);
        if (i == 0) {
            Toast.makeText(getApplicationContext(), "Registro No Eliminado", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Registro  Eliminado", Toast.LENGTH_LONG).show();
            ActivarBotones(false, true, false, true);
            LimpiarCampos();
        }

    }

    public long ActualizarRegistro(int cod, String dir, String pis){
        long estado=0;
        try{
            ContentValues values = new ContentValues();
            values.put("codigo", cod);
            values.put("direccion", dir);
            values.put("pisos", pis);
            estado=basededatos.update(nombretabla, values, "codigo=?", new String[] {String.valueOf(cod)});

        }catch (Exception e){
            estado=0;
            e.printStackTrace();
        }
        return estado;
    }
    public void Actualizar(){
        long i = 0;
        String dir1=txtDireccion.getText().toString();
        String pis1 = txtPisos.getText().toString();

        i=ActualizarRegistro(cod, dir1, pis1);

        if(i==0){
            Toast.makeText(getApplicationContext(), "No se actualizo", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "Si se actualizo", Toast.LENGTH_LONG).show();
            LimpiarCampos();
        }
    }

}
