package com.modelo.pfc;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class User  {
    public String username;
    public String contrasena;
    public String tipousuario;

    public User() {
    } // Default constructor required for calls to DataSnapshot.getValue(User.class)

    public User(String username, String contrasena, String tipousuario) {
        this.username = username;
        this.contrasena = contrasena;
        this.tipousuario=tipousuario;
    }



}
