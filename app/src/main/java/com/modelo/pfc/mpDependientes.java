package com.modelo.pfc;

/**
 * Entidad personas dependientes
 */

public class mpDependientes {

    public String Familiar;
    public String Grupo;
    public String Nombre;
    public String Cuidador;

    public mpDependientes() {
    } // Default constructor required for calls to DataSnapshot.getValue(User.class)

    public mpDependientes(String Familiar, String Grupo, String Nombre, String Cuidador) {
        this.Familiar = Familiar;
        this.Grupo = Grupo;
        this.Nombre=Nombre;
        this.Cuidador=Cuidador;
    }


}
