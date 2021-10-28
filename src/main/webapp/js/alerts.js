/* 
Mensaje para mostrar en los alert
*/

export function showAlert(parent, data) {
	// console.log("data desde ALert", data);
	const fragment = document.createDocumentFragment();
	for (let men in data) {
		// console.log("properti ", men);
		let itemMen = data[men];
		let $alertItem = document.createElement("P");
		$alertItem.classList.add("alert__item", `alert__item--${itemMen.estado}`);
		$alertItem.innerHTML = `${itemMen.mensaje}<span class ="alert__close">X</span>`;
		fragment.appendChild($alertItem);
	}
	parent.innerHTML = "";
	parent.appendChild(fragment);
	parent.style.opacity = "1";
	parent.style.zIndex = "9999";

	// Cerrar cada alert__item de forma individual
	[...parent.children].forEach((item) => {
		item.addEventListener("click", (e) =>
			e.target.matches("span") ? (item.style.opacity = "0") : ""
		);
	});

	// Ocultar el parent de los alert__item
	setTimeout(() => (parent.style.opacity = "0"), 5000);
}
