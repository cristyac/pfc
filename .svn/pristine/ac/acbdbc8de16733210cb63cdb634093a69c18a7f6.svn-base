package com.controlador.pfc.administrador;


import com.controlador.pfc.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.modelo.pfc.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class vprincipal_administrador extends Activity {

	//usuario y password
	EditText usuariotext,passtext;
	private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //si el centro est? dado de alta se abre la ventana de login
       setContentView(R.layout.vprincipal_administrador);
		//Instancio un Databasereference utilizando la clase FirebaseDatabase
			}
    
    public void sePulsaRegistrar(View view){
    	usuariotext= (EditText) findViewById(R.id.usuarioPersonal);
	 	passtext= (EditText) findViewById(R.id.passwordPersonal);
	 	String usuario=usuariotext.getText().toString();
	 	String password=passtext.getText().toString();
    	//comprobarUsuario(usuario, password);//comprobamos que exista el usuario en la bd y est? correcto





     }

 public void comprobarUsuario(String usuario, String password){	
//comprobamos si existe el usuario
	 boolean resultado=true;
	  if(resultado==true){//existe el usuario 
		  Toast.makeText(getApplicationContext(), "Usuario ya existe, pruebe con otro",Toast.LENGTH_LONG).show();
  
	  }else{//no existe el usuario, procedemos a crearlo	
		  	//hacer un insert en la tabla usuario, en la parte administrador poner falso, y meter usuario y password y tipo usuario = centro

			 Toast.makeText(getApplicationContext(), "Usuario creado correctamente",Toast.LENGTH_LONG).show();
	  }
 }

}
