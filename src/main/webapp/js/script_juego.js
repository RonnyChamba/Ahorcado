import { ajax, createQueryString } from "./ajax.js";
import { showAlert } from "./alerts.js";
import { Partida } from "./script_partida.js";

const $botonesJugar = document.querySelector(".jugar__opcion");
const $botonesLetra = document.querySelector(".jugar__item--letras");
const $verAyuda = document.querySelector(".aside-menu__item");
const $modal = document.getElementById("modal");
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
	if (event.target.id === "btn-play") {
		getCategorias();
	}
	if (event.target.id === "btn-pause") cancelPartida();
}

async function getCategorias() {

	// Validar si existe un juego activo
	if (miPartida === null) {

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
		$modal.classList.add("modal--show");
		return ;
	}
	
	showMensajesNotificacion("noRepeatJuego");

}

function cancelPartida(){
	
	
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
	$cateSelect.innerHTML = "";
	$cateSelect.appendChild(fragment);
}

function preIniciarJuego(event) {
	let element = event.target;
	if (element.matches("button")) {
		let buttonId = element.id;
		if (buttonId === "btn-iniciar-juego") getOptionSeleccionada();

		if (buttonId === "btn-cancelar-juego") {
			//console.log("cancelar");
			$modal.classList.remove("modal--show");
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


async function setDatosPartida(dataPalabra) {

	let objPalabra = JSON.parse(dataPalabra);
	miPartida = new Partida(objPalabra);

	try {

		let datosCargados = await datosOk();
		console.log(datosCargados);

		if (datosCargados) {
			$modal.classList.remove("modal--show");
		}

	} catch (error) {


	}


}

function datosOk() {

	const miPromise = new Promise((resolve, reject) => {
		// Asignar los datos de ayuda(aside)
		setOptionsAyuda();
		setPalabraImgJuego();
		resolve({ estado: true });
	});
	return miPromise;


}

function setOptionsAyuda() {
	const $helpOptions = $verAyuda.querySelectorAll(".row .help-juego");
	$helpOptions.forEach((elemento) => {

		if (elemento.classList.contains("help-categoria")) {
			elemento.textContent = miPartida.getCategoria.name;
		}
		if (elemento.classList.contains("help-desc-categoria")) {
			elemento.textContent = miPartida.getCategoria.descripcion;
		}
		if (elemento.classList.contains("help-desc-palabra")) {
			elemento.textContent = `${miPartida.getPalabra.descripcion}`;
		}
	});



}
function setPalabraImgJuego() {
	const $palabraJuego = document.getElementById("palabra-juego-main");
	$palabraJuego.textContent = miPartida.mostrarTextoPalabra();

	const $imgJuego = document.getElementById("img-juego-main");
	$imgJuego.setAttribute("src", miPartida.getPathImg);
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
		


		default:
			break;
	}


	const $alert = document.getElementById("alert");
	showAlert($alert, data);
}

