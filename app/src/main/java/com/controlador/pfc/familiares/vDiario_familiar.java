package com.controlador.pfc.familiares;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.controlador.pfc.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.modelo.pfc.Mensaje;

import java.util.ArrayList;

/**
 * En esta clase se muestra el diario de la persona dependiente
 */
public class vDiario_familiar extends Activity {
    private String nombrePDependiente;
    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vdiario_familiar);
        //sacamos de las preferencias el nombre del usuario
        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //sacamos de los extras enviados de la clase anterior el nombre de la persona dependiente
        nombrePDependiente = datos.getString("pDependiente", "");

        //consultamos en la bd y rellenamos el arraylist
        arrayList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.ListViewMensajes);
        RellenarArrayList();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }

    public void RellenarArrayList() {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Diarios").child(nombrePDependiente);
        mDatabase.orderByChild("Hora").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Mensaje mimensaje = dataSnapshot.getValue(Mensaje.class);
                String horaAMostrar=mimensaje.Hora.substring(0,4)+"-"+mimensaje.Hora.substring(4,6)+"-"+mimensaje.Hora.substring(6,8)+" "+mimensaje.Hora.substring(9,11)+":"+mimensaje.Hora.substring(11,13);
                    arrayList.add(mimensaje.Mensaje + "     " + horaAMostrar);

            }
            @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override public void onCancelled(DatabaseError databaseError) {}
        });
    }
}


