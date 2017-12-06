package com.modelo.pfc;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by FranciscoJavier2 on 15/11/2017.
 */

public class Mensaje {
    public String Emisor;
    public String Hora;
    public String Mensaje;
    public String Receptor;


    public Mensaje() {
    } // Default constructor required for calls to DataSnapshot.getValue(User.class)

    public Mensaje(String Emisor, String Hora, String Mensaje, String Receptor) {
        this.Emisor = Emisor;
        this.Hora = Hora;
        this.Mensaje=Mensaje;
        this.Receptor=Receptor;
    }

    public void AgregarMensaje (){
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Mensajes").child(Receptor);
        mDatabase.child(this.Hora).setValue(this);
    }

}
