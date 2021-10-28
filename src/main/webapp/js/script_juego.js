import { ajax, createQueryString } from "./ajax.js";
import { showAlert } from "./alerts.js";

console.log("holaaaa");
const $botonesJugar = document.querySelector(".jugar__opcion");
const $botonesLetra = document.querySelector(".jugar__item--letras");
const $verAyuda = document.querySelector(".aside-menu__item");
const serlvet = "CJuego";

$botonesJugar.addEventListener("click", (event) => {
	if (event.target.matches("button")) buttonJuego(event);
});

$botonesLetra.addEventListener("click", (event) => {
	console.log(event);
});

function buttonJuego(event) {
	if (event.target.id === "btn-play") {
		consultarCategorias();
		//openModal();
	}
	if (event.target.id === "btn-pause") {
	}
}

async function consultarCategorias() {

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

		// console.log("respuesta categoria: ", responseCate);

	} catch (error) {

	}

}
function openModal() {
	document.getElementById("modal").classList.add("modal--show");
}

function setOpcionesSelect(responseCate) {

	// Arreglo de objetos(cada indice hay un arreglo como string)
	let categorias = JSON.parse(responseCate.categorias);
	const fragment = document.createDocumentFragment();
	categorias.forEach((element) => {

		let objCateSelect = JSON.parse(element);
		let option = document.createElement("OPTION");
		option.setAttribute("value", objCateSelect.id);
		option.textContent = objCateSelect.name;
		fragment.appendChild(option);
		// console.log(objCateSelect);												
	});

	let $cateSelect = document.getElementById("categoria-modal");
	$cateSelect.appendChild(fragment);
	// console.log("categoriass parseadas ", categorias);
}
