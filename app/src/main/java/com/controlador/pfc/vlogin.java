package com.controlador.pfc;


import com.controlador.pfc.administrador.vprincipal_administrador;
import com.controlador.pfc.administrador.vregistrarcentro;
import com.controlador.pfc.cuidadores.vprincipal_cuidadores;
import com.controlador.pfc.familiares.vprincipal_familiar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ChildEventListener;
import com.modelo.pfc.User;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.format.Time;


public class vlogin extends Activity   {
	private EditText usuariotext,passtext;
	SharedPreferences datos=null;
	SharedPreferences.Editor myeditor=null;
	String usuario;
	User miuser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		datos= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		myeditor=datos.edit();
		String usuariopreferencias=datos.getString("USERNAME","");
		String tipo=datos.getString("tipoUsuario","");

		//si ya disponemos de los datos en preferencias abrimos vlogin, sino abrimos la ventana según tipo de usuario
		if(usuariopreferencias.equals("")) {
			setContentView(R.layout.vlogin);
		}
		else{
			empiezaActividadSegunUsuario(tipo);
		}
    }

    public void sePulsalogin(View view){
		usuariotext= (EditText) findViewById(R.id.usuario);
		passtext= (EditText) findViewById(R.id.pass);
		usuario=usuariotext.getText().toString();
		String password=passtext.getText().toString();
		comprobarUsuario(usuario, password);
    }

	public void comprobarUsuario(final String usuario, final String password) {
		final DatabaseReference mDatabase = (FirebaseDatabase.getInstance()).getReference("users").child(usuario);
		mDatabase.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				if(dataSnapshot.getValue()!=null){
					miuser=dataSnapshot.getValue(User.class);
					if ((miuser.contrasena).equals(password)){
						myeditor.putString("USERNAME",usuario);
						myeditor.putString("tipoUsuario",miuser.tipousuario);
						myeditor.apply();
						if(miuser.tipousuario.equals("familiar")){//sacamos el nombre de la persona dependiente vinculada y guardamos en preferencias
							consultarPersonaDependiente();
						}
						empiezaActividadSegunUsuario(miuser.tipousuario);
					}else {
						Toast.makeText(getApplicationContext(),"Contraseña introducida incorrecta",Toast.LENGTH_LONG).show();
					}
				}else{
					Toast.makeText(getApplicationContext(),"Nombre de usuario y/o contraseña incorrectos",Toast.LENGTH_LONG).show();
				}
			}
			@Override
			public void onCancelled(DatabaseError databaseError) {}
		});
	}

	public void empiezaActividadSegunUsuario(String tipoUsuario){
		Intent intent=null;
		switch (tipoUsuario){
			case "administrador":
				intent = new Intent(this, vprincipal_administrador.class);
				break;
			case "cuidador":
				intent = new Intent(this, vprincipal_cuidadores.class);
				break;
			case "familiar":
				intent = new Intent(this, vprincipal_familiar.class);
				break;
		}
		startActivity(intent);
		finish();

	}

    public void sePulsaRegistrar(View view){
    	Intent intent = new Intent(this, vregistrarcentro.class);
    	startActivity(intent);
     }

	public void consultarPersonaDependiente(){ //buscamos la persona dependiente asociada al familiar y la almacenamos en preferencias
		final DatabaseReference mDatabase2 = (FirebaseDatabase.getInstance()).getReference("pDependientes");
		mDatabase2.orderByChild("Familiar").equalTo(usuario).addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String s) {
				if (dataSnapshot.child("Familiar").getValue().toString().equals(usuario)) {
					String pDependiente = dataSnapshot.child("Nombre").getValue().toString();
					myeditor.putString("pDependiente", pDependiente);
					myeditor.apply();
				}
			}
			@Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
			@Override public void onChildRemoved(DataSnapshot dataSnapshot) {}
			@Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
			@Override public void onCancelled(DatabaseError databaseError) {}
		});
	}

}
