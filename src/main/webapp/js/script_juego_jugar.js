// Definición de variables globales
let miPartida = null
let timerPartida = null;
let $botonesJugar = null;
let $botonesLetra = null;
let ObjPartida = null;
let $modal = null;
let $modalContentSelect = null;
let $modalContentResultado = null;
let changeTextBtnTitle = null;
let createQueryString = null;
let createWraperMensaje = null;
let ajax = null; 

export function initComponents(partida, modal, modalContentSelect, 
								botonesJugar, botonesLetra,
								modalContentResultado,
								fnQueryString, fnAjax, fnCreateWraperMensaje,
								fnChangeTextBtnTitle){
	ObjPartida = partida;
	$modal = modal;
	$modalContentSelect = modalContentSelect;
	$botonesJugar = botonesJugar;
	$botonesLetra = botonesLetra;
	$modalContentResultado = modalContentResultado;
	createQueryString = fnQueryString;
	ajax = fnAjax;
	createWraperMensaje = fnCreateWraperMensaje;
	changeTextBtnTitle = fnChangeTextBtnTitle;	
}

// Fn recibe el  parent de los botones  iniciar, cancelar, info
function parentBotonesPartida(){
	$botonesJugar.addEventListener("click", (event) => {
	if (event.target.matches("button")) botonesPartida(event);
});	
}

// Fn recibe el parent de las letras del juego
function parentBotonesLetras(){
	$botonesLetra.addEventListener("click", (event) => clickBtnLetras(event)); // SN
}

// Fn recibe el parent modal
function parentModal(){
	$modal.addEventListener("click", (event) => preIniciarJuego(event)); // SN
}

// Fn maneja los botones iniciar,detener, info de una partida 
function botonesPartida(event) {
	let idButton = event.target.id;
	if ((idButton === "btn-play" && miPartida === null)
		|| (idButton === "btn-pause" && miPartida !== null)) {
		selectContentModal(idButton);
		return;
	}
	let mensaje = idButton === "btn-play" ? "Hay un juego Activo, no puede crear otro" : "No hay un juego Activo para detener";
	showMensajesNotificacion(mensaje); // SN
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

function dataCancelarJuego() {
	// Enviar el modal__load detras del contenido a mostrar
	$modal.querySelector(".modal__load").classList.add("modal__load--hide");
	let nuevoContent = createWraperMensaje("Seguro desea cancelar la partida"); // SN
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


