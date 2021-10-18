package com.ideas.test;

import org.json.simple.JSONObject;

import com.ideas.entidades.Categoria;

public class TestJSON {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Categoria categoria = new Categoria();
		categoria.setIdCategoria("10");
		categoria.setNombre("COSAS");
		categoria.setDescripcion("Para las descripciones");
		
		JSONObject cateJson = new JSONObject();
		cateJson.put("id", categoria.getIdCategoria());
		cateJson.put("name", categoria.getNombre());
		cateJson.put("descripcion", categoria.getDescripcion());	
		
		JSONObject json = new JSONObject();
		json.put("estado", true);
		json.put("sms", "hola mundo");
		json.put("categoria", cateJson.toJSONString());
		
		
		System.out.println(json.toJSONString());

	}

}
