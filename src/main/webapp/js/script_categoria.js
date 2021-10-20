import { validarCategoria } from "./script.js";
import { ajax, createQueryString } from "./ajax.js";
import { showAlert } from "./alerts.js";

const serlvet = "CCategoria";
const $formCategoria = document.forms["form-categoria"];
const $formCategoriaTable = document.forms["form-table-categoria"];
// Guardar el evento de un elemento, para saber en que fila estaba, utilil cuando actualizamos un registro 
let eventRowSelect = null;

$formCategoria.addEventListener("submit", function(event) {
	event.preventDefault();
	
	let $button = event.submitter;
	let action = $button.dataset["action"].toLowerCase();

	// console.log("action ",action);
	switch (action) {

		case "insertcategoria":
			insertCategoria(this, action);
			break;
		case "actionupdatecategoria":
			actionUpdateCategoria(this, action);
			break;

		default:
			break;

	}

});


$formCategoria.addEventListener("reset", function(event) {
	setResetForm(true);
	eventRowSelect = null;

});



$formCategoriaTable.addEventListener("submit", function(event) {
	event.preventDefault();
	let $button = event.submitter;
	let datos = {
		action: $button.dataset["action"].toLowerCase(),
		id: $button.dataset.id,
		form: this
	}

	// Guardar el evento para saber luego que fila debo actualizar, utilizo en el metodo updateUpdateTable
	eventRowSelect = event;

	if (datos.action === "formupdatecategoria") {
		updateCategoria(datos);
	}
	if (datos.action === "actiondeletecategoria") {
		actionDeleteCategoria(datos);
	}

});




async function insertCategoria(form, action) {


	let { estado, mensaje, datos } = validarCategoria(form);
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

				// Consultar la ultima categoria registrada
				queryString = createQueryString({ action: "ultimaCategoria" });
				let xhrUltima = await ajax( serlvet,queryString);
				responseUltima = xhrUltima.xhr.responseText;
				responseUltima = JSON.parse(responseUltima);
				if (responseUltima.estado) insertUpdateTable(responseUltima.categoria);
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

function insertUpdateTable(categoriaNueva) {

	categoriaNueva = JSON.parse(categoriaNueva);

	const $tableBody = document.getElementById("table-body");
	let $row = document.createElement("TR");
	$row.classList.add("table-row-insert");

	let celda = document.createElement("TD");
	celda.classList.add("table__celda", "table__celda--td");
	celda.textContent = categoriaNueva.id;

	let celda2 = document.createElement("TD");
	celda2.classList.add("table__celda", "table__celda--td");
	celda2.textContent = categoriaNueva.name;

	let celda3 = document.createElement("TD");
	celda3.classList.add("table__celda", "table__celda--td");
	celda3.textContent = categoriaNueva.descripcion;


	let celda4 = document.createElement("TD");
	celda4.classList.add("table__celda", "table__celda--btn", "table__celda--td");

	let btnUpdate = document.createElement("INPUT");
	btnUpdate.setAttribute("type", "submit");
	btnUpdate.setAttribute("value", "Update");
	btnUpdate.setAttribute("name", "action");
	btnUpdate.dataset["action"] = "formUpdateCategoria";
	btnUpdate.dataset["id"] = categoriaNueva.id;
	btnUpdate.classList.add("btn", "btn--info", "table__btn");
	btnUpdate.style.marginRight = "4px";

	let btnDelete = document.createElement("INPUT");
	btnDelete.setAttribute("type", "submit");
	btnDelete.setAttribute("value", "Delete");
	btnDelete.setAttribute("name", "action");
	btnDelete.dataset["action"] = "actionDeleteCategoria";
	btnDelete.dataset["id"] = categoriaNueva.id;
	btnDelete.classList.add("btn", "btn--danger", "table__btn");


	celda4.appendChild(btnUpdate);
	celda4.appendChild(btnDelete);

	$row.appendChild(celda);
	$row.appendChild(celda2);
	$row.appendChild(celda3);
	$row.appendChild(celda4);

	$tableBody.insertAdjacentElement("afterbegin", $row);


	setTimeout(() => $row.classList.remove("table-row-insert"), 4000);
}

async function updateCategoria(dataSubmit) {

	try {
		let queryString = createQueryString({ id: dataSubmit.id, action: dataSubmit.action });
		let xhrUpdate = await ajax( serlvet,queryString);
		let responseUpdate = xhrUpdate.xhr.responseText;
		responseUpdate = JSON.parse(responseUpdate);

		// Poner datos en las cajas de texto
		if (responseUpdate.estado) {
			setResetForm(false);
			setDataForm(responseUpdate.categoria);
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

	}

}

async function actionUpdateCategoria(form, action) {

	let { estado, mensaje, datos } = validarCategoria(form);
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
				queryString = createQueryString({ id: datos.id, action: "actionBuscarCategoria" });
				let xhrSelect = await ajax(serlvet, queryString);
				let responseSelect = xhrSelect.xhr.responseText;
				responseSelect = JSON.parse(responseSelect);

				//console.log(responseSelect);
				if (responseSelect.estado) updateUpdateTable(responseSelect.categoria);
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

/*
asignar valores de una categoria a los campos del form categoria
*/
function setDataForm(categoria) {
	let newCategoria = JSON.parse(categoria);

	$formCategoria.elements["id"].value = newCategoria.id;
	$formCategoria.elements["name"].value = newCategoria.name;
	$formCategoria.elements["descripcion"].value = newCategoria.descripcion;

}


function updateUpdateTable(categoriaUpdate) {

	let categoriaNueva = JSON.parse(categoriaUpdate);
	// Fila actulizar: padre del boton/ padre de la celda = fila donde estaba el boton
	let rowUpdate = eventRowSelect.submitter.parentElement.parentElement;
	let celdas = [...rowUpdate.cells];
	celdas[0].textContent = categoriaNueva.id;
	celdas[1].textContent = categoriaNueva.name;
	celdas[2].textContent = categoriaNueva.descripcion;
}


async function actionDeleteCategoria(dataSubmit) {

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
/*

true: reset
false: asignar
 */
function setResetForm(estado) {

	document.querySelector(".mensaje").innerHTML = "";

	// Cambiar los datos de los botones de accion
	let $btnGuardar = $formCategoria.elements["guardar"];
	let $btnReset = $formCategoria.elements["reset"];
	$btnGuardar.dataset.action = estado ? "InsertCategoria" : "ActionUpdateCategoria";
	$btnGuardar.setAttribute("value", estado ? "Guardar" : "Guardar Cambios");
	$btnReset.setAttribute("value", estado ? "Limpiar" : "Cancelar");

}


