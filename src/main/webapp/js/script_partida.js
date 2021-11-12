export class Partida {

	constructor(objPalabra) {
		this.objPalabra = objPalabra;
		this.objCategoria = JSON.parse(this.objPalabra.categoria);
		this.listaCaracteresPalabra = [...this.objPalabra.name].map(() => "");
		this.numeroIntentos = 0;
		this.puntosPartida = 100;
		this.segundosJuego = 0;
		// Almacena todas las letras pulsadas/ correcta e incorrectas
		this.letrasPulsadas = [];
	}
	mostrarTextoPalabra() {
		let palabraLabel = "";
		this.listaCaracteresPalabra.forEach((value, index) => {
			palabraLabel +=
				value === ""
					? " _ "
					: `${this.objPalabra.name.substring(index, index + 1)} `;
		});
		return palabraLabel;
	}

	// Verifica letra pulsada
	verificarLetraContenida(letra = "") {
		this.letrasPulsadas.push(letra);

		if (this.objPalabra.name.includes(letra)) {
			[...this.objPalabra.name].forEach((value, index) =>
				letra === value ? (this.listaCaracteresPalabra[index] = letra) : ""
			);
			return true;
		}
		this.numeroIntentos++;
		this.puntosPartida -= 20;
	}

	get getPalabra() {
		return this.objPalabra;
	}
	get getCategoria() {
		return this.objCategoria;
	}
	get getPalabraAcertada() {
		return this.listaCaracteresPalabra.join("") === this.objPalabra.name;
	}
	get getNumeroIntentos() {
		return this.numeroIntentos;
	}
	get getPuntos() {
		return this.puntosPartida;
	}
	get getPathImg() {
		return `img/img-estado/ahor${(this.numeroIntentos + 1)}.png`;
	}

	get isFinJuego() {
		return this.getPalabraAcertada || this.getNumeroIntentos == 5 || this.getSegundosJuego == 60;
	}

	get getSmsFinJuego() {

		let mensaje = "";

		if (this.getPalabraAcertada) mensaje = "Has acertado la palabra";
		else if (this.getNumeroIntentos === 5) mensaje = "Se terminaron los intentos permitidos";
		else if (this.getSegundosJuego === 60) mensaje = "Se termino el tiempo disponible";
		else mensaje = "Fin del juego";

		return mensaje;
	}
	get getFechaJuego() {
		let fecha = new Date();
		return `${fecha.getFullYear()}-${fecha.getMonth() + 1}-${fecha.getDate()}`;
	}
	get getLetrasPulsadas() {
		return this.letrasPulsadas;
	}
	get getSegundosJuego() {
		return this.segundosJuego;
	}
	set setSegundosJuego(segundos) {
		this.segundosJuego = segundos;
	}

}