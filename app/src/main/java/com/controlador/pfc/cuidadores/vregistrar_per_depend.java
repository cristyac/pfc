package com.controlador.pfc.cuidadores;

import com.controlador.pfc.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.modelo.pfc.User;
import com.modelo.pfc.mpDependientes;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class vregistrar_per_depend extends Activity {
	private EditText nombrePerDepend, grupo, nombreF, usuFamiliar, passF;
	private String nombrePerDependiente, grupoPerDepend,nombreFamiliar,usuarioFamiliar,passwordFamiliar, nombreUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//mostramos la vista
		setContentView(R.layout.vregistrar_per_depend);
		SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		nombreUser = datos.getString("USERNAME", ""); //El segundo valor es para ver cuánto tendrá si no encuentra la clave.
	}

	public void sepulsaagregar(View view){
		//Se cogen los datos del formulario 5 datos
		//nombre de la persona dependiente
		nombrePerDepend=(EditText) findViewById(R.id.nombrePerDepend);
		nombrePerDependiente=nombrePerDepend.getText().toString();
		//grupo
		grupo=(EditText) findViewById(R.id.grupo);
		grupoPerDepend=grupo.getText().toString();
		//nombre Familiar
		nombreF=(EditText) findViewById(R.id.nombreFamiliar);
		nombreFamiliar=nombreF.getText().toString();
		//usuario familiar
		usuFamiliar=(EditText) findViewById(R.id.nuevoUsuarioFamiliar);
		usuarioFamiliar=usuFamiliar.getText().toString();
		//contrasena familiar
		passF=(EditText) findViewById(R.id.passwordUsuarioFamiliar);
		passwordFamiliar=passF.getText().toString();

		AgregarPersonaDependiente(usuarioFamiliar,grupoPerDepend,nombrePerDependiente,passwordFamiliar);

	}


	public void AgregarPersonaDependiente(final String familiar, final String grupo, final String nombre,final String passwordFamiliar) {
		final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("pDependientes").child(nombre);
		mDatabase.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				if(dataSnapshot.getValue()!=null){ //se ha encontrado la persona en la bd
					Toast.makeText(getApplicationContext(),"Esa persona ya existe",Toast.LENGTH_LONG).show();
				}else{ //no está la persona en la bd y la queremos añadir. Pero primero, tengo que ver si el familiar ya estaba creado o no.
					final DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference("users").child(familiar);
					mDatabase2.addValueEventListener(new ValueEventListener() {
						@Override
						public void onDataChange(DataSnapshot dataSnapshot) {
							if(dataSnapshot.getValue()!=null){ //se ha encontrado el familiar en la bd
								Toast.makeText(getApplicationContext(),"Ese familiar ya existe",Toast.LENGTH_LONG).show();
							}else { //no está el familiar en la bd. Por tanto, añadimos tanto el familiar como a la persona dependiente
								mpDependientes midependiente= new mpDependientes(familiar,grupo,nombre,nombreUser);
								mDatabase.setValue(midependiente);
								User miuser=new User(familiar, passwordFamiliar,"familiar");
								mDatabase2.setValue(miuser);
								Toast.makeText(getApplicationContext(),"Creado correctamente",Toast.LENGTH_LONG).show();
							}
						}
						@Override public void onCancelled(DatabaseError databaseError) {}
					});
				}
			}
			@Override public void onCancelled(DatabaseError databaseError) {}
		});
	}

	
}
