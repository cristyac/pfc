package com.controlador.pfc.cuidadores;


import com.controlador.pfc.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.modelo.pfc.mpDependientes;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class vborrar_per_depend extends Activity {
	private ListView listView;
	private int posicionArrayList=-1;   //posición del ArrayList que pinchamos
	private ArrayList<String> arrayList = new ArrayList<>();
	private ArrayAdapter arrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vborrar_per_depend);
		listView=(ListView)findViewById(R.id.listView1);
		RellenarArrayList();     //Descarga los campos Nombre de pDependientes de la bd y los almacena en la posiciones del ArrayList
		arrayAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);   //Establece el arrayAdapter
		MostrarListview();   //Establece el ListView mediante el arrayAdapter y pone todas sus vistas a la escucha de un click.

		System.out.println("-------------------"+listView.getChildCount());

	}


	public void RellenarArrayList() {
		final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("pDependientes");
		mDatabase.orderByChild("Nombre").addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String s) {
				arrayList.add(dataSnapshot.getKey());
			}
			@Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
			@Override public void onChildRemoved(DataSnapshot dataSnapshot) {}
			@Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
			@Override public void onCancelled(DatabaseError databaseError) {}
		});
	}


	public void MostrarListview(){
		posicionArrayList =-1;//posición del listview seleccionada
		listView.setAdapter(arrayAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
				//El bucle for es necesario aunque por defecto el color sea transparente para que cada vez que se llame a este método,
				//se ponga una opción transparente si no lo estaba previamente por haber pinchado sobre ella.
				for (int j = 0; j < adapterView.getChildCount(); j++) {
					adapterView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
				}
				view.setBackgroundColor(Color.LTGRAY);
				posicionArrayList =i;   //Importante actualizar posicionArrayList ya que la utilizaremos en el módulo sepulsaeliminar
			}
		});
	}


	public void sepulsaeliminar(View view) {
		if(posicionArrayList !=-1) {
			final DatabaseReference referenciapDependienteaBorrar=FirebaseDatabase.getInstance().getReference("pDependientes").child(arrayList.get(posicionArrayList));

			referenciapDependienteaBorrar.orderByChild("Familiar").addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					if(dataSnapshot.getValue()!=null){   //Obligatorio incluir esta condición ya que al borrar referenciapDependienteaBorrar, hace de nuevo una llamada a este Familiar y el datasnapshot devuelve null y cae la App.
						mpDependientes midependiente=dataSnapshot.getValue(mpDependientes.class);
						final DatabaseReference referenciaUseraBorrar=FirebaseDatabase.getInstance().getReference("users").child(midependiente.Familiar);
						referenciapDependienteaBorrar.removeValue();// Elimina el campo de la persona dependiente de la BD
						referenciaUseraBorrar.removeValue();   //Elimina el campo del User de la BD
						arrayList.remove(posicionArrayList);   //Elimina la posición correspondiente del ArrayList
						arrayAdapter.notifyDataSetChanged();   //Función que actualiza el arrayAdapter y lo dibuja de nuevo.
					}
				}
				@Override public void onCancelled(DatabaseError databaseError) {}
			});
		}else{
			Toast.makeText(getApplicationContext(),"No se ha seleccionado ningún elemento",Toast.LENGTH_LONG).show();
		}
	}

}



