package com.movileii.isil.pc03_rios;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ReporteActivity extends Activity implements OnItemClickListener {
	String nombrebd="BD000007";
	String nommbre;
	SQLiteDatabase basededatos=null;
	EditText txtarea;
	ArrayList<String> listado=null;
	
	ListView lista=null;
	String w[];
	protected void onCreate(Bundle savedInstanceState)	{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.reporte);

				Intent objeto = getIntent();
				nommbre = objeto.getStringExtra("nombre");
			    Conectarse();
			    w=cargarArregloSimple();
			    lista=(ListView)findViewById(R.id.LISTA);
			    ArrayAdapter<String> listadog=new ArrayAdapter<String>(this,R.layout.w,w);
			    lista.setAdapter(listadog);  
			    lista.setOnItemClickListener(this);
	}
	     public   void  Conectarse()
	     {  	 
	        try 
	        {
	            basededatos=openOrCreateDatabase(nombrebd,MODE_PRIVATE,null);
	     	
	 	   } catch (Exception e) {
	 		
	 	   }
	     }
	     public ArrayList<String> MostrarDatos()
	     {   Cursor c ;
	  	   int codigo;
	  	   String nombre , apellido , dni,acum="";
	  	   
	  	   try {
	  		  c = basededatos.rawQuery(" SELECT *  FROM persona  ",null); 
		  	   listado=new ArrayList<String>();
		  	    while(c.moveToNext())
		  	    {  	  	     
		  	       acum= c.getInt(0)+"-"+c.getString(1)+" "+c.getString(2);
		  		   listado.add(acum) ; 
		  	    }		  	       
			
		} catch (Exception e) {
			
		}
	  	 
	  return   listado;  
	     } 
	
	    
	     
	     String[]  cargarArregloSimple()
		    {
		    	String x[]=null;
		    	listado=null;
		    	int  i=0;
		    	listado=MostrarDatos();
		    	x=new String[listado.size()];
		    	
		    	for(String obj:listado)
		    	{
		    		x[i]=obj;
		    		i++;
		    	}
		    	
		    	return  x;
		    }    
	     
	public  void  seleccionar(View arg1)
	{		
		String codigocad=((TextView)arg1).getText().toString();
		String x[]=codigocad.split("-");
			
		Intent objeto=new Intent(ReporteActivity.this,MainActivity.class);
		     Bundle obj=new Bundle();
		              obj.putString("op","2");
		  	          obj.putString("codigo",x[0]);
		  	          obj.putString("nombre", nommbre);
		  	          objeto.putExtras( obj);		 
			          startActivity(objeto); 		
		
	}
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		seleccionar(arg1);
	}
	
	     
	     
	     
}
