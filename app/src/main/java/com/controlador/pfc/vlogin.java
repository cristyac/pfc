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
		Intent intent;
		switch (tipoUsuario){
			case "administrador":
				intent = new Intent(this, vprincipal_administrador.class);
				startActivity(intent);
				finish();
				break;
			case "cuidador":
				intent = new Intent(this, vprincipal_cuidadores.class);
				startActivity(intent);
				finish();
				break;
			case "familiar":
				intent = new Intent(this, vprincipal_familiar.class);
				startActivity(intent);
				finish();
				break;
		}
		myeditor.putString("USERNAME",usuario);
		myeditor.putString("tipoUsuario",tipoUsuario);
		myeditor.apply();
	}

    public void sePulsaRegistrar(View view){
    	Intent intent = new Intent(this, vregistrarcentro.class);
    	startActivity(intent);
     }

}
