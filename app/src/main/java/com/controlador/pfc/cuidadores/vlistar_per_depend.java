package com.controlador.pfc.cuidadores;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.controlador.pfc.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * DIARIO
 * Esta clase se usa para listar las personas dependientes de la pestaña diarios
 */
public class vlistar_per_depend extends Activity {
    public String usuario, nombrepDependiente;
    private ListView listView;
    private int posicionArrayList = -1;   //posición del ArrayList que pinchamos
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v_mensajes);
        //sacamos el nombre de usuario de las preferencias
        SharedPreferences datos= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        usuario=datos.getString("USERNAME",""); //El segundo valor es para ver cuánto tendrá si no encuentra la clave.

        listView = (ListView) findViewById(R.id.ListViewPDependientes);
        RellenarArrayList();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        MostrarListview();
        listView.setAdapter(arrayAdapter);
    }

    public void RellenarArrayList() {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("pDependientes");
        mDatabase.orderByChild("Nombre").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrayList.add(dataSnapshot.getKey());}
            @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override public void onCancelled(DatabaseError databaseError) {}
        });
    }


    public void MostrarListview() {
        posicionArrayList = -1;//posición del listview seleccionada
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                //El bucle for es necesario aunque por defecto el color sea transparente para que cada vez que se llame a este método,
                //se ponga una opción transparente si no lo estaba previamente por haber pinchado sobre ella.
                for (int j = 0; j < adapterView.getChildCount(); j++) {
                    adapterView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
                }
                view.setBackgroundColor(Color.LTGRAY);
                posicionArrayList = i;   //Importante actualizar posicionArrayList ya que la utilizaremos en el método sepulsaabrirconversacion
            }
        });
    }


    /**
     * este método abre el DIARIO de una persona dependiente específica
     * @param view
     */
    public void sepulsaabrirconversacion(View view) {
        if (posicionArrayList != -1) {
            nombrepDependiente = arrayList.get(posicionArrayList);
            SharedPreferences datos= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor myeditor=datos.edit();
            //////////////////////////////////////////////////NO ES VCHAT
            Intent i = new Intent(this, vDiario.class);
            i.putExtra("nombrePDependiente",nombrepDependiente);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), "No se ha seleccionado ningún elemento", Toast.LENGTH_LONG).show();
        }
    }

}



