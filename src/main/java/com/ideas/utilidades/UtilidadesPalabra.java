package com.ideas.utilidades;

import org.json.simple.JSONObject;

import com.ideas.entidades.Categoria;
import com.ideas.entidades.Palabra;

public class UtilidadesPalabra {


	public static String jsonString(Palabra palabra) {

	
		JSONObject json = new JSONObject();
		json.put("id", palabra.getIdPalabra());
		json.put("name", palabra.getNombre());
		json.put("descripcion", palabra.getDescripcion());
		json.put("categoria", UtilidadesCategoria.jsonString(palabra.getCategoria()));

		return json.toJSONString();
	}

}
