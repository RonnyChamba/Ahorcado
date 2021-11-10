import { ajax, createQueryString } from "./ajax.js";
import { showAlert } from "./alerts.js";
import { Partida } from "./script_partida.js";

import {
	createWraperCategoria, createWraperMensaje,
	changeTextBtnTitle, createWraperResultado,
	createWraperDetalleJuego, $modal
} from "./script_modal.js";

const $botonesJugar = document.querySelector(".jugar__opcion");
const $botonesLetra = document.querySelector(".jugar__item--letras");
const $verAyuda = document.querySelector(".aside-menu__item");
const $modalContentSelect = createWraperCategoria();
const $modalContentResultado = createWraperResultado();
const $modalContentDetalleJuego = createWraperDetalleJuego();
// const $modal = document.getElementById("modal");
const serlvet = "CJuego";
let miPartida = null

$botonesJugar.addEventListener("click", (event) => {
	if (event.target.matches("button")) buttonJuego(event);
});

$botonesLetra.addEventListener("click", (event) => clickBtnLetras(event));

$modal.addEventListener("click", (event) => preIniciarJuego(event));

function buttonJuego(event) {

	let idButton = event.target.id;
	if ((idButton === "btn-play" && miPartida === null)
		|| (idButton === "btn-pause" && miPartida !== null)) {
		selectContentModal(idButton);
		return;
	}
	let mensaje = idButton === "btn-play" ? "Hay un juego Activo, no puede crear otro" : "No hay un juego Activo para detener";
	showMensajesNotificacion(mensaje);
}

async function selectContentModal(tipoAccionModal) {

	try {

		// Hacer visible modal enseguida se pulse el boton
		$modal.classList.add("modal--show");
		let nuevoContentShow = null;
		let dataSmsGeneral = {};

		if (tipoAccionModal === "btn-play") {
			let data = await dataJuego();
			nuevoContentShow = data.nuevoContent;
			dataSmsGeneral = data.sms;
		} else if (tipoAccionModal === "btn-pause") {
			let data = dataCancelarJuego();
			nuevoContentShow = data.nuevoContent;
			dataSmsGeneral = data.sms;
			// Se lanza cuando acierto o pierdo la partida(se ejecuta automaticamente)	
		} else if (tipoAccionModal === "fin-juego") {
			let data = dataFinPartidaJuego();
			nuevoContentShow = data.nuevoContent;
			dataSmsGeneral = data.sms;
		}


		// Wraper para del nuevo contenido  modal
		let $modalContent = $modal.querySelector(".modal__item-content");
		// Limpiar contenido
		$modalContent.innerHTML = "";
		// Add contenido nuevo en la seccion contenido del modal
		$modalContent.insertAdjacentElement("afterbegin", nuevoContentShow);
		// Datos del title y botones de accion
		changeTextBtnTitle(dataSmsGeneral);

	} catch (error) {
		console.log("Errro general ", error);
	}
}

async function dataJuego() {
	let estado = await getCategorias();

	let datosMensaje = {
		title: "Seleccionar Categoria",
		btnOk: "Iniciar Juego",
		btnCancelar: "Cancelar",
	};
	$modal.querySelector(".modal__load").classList.add("modal__load--hide");
	return { nuevoContent: $modalContentSelect, sms: datosMensaje };
}

function dataCancelarJuego() {
	// Enviar el modal__load detras del contenido a mostrar
	$modal.querySelector(".modal__load").classList.add("modal__load--hide");
	let nuevoContent = createWraperMensaje("Seguro desea cancelar la partida");
	let datosMensaje = {
		title: "Cancelar Partida",
		btnOk: "Seguir Jugando",
		btnCancelar: "Detener Juego",
	};
	return { nuevoContent, sms: datosMensaje };
}


function dataFinPartidaJuego() {
	// Enviar el modal__load detras del contenido a mostrar
	$modal.querySelector(".modal__load").classList.add("modal__load--hide");

	let nuevoContent = $modalContentResultado;
	let datosMensaje = {
		title: "Fin del Juego",
		btnOk: "Aceptar",
		btnCancelar: "Ver Detalles",
	};
	return { nuevoContent, sms: datosMensaje };
}



async function getCategorias() {
	try {
		const datos = { action: "listarCategorias" };
		let queryString = createQueryString(datos);
		let xhrCategoria = await ajax(serlvet, queryString);
		let responseCate = xhrCategoria.xhr.responseText;
		responseCate = JSON.parse(responseCate);

		if (responseCate.estado) {
			setOpcionesSelect(responseCate);
			return Promise.resolve({ estado: true });
		}

	} catch (error) {
		return Promise.reject({ estado: false, error });
	}
}


function setOpcionesSelect(responseCate) {

	// Arreglo de objetos(cada indice hay un arreglo como string)
	let categorias = JSON.parse(responseCate.categorias);
	const fragment = document.createDocumentFragment();
	categorias.forEach(element => {
		let objCateSelect = JSON.parse(element);
		let option = document.createElement("OPTION");

		option.setAttribute("value", objCateSelect.id);
		option.textContent = objCateSelect.name;
		fragment.appendChild(option);
	});

	// Add fragmento con las opiones al select
	let $cateSelect = $modalContentSelect.querySelector("#modal-categoria");
	$cateSelect.innerHTML = "";
	$cateSelect.appendChild(fragment);

	//Add el contenedor actualizados de las categorias al contenido del modal	
	let $modalContent = $modal.querySelector(".modal__item-content");
	$modalContent.innerHTML = "";
	$modalContent.appendChild($modalContentSelect);

}

