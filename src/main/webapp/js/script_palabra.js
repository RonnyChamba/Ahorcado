import { validarPalabra } from "./script.js";
import { ajax, createQueryString } from "./ajax.js";
import { showAlert } from "./alerts.js";

const serlvet = "CPalabra";
const $formPalabra = document.forms["form-palabra"];
const $formPalabraTable = document.forms["form-table-palabra"];
// Guardar el evento de un elemento, para saber en que fila estaba, utilil cuando actualizamos un registro 
let eventRowSelect = null;

$formPalabra.addEventListener("submit", function(event) {
	event.preventDefault();
	
	let $button = event.submitter;
	let action = $button.dataset["action"].toLowerCase();

	// console.log("action  formPalabra",action);
	switch (action) {

		case "insertpalabra":
			insertPalabra(this, action);
			break;
		case "actionupdatepalabra":
			 actionUpdatePalabra(this, action);
			break;

		default:
			break;

	}

});


$formPalabra.addEventListener("reset", function(event) {
	setResetForm(true);
	eventRowSelect = null;

});



$formPalabraTable.addEventListener("submit", function(event) {
	event.preventDefault();
	let $button = event.submitter;
	let datos = {
		action: $button.dataset["action"].toLowerCase(),
		id: $button.dataset.id,
		form: this
	}

	// Guardar el evento para saber luego que fila debo actualizar, utilizo en el metodo updateUpdateTable
	eventRowSelect = event;

	if (datos.action === "formupdatepalabra") {
		updatePalabra(datos);
	}
	if (datos.action === "actiondeletepalabra") {
		actionDeletePalabra(datos);
	}

});

async function insertPalabra(form, action) {
	// console.log("action:", action);
	let { estado, mensaje, datos } = validarPalabra(form);
	// console.log(datos);
	document.querySelector(".mensaje").innerHTML = mensaje;
	if (estado) {
		datos["action"] = action;
		let queryString = createQueryString(datos);
		try {

			let xhrInsert = await ajax(serlvet,queryString);
			let responseInsert = xhrInsert.xhr.responseText;
			responseInsert = JSON.parse(responseInsert);
			let responseUltima = null;

			// Verifico si fue correcto el insert para actualizar el registro en la tabla
			if (responseInsert.estado) {

				// Consultar la ultima palabra registrada
				queryString = createQueryString({ action: "ultimaPalabra" });
				let xhrUltima = await ajax( serlvet,queryString);
				responseUltima = xhrUltima.xhr.responseText;
				responseUltima = JSON.parse(responseUltima);
				if (responseUltima.estado) insertUpdateTable(responseUltima.palabra);
			}

			const data = {
				sms1: {
					mensaje: responseInsert.sms,
					estado: responseInsert.estado ? "succes" : "danger"
				},
				sms2: {
					mensaje: responseUltima.sms,
					estado: responseUltima.estado ? "succes" : "danger"
				}
			};

			const $alert = document.getElementById("alert");
			showAlert($alert, data);

		} catch (error) {

			console.log("Error", error);

		}

	}

}



function insertUpdateTable(palabraNueva) {

	palabraNueva = JSON.parse(palabraNueva);

	const $tableBody = document.getElementById("table-body");
	let $row = document.createElement("TR");
	$row.classList.add("table-row-insert");

	let celda = document.createElement("TD");
	celda.classList.add("table__celda", "table__celda--td");
	celda.textContent = palabraNueva.id;

	let celda2 = document.createElement("TD");
	celda2.classList.add("table__celda", "table__celda--td");
	celda2.textContent = palabraNueva.name;

	let celda3 = document.createElement("TD");
	celda3.classList.add("table__celda", "table__celda--td");
	celda3.textContent = palabraNueva.descripcion;
	
	
	let celda4 = document.createElement("TD");
	celda4.classList.add("table__celda", "table__celda--td");
	let categoria= JSON.parse(palabraNueva.categoria);
	// console.log("categoriaaaaa", categoria);
	celda4.textContent = categoria.name;


	let celda5 = document.createElement("TD");
	celda5.classList.add("table__celda", "table__celda--btn", "table__celda--td");

	let btnUpdate = document.createElement("INPUT");
	btnUpdate.setAttribute("type", "submit");
	btnUpdate.setAttribute("value", "Update");
	btnUpdate.setAttribute("name", "action");
	btnUpdate.dataset["action"] = "formupdatePalabra";
	btnUpdate.dataset["id"] = palabraNueva.id;
	btnUpdate.classList.add("btn", "btn--info", "table__btn");
	btnUpdate.style.marginRight = "4px";

	let btnDelete = document.createElement("INPUT");
	btnDelete.setAttribute("type", "submit");
	btnDelete.setAttribute("value", "Delete");
	btnDelete.setAttribute("name", "action");
	btnDelete.dataset["action"] = "actionDeletePalabra";
	btnDelete.dataset["id"] = palabraNueva.id;
	btnDelete.classList.add("btn", "btn--danger", "table__btn");


	celda5.appendChild(btnUpdate);
	celda5.appendChild(btnDelete);

	$row.appendChild(celda);
	$row.appendChild(celda2);
	$row.appendChild(celda3);
	$row.appendChild(celda4);
	$row.appendChild(celda5);

	$tableBody.insertAdjacentElement("afterbegin", $row);


	setTimeout(() => $row.classList.remove("table-row-insert"), 4000);
}



