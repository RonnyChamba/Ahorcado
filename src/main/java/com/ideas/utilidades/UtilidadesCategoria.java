package com.ideas.utilidades;

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

}
