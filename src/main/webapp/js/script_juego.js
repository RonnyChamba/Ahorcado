import { ajax, createQueryString } from "./ajax.js";
import { showAlert } from "./alerts.js";
import { Partida } from "./script_partida.js";

import {
	createWraperCategoria, createWraperMensaje,
	changeTextBtnTitle, createWraperResultado,
	createWraperDetalleJuego, $modal
} from "./script_modal.js";

import  {initComponents } from "./script_juego_jugar.js"; 

const $botonesJugar = document.querySelector(".jugar__opcion");
const $botonesLetra = document.querySelector(".jugar__item--letras");
const $verAyuda = document.querySelector(".aside-menu__item");
const $modalContentSelect = createWraperCategoria();
const $modalContentResultado = createWraperResultado();
const $modalContentDetalleJuego = createWraperDetalleJuego();
const $labelTimer = document.getElementById("time-js");
const serlvet = "CJuego";


const datosJuego = {
	
	partida: Partida,
	modal :$modal,
	modalContentSelect: $modalContentSelect,
	botonesJugar: $botonesJugar,
	botonesLetra : $botonesLetra,
	modalContentResultado : $modalContentResultado,
	verAyuda : $verAyuda,
	modalContentDetalleJuego: $modalContentDetalleJuego,
	labelTimer : $labelTimer,
	serlvet: serlvet,
	createQueryString : createQueryString,
	ajax: ajax,
	createWraperMensaje: createWraperMensaje,
	changeTextBtnTitle:changeTextBtnTitle,
	showAlert:showAlert	
}


initComponents (datosJuego);

console.log("LEIDO JUEGO SCRIP_JUEGO.js")