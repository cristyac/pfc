package com.controlador.pfc.cuidadores;

import com.controlador.pfc.R;
import com.controlador.pfc.modelo.vMensajes;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class vprincipal_cuidadores extends Activity {
	//public String usuario;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vprincipal_cuidador);

		//SharedPreferences datos= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		//usuario=datos.getString("USERNAME",""); //El segundo valor es para ver cuánto tendrá si no encuentra la clave.

		//Bundle mybundle=getIntent().getExtras();
		//usuario = mybundle.getString("USERNAME");   //Recuperamos el nombre de usuario ya que puede ser útil
	}
	
	 @Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu_principal_cuidador, menu);
		return true;
	}
	 
	 @Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case R.id.agregar_persona_dependiente:
			lanzar_agregar_persona_dependiente();
			return true;
		case R.id.borrar_persona_dependiente:
			lanzar_borrar_persona_dependiente();
			return true;
		case R.id.diario_persona_dependiente:
			lanzar_diario_persona_dependiente();
			return true;
		case R.id.alertas:
			lanzar_alertas();
			return true;
		case R.id.mensajes:
			lanzar_mensajes();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void lanzar_agregar_persona_dependiente(){
		Intent i= new Intent(this, vregistrar_per_depend.class);
		startActivity(i);
	}

	public void lanzar_borrar_persona_dependiente(){
		Intent i= new Intent(this, vborrar_per_depend.class);
		startActivity(i);
	}

	 public void lanzar_diario_persona_dependiente(){
	 }

	 public void lanzar_mensajes(){
	 	Intent i= new Intent(this, vMensajes.class);
	 	startActivity(i);
	 }

	 public void lanzar_alertas(){
			 Intent i= new Intent(this, Valertas_cuidadores.class);
			 startActivity(i);
	 }

}
