package com.modelo.pfc;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Se almacena en este objeto los datos del diario
 */

public class Diario {
    public String Emisor;
    public String Hora;
    public String Mensaje;
    public String Receptor;


    public Diario() {
    } // Default constructor required for calls to DataSnapshot.getValue(User.class)

    public Diario(String Emisor, String Hora, String Mensaje, String Receptor) {
        this.Emisor = Emisor;
        this.Hora = Hora;
        this.Mensaje=Mensaje;
        this.Receptor=Receptor;
    }

    public void AgregarDiario (){
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Diarios").child(Receptor);
        mDatabase.child(this.Hora).setValue(this);
    }

}
