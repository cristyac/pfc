package com.controlador.pfc.familiares;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.controlador.pfc.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.modelo.pfc.Mensaje;

import java.util.ArrayList;
import java.util.List;


public class vChat_familiar extends Activity {
    private String nombrePDependiente, nombreUser;
    private ListView listView;
    private ArrayList<String> arrayList;
    private AdapterCustomizado arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v_chat);
        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        nombreUser = datos.getString("USERNAME", ""); //El segundo valor es para ver cuánto tendrá si no encuentra la clave.
        Bundle extras = getIntent().getExtras();
        nombrePDependiente = extras.getString("nombrePDependiente");

        arrayList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.ListViewMensajes);
        RellenarArrayList();
        arrayAdapter = new AdapterCustomizado(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }

    public void RellenarArrayList() {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Mensajes").child(nombrePDependiente);
        mDatabase.orderByChild("Hora").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Mensaje mimensaje = dataSnapshot.getValue(Mensaje.class);
                String horaAMostrar=mimensaje.Hora.substring(0,4)+"-"+mimensaje.Hora.substring(4,6)+"-"+mimensaje.Hora.substring(6,8)+" "+mimensaje.Hora.substring(9,11)+":"+mimensaje.Hora.substring(11,13);
                if (mimensaje.Emisor.equals(nombreUser)) {
                    arrayList.add("Yo: " + mimensaje.Mensaje + "     " + horaAMostrar);
                } else {
                    arrayList.add(mimensaje.Emisor + ": " + mimensaje.Mensaje + "     " + horaAMostrar);
                }
            }
            @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override public void onCancelled(DatabaseError databaseError) {}
        });
    }


    class AdapterCustomizado extends ArrayAdapter<String> {
        public AdapterCustomizado(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        public View getView(int pos, View convertView, ViewGroup parent) {
            TextView textView = (TextView) super.getView(pos, convertView, parent);

            System.out.println(textView.getText().toString());
            if (textView.getText().toString().startsWith("Yo:")) {
                textView.setBackgroundColor(Color.rgb(200, 231, 141));
            } else {
                textView.setBackgroundColor(Color.rgb(235, 236, 233));
            }
            return textView;
        }
    }


    public void sePulsaEnviar(View view) { //se envía un mensaje, se añade en la base de datos y se muestra por pantalla
        EditText editText = (EditText) findViewById(R.id.editTextEscribir);
        String loQueEscribo = editText.getText().toString();
        //cogemos la fecha actual
        Time mitime = new Time();
        mitime.setToNow();
        String fecha = mitime.toString().substring(0, 15);
        //creamos el registro mensaje y se añade a la base de datos
        Mensaje mimensaje = new Mensaje(nombreUser, fecha, loQueEscribo, nombrePDependiente);
        mimensaje.AgregarMensaje();
        //agregamos al arraylist
        String horaAMostrar=fecha.substring(0,4)+"-"+fecha.substring(4,6)+"-"+fecha.substring(6,8)+" "+fecha.substring(9,11)+":"+fecha.substring(11,13);
        arrayList.add("Yo: " + loQueEscribo + "     " + horaAMostrar);
        arrayAdapter = new AdapterCustomizado(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }
}