async function updatePalabra(dataSubmit) {

	try {
		let queryString = createQueryString({ id: dataSubmit.id, action: dataSubmit.action });
		// console.log("Query string ", queryString);
		let xhrUpdate = await ajax( serlvet,queryString);
		// console.log(xhrUpdate);
		let responseUpdate = xhrUpdate.xhr.responseText;
		responseUpdate = JSON.parse(responseUpdate);

		// Poner datos en las cajas de texto
		if (responseUpdate.estado) {
			setResetForm(false);
			setDataForm(responseUpdate.palabra);
			return;
		}

		const data = {
			sms1: {
				mensaje: responseUpdate.sms,
				estado: responseUpdate.estado ? "succes" : "danger"
			}
		};

		const $alert = document.querySelector(".alert");
		showAlert($alert, data);

	} catch (error) {
	
	console.log("Error ", error);
	}

}

async function actionUpdatePalabra(form, action) {

	let { estado, mensaje, datos } = validarPalabra(form);
	document.querySelector(".mensaje").innerHTML = mensaje;

	if (estado) {

		datos["action"] = action;

		let queryString = createQueryString(datos);
		try {

			let xhrUpdate = await ajax( serlvet, queryString);
			let responseUpdate = xhrUpdate.xhr.responseText;
			responseUpdate = JSON.parse(responseUpdate);
			const data = {
				sms1: {
					mensaje: responseUpdate.sms,
					estado: responseUpdate.estado ? "succes" : "danger"
				}
			};
			
			if (responseUpdate.estado) {

				// Consultar el registro actualizado para actualizar la fila del registro
				queryString = createQueryString({ id: datos.id, action: "actionBuscarPalabra" });
				let xhrSelect = await ajax(serlvet, queryString);
				let responseSelect = xhrSelect.xhr.responseText;
				responseSelect = JSON.parse(responseSelect);

				//console.log(responseSelect);
				if (responseSelect.estado) updateUpdateTable(responseSelect.palabra);
				data["sms2"] = {

					mensaje: responseSelect.sms,
					estado: responseSelect.estado ? "succes" : "danger"
				}
			
			}

			const $alert = document.getElementById("alert");
			showAlert($alert, data);

		} catch (error) {

			console.log("Error", error);

		}

	}
}

// asignar valores de una categoria a los campos del form categoria

function setDataForm(palabra) {
	let newPalabra = JSON.parse(palabra);

	$formPalabra.elements["id"].value = newPalabra.id;
	$formPalabra.elements["name"].value = newPalabra.name;
	$formPalabra.elements["descripcion"].value = newPalabra.descripcion;
	let categoria = JSON.parse(newPalabra.categoria);
	let index = indexOption(categoria.id);
	if (index !==null) $formPalabra.elements["categoria"].selectedIndex = index;
}

function indexOption(valueId) {
  const $select = $formPalabra.elements["categoria"];
  let options = [...$select.options];
  let option = options.find((x) => x.value === valueId);
  if (option !== undefined && option !== null) {
	return option.index;
  }
return null;
}



function updateUpdateTable(palabraUpdate) {

	let palabraNueva = JSON.parse(palabraUpdate);
	// Fila actulizar: padre del boton/ padre de la celda = fila donde estaba el boton
	let rowUpdate = eventRowSelect.submitter.parentElement.parentElement;
	let celdas = [...rowUpdate.cells];
	celdas[0].textContent = palabraNueva.id;
	celdas[1].textContent = palabraNueva.name;
	celdas[2].textContent = palabraNueva.descripcion;
	celdas[3].textConten = JSON.parse(palabraNueva.categoria).name;
	
}


async function actionDeletePalabra(dataSubmit) {

	const datos = {
		id: dataSubmit.id,
		action: dataSubmit.action
	}


	let queryString = createQueryString(datos);
	try {

		let xhrDelete = await ajax( serlvet,queryString);
		let responseDelete = xhrDelete.xhr.responseText;
		responseDelete = JSON.parse(responseDelete);
		const data = {
			sms1: {
				mensaje: responseDelete.sms,
				estado: responseDelete.estado ? "succes" : "danger"
			}
		};
		
		if (responseDelete.estado) {
			// Eliminar la fila de la tabla
			let rowDelete = eventRowSelect.submitter.parentElement.parentElement;
			rowDelete.remove();
		}
		
		const $alert = document.getElementById("alert");
		showAlert($alert, data);

	} catch (error) {

		console.log("Error", error);

	}
}


// true: reset
// false: asignar
function setResetForm(estado) {

	document.querySelector(".mensaje").innerHTML = "";

	// Cambiar los datos de los botones de accion
	let $btnGuardar = $formPalabra.elements["guardar"];
	let $btnReset = $formPalabra.elements["reset"];
	$btnGuardar.dataset.action = estado ? "insertPalabra" : "ActionUpdatePalabra";
	$btnGuardar.setAttribute("value", estado ? "Guardar" : "Guardar Cambios");
	$btnReset.setAttribute("value", estado ? "Limpiar" : "Cancelar");

}

