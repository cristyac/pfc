package com.modelo.pfc;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Se almacena en este objeto los datos de los mensajes-chat
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

    public void AgregarMensaje (String pDependiente){
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Mensajes").child(pDependiente);
        mDatabase.child(this.Hora).setValue(this);
    }

}
