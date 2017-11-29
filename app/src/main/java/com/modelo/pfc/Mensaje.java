package com.modelo.pfc;

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

}
