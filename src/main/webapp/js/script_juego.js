import { ajax, createQueryString } from "./ajax.js";
import { showAlert } from "./alerts.js";
import { Partida } from "./script_partida.js";

import { createWraperCategoria, createWraperMensaje, changeTextBtnTitle, $modal } from "./script_modal.js";

const $botonesJugar = document.querySelector(".jugar__opcion");
const $botonesLetra = document.querySelector(".jugar__item--letras");
const $verAyuda = document.querySelector(".aside-menu__item");
const $modalContentSelect = createWraperCategoria();
// const $modal = document.getElementById("modal");
const serlvet = "CJuego";
let miPartida = null

$botonesJugar.addEventListener("click", (event) => {
	if (event.target.matches("button")) buttonJuego(event);
});

$botonesLetra.addEventListener("click", (event) => {
	console.log(event);
});

$modal.addEventListener("click", (event) => preIniciarJuego(event));

function buttonJuego(event) {

	let idButton = event.target.id;
	let mensaje = "";

	if ((idButton === "btn-play" && miPartida === null)
		|| (idButton === "btn-pause" && miPartida !== null)) {
		selectContentModal(idButton);
		return;
	}
	mensaje = idButton === "btn-play" ? "noRepeatJuego" : "noActiveJuego";
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

function cancelPartida() {

	setOptionsAyuda(false);
	setPalabraImgJuego(false);	
	miPartida = null;
	$modal.classList.remove("modal--show");
	
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
		let buttonId = element.id;

		// Nuevo Juego
		if (buttonId === "modal-btn-aceptar" && miPartida === null) getOptionSeleccionada();

		// Seguir Jugando
		if (buttonId === "modal-btn-aceptar" && miPartida !== null) $modal.classList.remove("modal--show");

		// Opcion Cancelada nuevo Juego
		if (buttonId === "modal-btn-cancelar" && miPartida === null) $modal.classList.remove("modal--show");

		// Cancelar Partida en Juego
		if (buttonId === "modal-btn-cancelar" && miPartida !== null) cancelPartida();
	}
}

function getOptionSeleccionada() {
	let $optionSelect = $modal.querySelector("#modal-categoria");
	let index = $optionSelect.selectedIndex;
	if (index === -1) {
		showMensajesNotificacion("noCategoria");
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

		showMensajesNotificacion("noPalabraCate", responsePalabra.sms);
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
		setPalabraImgJuego();
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
function setPalabraImgJuego(tipoAccion = true) {
	
	/* 
	true: nuevo juego
	false: resetear
	 */
	const $palabraJuego = document.getElementById("palabra-juego-main");
	$palabraJuego.textContent = tipoAccion? miPartida.mostrarTextoPalabra(): "COMIENZA A JUGAR";

	const $imgJuego = document.getElementById("img-juego-main"); 
	$imgJuego.setAttribute("src", tipoAccion? miPartida.getPathImg: "img/img-estado/ahor0.png");
}

function showMensajesNotificacion(tipoMensaje = "", mensaje = "") {
	const data = {};
	switch (tipoMensaje) {
		case "noCategoria":
			data["sms1"] = {
				mensaje: "Tiene que seleccionar una categoria",
				estado: "warning"
			}

			break;

		case "noPalabraCate":
			data["sms1"] = {
				mensaje,
				estado: "warning"
			}

			break;
		case "noRepeatJuego":
			data["sms1"] = {
				mensaje: "Existe un juego activo, no puede crear otro",
				estado: "warning"
			}

			break;
		case "noActiveJuego":
			data["sms1"] = {
				mensaje: "No exite un juego activo para cancelar",
				estado: "warning"
			}

			break;



		default:
			break;
	}


	const $alert = document.getElementById("alert");
	showAlert($alert, data);
}

console.log("LEIDO JUEGO")