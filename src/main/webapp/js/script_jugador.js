import { validarJugador, showPass} from "./script.js";
import { ajax, createQueryString } from "./ajax.js";
import { showAlert } from "./alerts.js";

const $formJugador = document.forms["form-jugador"];
const serlvet = "CJugador";

$formJugador.addEventListener("submit", function(event) {
	event.preventDefault();
	insertJugador(this);
});

$formJugador.addEventListener("reset", function(event) {
	
	console.log(event);
	
	document.querySelector(".mensaje").innerHTML = "";
});


async function insertJugador(form) {
	let { estado, mensaje, datos } = validarJugador(form);
	document.querySelector(".mensaje").innerHTML = mensaje;
	console.log(datos);

	if (estado) {
		
		try {
			
			let queryString = createQueryString(datos);
			let xhrInsert = await ajax( serlvet,queryString);
			let responseInsert = xhrInsert.xhr.responseText;
			responseInsert = JSON.parse(responseInsert);
			
			const data = {
				sms1: {
					mensaje: responseInsert.sms,
					estado: responseInsert.estado ? "succes" : "danger"
				}
			};

			const $alert = document.getElementById("alert");
			showAlert($alert, data);

		} catch (error) {

			console.log("Error", error);

		}
	}

}

$formJugador.elements["showpass"].addEventListener ("change", (event) => showPass($formJugador, event));
	
