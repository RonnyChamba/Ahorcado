import { validarLogin as validarL, showPass } from "./script.js";
const $formLogin = document.forms["form-login"];
$formLogin.addEventListener("submit", function(e) {
	e.preventDefault();
	
	let {estado, mensaje, datos} = validarL(this);
		document.querySelector(".mensaje").innerHTML = mensaje;
	if (estado) this.submit();
});

$formLogin.elements["showpass"].addEventListener ("change", (event) => showPass($formLogin, event));
	