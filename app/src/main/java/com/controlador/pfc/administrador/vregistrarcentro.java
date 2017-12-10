package com.controlador.pfc.administrador;

import com.controlador.pfc.R;

import com.controlador.pfc.R.layout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.modelo.pfc.User;
import com.modelo.pfc.mpDependientes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class vregistrarcentro extends Activity {

	    private Button btnRegistrarAdmin;
	    private RelativeLayout registrarAdministrador;
	    private EditText usuarioAdmin, passAdmin;

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.vregistrarcentro);
	        
	        //ocultamos el panel de registrar administrador hasta que se haga link en dropbox
	        registrarAdministrador =(RelativeLayout) findViewById(R.id.layoutRegistrarAdministrador);
	        registrarAdministrador.setVisibility(View.VISIBLE);
	        
	        //configuramos el bot�n de registrar administrador
	        btnRegistrarAdmin = (Button) findViewById(R.id.btnRegistrarAdmin);
	        btnRegistrarAdmin.setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	// faltaria tramitar el alta del administrador en la bd
	            	//comprobar que no haya ningun administrador en la bd
	            	//cogemos usuario y contrase�a
	            	usuarioAdmin = (EditText) findViewById(R.id.usuarioAdmin);
	            	passAdmin = (EditText) findViewById(R.id.passAdmin);

	            	//darlo de alta en la bd
	            	creandoNuevaBDAdministrador();
	            	
	            }
	        });
	                  
	    }

	    public void creandoNuevaBDAdministrador(){
			final String usuario=usuarioAdmin.getText().toString();
			final String password=passAdmin.getText().toString();

			//no está la persona en la bd y la queremos añadir. Pero primero, tengo que ver si el familiar ya estaba creado o no.
			final DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference("users").child(usuario);
			mDatabase2.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					if(dataSnapshot.getValue()!=null){ //se ha encontrado el familiar en la bd
						Toast.makeText(getApplicationContext(),"Ese Administrador ya existe",Toast.LENGTH_LONG).show();
					}else { //no está en la bd, agregamos el nuevo administrador
						User miuser=new User(usuario, password,"administrador");
						mDatabase2.setValue(miuser);

						Toast.makeText(getApplicationContext(), "Creado Administrador",Toast.LENGTH_LONG).show();
					}
				}
				@Override public void onCancelled(DatabaseError databaseError) {}
			});


		 }
}
