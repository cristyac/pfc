package com.controlador.pfc.administrador;


import com.controlador.pfc.R;
import com.controlador.pfc.vlogin;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.modelo.pfc.User;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
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
    	comprobarUsuario(usuario, password);//comprobamos que exista el usuario en la bd y este correcto
	}

	/**
	 * en este metodo eliminamos un usuario cuidador
	 * @param view
	 */
	public void sePulsarEliminar(View view){
		Intent intent = new Intent(this, vborrar_cuidador.class);
		startActivity(intent);
	}

	 public void comprobarUsuario(final String usuario, final String password){
	//comprobamos si existe el usuario
		 //no está la persona en la bd y la queremos añadir. Pero primero, tengo que ver si el cuidador ya estaba creado o no.
		 final DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference("users").child(usuario);
		 mDatabase2.addValueEventListener(new ValueEventListener() {
			 @Override
			 public void onDataChange(DataSnapshot dataSnapshot) {
				 if(dataSnapshot.getValue()!=null){ //se ha encontrado el usuario en la bd
					 Toast.makeText(getApplicationContext(), "Usuario ya existe, pruebe con otro",Toast.LENGTH_LONG).show();
				 }else { //no está en la bd, agregamos el nuevo cuidador
					 User miuser=new User(usuario, password,"cuidador");
					 mDatabase2.setValue(miuser);

					 Toast.makeText(getApplicationContext(), "Usuario cuidador creado correctamente",Toast.LENGTH_LONG).show();
				 }
			 }
			 @Override public void onCancelled(DatabaseError databaseError) {}
		 });
	 }
	//MENU
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.menu_principal_administrador, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
			case R.id.btnMenuCerrarSesion:
				cerrar_sesion();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}
	public void cerrar_sesion(){
		SharedPreferences datos=null;
		SharedPreferences.Editor myeditor=null;
		datos= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		myeditor=datos.edit();
		myeditor.putString("USERNAME","");
		myeditor.putString("tipoUsuario","");
		myeditor.apply();
		Intent intent = new Intent(this, vlogin.class);
		startActivity(intent);
		finish();
	}
}
