import { ajax, createQueryString } from "./ajax.js";
import { showAlert } from "./alerts.js";

const $botonesJugar = document.querySelector(".jugar__opcion");
const $botonesLetra = document.querySelector(".jugar__item--letras");
const $verAyuda = document.querySelector(".aside-menu__item");
const $modal = document.getElementById("modal");
const serlvet = "CJuego";

$botonesJugar.addEventListener("click", (event) => {
	if (event.target.matches("button")) buttonJuego(event);
});

$botonesLetra.addEventListener("click", (event) => {
	console.log(event);
});

$modal.addEventListener("click", (event) => preIniciarJuego(event));

function buttonJuego(event) {
	if (event.target.id === "btn-play") {
		getCategorias();
	}
	if (event.target.id === "btn-pause") {
	}
}

async function getCategorias() {

	openModal();
	const datos = { action: "listarCategorias" }
	let queryString = createQueryString(datos);

	try {
		let xhrCategoria = await ajax(serlvet, queryString);
		let responseCate = xhrCategoria.xhr.responseText;
		responseCate = JSON.parse(responseCate);

		if (responseCate.estado) {
			setOpcionesSelect(responseCate);
		}

	} catch (error) {

	}

}
function openModal() {
	$modal.classList.add("modal--show");
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

	let $cateSelect = $modal.querySelector("#categoria-modal");
	$cateSelect.appendChild(fragment);
}

function preIniciarJuego(event) {
	let element = event.target;
	if (element.matches("button")) {
		let buttonId = element.id;
		if (buttonId === "btn-iniciar-juego") getOptionSeleccionada();
		if (buttonId === "btn-cancelar-juego") {
			console.log("cancelar");
		}
	}
}

function getOptionSeleccionada() {
	let $optionSelect = $modal.querySelector("#categoria-modal");
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


function setDatosPartida(dataPalabra){
	
	let objPalabra= JSON.parse(dataPalabra);
	let objCatePalabra = JSON.parse(objPalabra.categoria);
	// Aqui ya puedo ir pintando los datos
	
}

function showMensajesNotificacion(tipoMensaje = "", mensaje ="") {
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

			

		default:
			break;
	}


	const $alert = document.getElementById("alert");
	showAlert($alert, data);
}

