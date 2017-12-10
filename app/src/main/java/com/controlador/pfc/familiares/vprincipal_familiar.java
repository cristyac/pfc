package com.controlador.pfc.familiares;

import com.controlador.pfc.R;
import com.controlador.pfc.vChat;
import com.controlador.pfc.vlogin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class vprincipal_familiar extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//mostramos la vista
		setContentView(R.layout.vprincipal_familiar);
	}
	
	//MENU
	 @Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.menu_principal_familiar, menu);
		return true;
	}
	 
	 @Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case R.id.diario_persona_dependiente:
			lanzar_diario_persona_dependiente();
			return true;
		case R.id.mensajes:
			lanzar_mensajes();
			return true;
		case R.id.alertas:
			lanzar_alertas();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	 
	//lanzadores de actividades
	 public void lanzar_diario_persona_dependiente(){
		 Intent i= new Intent(this, vDiario_familiar.class);
		 startActivity(i);
	 }
	 public void lanzar_mensajes(){
			Intent i= new Intent(this, vChat.class);
		 	SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			String nombrePDependiente = datos.getString("pDependiente", "");
		 	i.putExtra("nombrePDependiente",nombrePDependiente);
		 	startActivity(i);
	 }
	 public void lanzar_alertas(/*View view*/){
			// Intent i= new Intent(this, Acercade.class);
			 //startActivity(i);
	 }
	public void cerrar_sesion(View view){
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
