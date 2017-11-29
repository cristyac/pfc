package com.controlador.pfc.administrador;

import com.controlador.pfc.R;

import com.controlador.pfc.R.layout;
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
			 //si la base de datos no est creada creamos la bd y las tablas
			 //comprobamos que no haya ya creado un administrador
			boolean resultado=true;
			  if(resultado){//hay administrador
				  Toast.makeText(getApplicationContext(), "Ya hay un administrador",Toast.LENGTH_LONG).show();
				  btnRegistrarAdmin.setEnabled(false);//deshabilitamos el bot�n de registrar administrador
			  }else{//no hay administrador creado			 
				 //cogemos el usuario y password
				 String usuario=usuarioAdmin.getText().toString();
				 String password=passAdmin.getText().toString();
				 //insertamos el nuevo administrador en la base de datos
				 //DbxRecord elemento = tabla.insert().set("usuario", usuario).set("Administrador", true).set("password", password);
				 Toast.makeText(getApplicationContext(), "Creado Administrador",Toast.LENGTH_LONG).show();
				  btnRegistrarAdmin.setEnabled(false);//deshabilitamos el bot�n de registrar administrador
			  }
		 }
}
