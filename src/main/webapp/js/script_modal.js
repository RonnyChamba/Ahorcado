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
