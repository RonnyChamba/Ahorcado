
/*
Form Categoria
*/
export function validarCategoria(form) {
	let id = form.elements["id"].value;
	let name = form.elements["name"].value;
	let descripcion = form.elements["descripcion"].value;
	const datos = {
		id,
		name,
		descripcion
	};

	let mensaje = name === "" ? "<p>Ingrese un nombre para la categoria</p>" : "";
	let estado = name !== "";
	return { estado, mensaje, datos };
}

/* Form Jugador */

export function validarJugador(form) {
	let action = form.elements["action"].value;
	let id = form.elements["id"].value;
	let tipoJugador = form.elements["tipoJugador"].value;
	let dni = form.elements["dni"].value;
	let name = form.elements["name"].value;
	let ciudad = form.elements["ciudad"].value;
	let clave = form.elements["clave"].value;
	let puntaje = form.elements["puntaje"].value;
	
	const datos = {
		action,
		id,
		tipoJugador,
		dni,
		name,
		ciudad,
		clave,
		puntaje
	};

	let mensaje = "";
	if (dni ==="")
		mensaje = "<p>Ingrese su dni</p>";
		
	if (name ==="")
		mensaje += "<p>Ingrese su nombre</p>";
		
	if (clave ==="")
		mensaje += "<p>Ingrese una clave</p>";
	
	let estado = mensaje === "";
	return { estado, mensaje, datos };
}




/* 
Form Login
*/
export function validarLogin(form) {

	let user = form.elements["user"].value;
	let clave = form.elements["clave"].value;
	
	const datos = {
		user,
		clave
	}
		
	let estado = true;
	
	let mensaje = "";
	if (user === "")  {
		estado = false;		
		mensaje = `<p>Ingrese su usuario</p>`;
	}

	if (clave === "") {
		estado = false;
		mensaje += `<p>Ingrese su contraseña</p>`;
	} 
	
	
	return {estado, mensaje, datos};	
}

export function showPass(form, event){	
		let $inputClave = form.elements["clave"];  
		if (event.target.checked)   $inputClave.setAttribute("type", "text");
		else   $inputClave.setAttribute("type", "password");
}