function preIniciarJuego(event) {
	let element = event.target;
	if (element.matches("button")) {
		let buttonAction = element.dataset["action"];
		// console.log("Data Action : ", element.dataset);

		// Nuevo Juego
		if (buttonAction === "modal-btn-aceptar" && miPartida === null) getOptionSeleccionada();

		// Seguir Jugando
		if (buttonAction === "modal-btn-aceptar" && miPartida !== null) $modal.classList.remove("modal--show");

		// Opcion Cancelada nuevo Juego
		if (buttonAction === "modal-btn-cancelar" && miPartida === null) $modal.classList.remove("modal--show");

		// Cancelar Partida en Juego
		if (buttonAction === "modal-btn-cancelar" && miPartida !== null) cancelPartida();

		// Finaliza el juego(pierda o gane) y acepta 
		if (buttonAction === "modal-btn-aceptar-fin-juego") reiniciaPartida();
		

		// Detalles del juego despues de finalizar 
		if (buttonAction === "modal-btn-detalles-fin-juego") detallesJuego();



	}

}

function getOptionSeleccionada() {
	let $optionSelect = $modal.querySelector("#modal-categoria");
	let index = $optionSelect.selectedIndex;
	if (index === -1) {
		showMensajesNotificacion("Tiene que seleccionar una categoria");
		return;
	}

	let idCate = $optionSelect.options[index].value;
	getDatosPalabra(idCate);
}

async function getDatosPalabra(idCategoria) {
	const datos = { action: "datosPalabra", idCategoria }
	let queryString = createQueryString(datos);
	try {

		let xhrPalabra = await ajax(serlvet, queryString);
		let responsePalabra = xhrPalabra.xhr.responseText;
		responsePalabra = JSON.parse(responsePalabra);

		if (responsePalabra.estado) {
			// Pintar los datos para jugar
			setDatosPartida(responsePalabra.palabra);
			return;
		}

		showMensajesNotificacion(responsePalabra.sms);
	} catch (error) {

	}

}


async function setDatosPartida(dataPalabra) {

	let objPalabra = JSON.parse(dataPalabra);
	miPartida = new Partida(objPalabra);

	try {

		if (await datosOk()) $modal.classList.remove("modal--show");
	} catch (error) {

	}
}

function datosOk() {

	const miPromise = new Promise((resolve, reject) => {
		// Asignar los datos de ayuda(aside)
		setOptionsAyuda(true);
		setPalabraImgJuego(true);
		resolve({ estado: true });
	});
	return miPromise;
}

function setOptionsAyuda(tipoAccion = true) {
	/* 
	true: nuevo juego
	false: resetear todo
	 */
	const $helpOptions = $verAyuda.querySelectorAll(".row .help-juego");
	$helpOptions.forEach((elemento) => {

		if (elemento.classList.contains("help-categoria")) {
			elemento.textContent = tipoAccion ? miPartida.getCategoria.name : "";
		}
		if (elemento.classList.contains("help-desc-categoria")) {
			elemento.textContent = tipoAccion ? miPartida.getCategoria.descripcion : "";
		}
		if (elemento.classList.contains("help-desc-palabra")) {
			elemento.textContent = tipoAccion ? `${miPartida.getPalabra.descripcion}` : "";
		}
	});


}

function cancelPartida() {
	setOptionsAyuda(false);
	setPalabraImgJuego(false);
	miPartida = null;
	$modal.classList.remove("modal--show");

}

function setPalabraImgJuego(tipoAccion = true) {

	/* 
	true: nuevo juego
	false: resetear
	 */
	const $palabraJuego = document.getElementById("palabra-juego-main");
	$palabraJuego.textContent = tipoAccion ? miPartida.mostrarTextoPalabra() : "COMIENZA A JUGAR";

	const $imgJuego = document.getElementById("img-juego-main");
	$imgJuego.setAttribute("src", tipoAccion ? miPartida.getPathImg : "img/img-estado/ahor0.png");
}

function showMensajesNotificacion(mensaje = "Mensaje Sin Especificar", estado = "warning") {

	const data = {
		sms1: {
			mensaje,
			estado
		}
	};
	const $alert = document.getElementById("alert");
	showAlert($alert, data);
}

