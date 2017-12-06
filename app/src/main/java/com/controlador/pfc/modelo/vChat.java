package com.controlador.pfc.modelo;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.format.Time;
import android.text.style.ForegroundColorSpan;
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
import java.util.*;


public class vChat extends Activity {
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
                System.out.println("-----------NOMBREUSER--------" + nombreUser);
                System.out.println("-----------EMISOR--------" + mimensaje.Emisor);
                if (mimensaje.Emisor.equals(nombreUser)) {
                    arrayList.add("Yo: " + mimensaje.Mensaje + "     " + mimensaje.Hora);
                } else {
                    arrayList.add(mimensaje.Emisor + ": " + mimensaje.Mensaje + "     " + mimensaje.Hora);
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


    public void sePulsaEnviar(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextEscribir);
        String loQueEscribo = editText.getText().toString();
        Time mitime = new Time();
        mitime.setToNow();
        System.out.println("----------------------------------------------------------------------------" + mitime.toString().substring(0, 15));
        String fecha = mitime.toString().substring(0, 15);
        Mensaje mimensaje = new Mensaje(nombreUser, fecha, loQueEscribo, nombrePDependiente);
        mimensaje.AgregarMensaje();
        arrayList.add("Yo: " + loQueEscribo + "     " + fecha);
        arrayAdapter = new AdapterCustomizado(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }
}


