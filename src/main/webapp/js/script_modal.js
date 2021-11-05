export const $modal = document.getElementById("modal");

// Contenido del modal, se crea cuando se lee el archivo(solo una vez creamos este obj)
export function createWraperCategoria() {
	let wraperSelect = document.createElement("DIV");
	wraperSelect.classList.add("modal-content-categoria");

	let selectCat = document.createElement("SELECT");
	selectCat.setAttribute("name", "categoria");
	selectCat.setAttribute("id", "modal-categoria");
	selectCat.classList.add("modal__categoria");

	let labelCategoria = document.createElement("SPAN");
	labelCategoria.textContent = "Tipo categoria:";

	wraperSelect.appendChild(labelCategoria);
	wraperSelect.appendChild(selectCat);
	return wraperSelect;
}


// Contenido del modal, se crea cuando se lee el archivo
// mostrara los detalle de la partida jugada, palabra, categoria, ect
export function createWraperDetalleJuego() {
	let wraperJuego = document.createElement("DIV");
	wraperJuego.classList.add("modal-content-detalle-juego", "moda-content-detalle-juego--flex");
	
	// columnUno contendra la clave : palabra
	let columnOptionUno = document.createElement("DIV");
	// COlumnDos contendra el valor : leon
	let columnOptionDos = document.createElement("DIV");
	
	wraperJuego.appendChild(columnOptionUno);
	wraperJuego.appendChild(columnOptionDos);
	return wraperJuego;
}


/*Modal para mostrar al ganar o perder la partida */
export function createWraperResultado() {
  let wraperResultado = document.createElement("DIV");
  wraperResultado.classList.add("modal-content-resultado");

  let img = document.createElement("IMG");
  img.setAttribute("src", "");
  img.setAttribute("id", "modal-img-resultado")	
  img.classList.add("modal-content-resultado-img");

  let mensaje = createWraperMensaje("Has Perdido la partida");
  wraperResultado.appendChild(img);
  wraperResultado.appendChild(mensaje);
  return wraperResultado;
}

// Se lo llama varias veces
export function createWraperMensaje(mensaje = "Mensaje del sistema") {
	let wraper = document.createElement("DIV");
	let mensajeModal = document.createElement("P");
	mensajeModal.textContent = mensaje;
	mensajeModal.classList.add("modal__mensaje");
	wraper.appendChild(mensajeModal);
	return wraper;
}

export function changeTextBtnTitle(datos) {
	let estado = datos === undefined || datos === null;
	$modal.querySelector(".modal__title").textContent = estado
		? "Mensaje del Sistema"
		: datos.title;
	$modal.querySelector("#modal-btn-aceptar").textContent = estado
		? "Aceptar"
		: datos.btnOk;
	$modal.querySelector("#modal-btn-cancelar").textContent = estado
		? "Cancelar"
		: datos.btnCancelar;
}

export function createWraperBotones(mensaje = "Aceptar") {
  let wraper = document.createElement("DIV");
  let mensajeModal = document.createElement("BUTTON");
  mensajeModal.textContent = mensaje;
  mensajeModal.setAttribute("id", "modal-fin-juego");
  mensajeModal.classList.add("button-accion");
  wraper.appendChild(mensajeModal);
  return wraper;
}