function clickBtnLetras(event) {

	if (miPartida !== null) {

		let typeElement = event.target;
		let letraPresionada = typeElement.textContent;
		// Verificar si es button  y ademas que si ya fue presionada o no anteriormente
		if (typeElement.matches("button") && !miPartida.getLetrasPulsadas.includes(letraPresionada)) {

			// Cambiar el tipo de cursor para el elemento presionado
			typeElement.classList.add("btn-cursor-no-pointer");
			let isContentLetra = miPartida.verificarLetraContenida(letraPresionada);
			setPalabraImgJuego(true);
			if (miPartida.isFinJuego) {
				// Guardar en la base de datos
				aceptarFinPartida();

				$modalContentResultado.querySelector("#modal-img-resultado").setAttribute("src", miPartida.getPathImg);
				$modalContentResultado.querySelector(".modal__mensaje").textContent = miPartida.getSmsFinJuego;
				$modal.querySelector("#modal-btn-aceptar").dataset["action"] = "modal-btn-aceptar-fin-juego";
				$modal.querySelector("#modal-btn-cancelar").dataset["action"] = "modal-btn-detalles-fin-juego";
				selectContentModal("fin-juego");
			}
		}
		return;
	}

	showMensajesNotificacion("No existe una partida para jugar, crea una nueva ...");
	// console.log("No existe una partida actual");
}

// Cuando se gane o pierda
function aceptarFinPartida() {
	let idPalabra = miPartida.getPalabra.id;
	let puntaje = miPartida.getPuntos;
	let tiempo = 30;
	let fechaJuego = miPartida.getFechaJuego;
	const datos = {
		action: "guardarJuego",
		idPalabra,
		puntaje,
		tiempo,
		fechaJuego
	}

	guardarJuegoBD(datos);
	// cancelPartida(); // llamar despues de guardarJuegos BD
	//$modal.querySelector("#modal-btn-aceptar").dataset["action"] = "modal-btn-aceptar";
	// $modal.querySelector("#modal-btn-cancelar").dataset["action"] = "modal-btn-cancelar";
}

async function guardarJuegoBD(datos) {

	try {
		let queryString = createQueryString(datos);
		let xhrGuadarJuego = await ajax(serlvet, queryString);
		let responseJuego = xhrGuadarJuego.xhr.responseText;
		responseJuego = JSON.parse(responseJuego);
		showMensajesNotificacion(responseJuego.sms, responseJuego.estado ? "succes" : "warning");



	} catch (error) {
		// Error de ajax o error de este bloque
		console.log("Error en guarsar Juego ", error);
	} finally {

	}
}

// Despues de pulsar aceptar del modal cuando finaliza la partida
function reiniciaPartida() {

	$modal.querySelector("#modal-btn-aceptar").dataset["action"] = "modal-btn-aceptar";
	$modal.querySelector("#modal-btn-cancelar").dataset["action"] = "modal-btn-cancelar";
	cancelPartida();
}

function detallesJuego() {
	console.log("click en detalles");
	// Cambiar el action de los botones del modal
	$modal.querySelector("#modal-btn-aceptar").dataset["action"] = "modal-btn-aceptar";
	$modal.querySelector("#modal-btn-cancelar").dataset["action"] = "modal-btn-cancelar";

	let fragment = document.createDocumentFragment();

	let opcionPalabra = document.createElement("P");
	opcionPalabra.innerHTML = `
	<span class ="detalle-juego__option detalle-juego__option--bold"> Palabra: </span> : <span class ="detalle-juego__option detalle-juego__option--value"> ${miPartida.getPalabra.name} </span> `;

	let opcionDescripcion = document.createElement("P");
	opcionDescripcion.innerHTML = `
	<span class ="detalle-juego__option detalle-juego__option--bold"> Descripción: </span> : <span class ="detalle-juego__option detalle-juego__option--value"> ${miPartida.getPalabra.descripcion} </span> `;


	let opcionCategoria = document.createElement("P");
	opcionCategoria.innerHTML = `
	<span class ="detalle-juego__option detalle-juego__option--bold"> Categoria: </span> : <span class ="detalle-juego__option detalle-juego__option--value"> ${miPartida.getCategoria.name} </span> `;


	let opcionletrasPulsadas = document.createElement("P");
	opcionletrasPulsadas.innerHTML = `
	<span class ="detalle-juego__option detalle-juego__option--bold"> Letras Pulsadas: </span> : <span class ="detalle-juego__option detalle-juego__option--value"> ${miPartida.getLetrasPulsadas} </span> `;

	let opcionLabel = document.createElement("P");
	opcionLabel.innerHTML = `
	<span class ="detalle-juego__option detalle-juego__option--bold"> Palabra en Juego: </span> : <span class ="detalle-juego__option detalle-juego__option--value"> ${miPartida.mostrarTextoPalabra()} </span> `;

	fragment.appendChild(opcionPalabra);
	fragment.appendChild(opcionDescripcion);
	fragment.appendChild(opcionCategoria);
	fragment.appendChild(opcionletrasPulsadas);
	fragment.appendChild(opcionLabel);

	$modalContentDetalleJuego.querySelector("#content-detalle-js").appendChild(fragment);

	// Wraper para del nuevo contenido  modal
	let $modalContent = $modal.querySelector(".modal__item-content");
	// Limpiar contenido
	$modalContent.innerHTML = "";
	// Add contenido nuevo en la seccion contenido del modal
	$modalContent.insertAdjacentElement("afterbegin", $modalContentDetalleJuego);

	console.log("partida ", miPartida.getPalabra);

}

console.log("LEIDO JUEGO")