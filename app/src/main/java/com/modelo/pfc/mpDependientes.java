package com.modelo.pfc;

/**
 * Created by FranciscoJavier2 on 11/11/2017.
 */

public class mpDependientes {

    public String Familiar;
    public String Grupo;
    public String Nombre;

    public mpDependientes() {
    } // Default constructor required for calls to DataSnapshot.getValue(User.class)

    public mpDependientes(String Familiar, String Grupo, String Nombre) {
        this.Familiar = Familiar;
        this.Grupo = Grupo;
        this.Nombre=Nombre;
    }


}
