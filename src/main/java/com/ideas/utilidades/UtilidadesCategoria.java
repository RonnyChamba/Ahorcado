package com.ideas.utilidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ideas.entidades.Categoria;

public class UtilidadesCategoria {

	public static String jsonString(Categoria categoria) {
		
		JSONObject json = new JSONObject();
		json.put("id", categoria.getIdCategoria());
		json.put("name", categoria.getNombre());
		json.put("descripcion", categoria.getDescripcion());
		return json.toJSONString();
	}
	
	
public static String jsonStringList( List<Categoria> categorias) {
		
	JSONArray json = new JSONArray();
	
	for  (Categoria ct : categorias) 
		json.add( jsonString(ct));	
		
	return json.toJSONString();
	}



/*
public static void main (String ...rene) {

	
	Categoria c1 = new Categoria();
	c1.setNombre("COLAS");
	c1.setDescripcion("PARA BEBER");
	c1.setIdCategoria("1");
	Categoria c2 = new Categoria();
	c2.setNombre("BISUTERIA");
	c2.setDescripcion("PARA PONERSE");
	c2.setIdCategoria("2");
	
	
	List<Categoria> lista = new ArrayList<Categoria>();
	
	System.out.println(jsonStringList(lista));	
	
}
*/




}