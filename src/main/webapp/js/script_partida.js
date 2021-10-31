export class Partida {

	constructor(objPalabra) {
		this.objPalabra = objPalabra;
		this.objCategoria = JSON.parse(this.objPalabra.categoria);
		this.listaCaracteresPalabra = [...this.objPalabra.name].map(() => "");
		this.numeroIntentos = 0;
		this.puntosPartida = 100;
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
		return `img/img-estado/ahor${(this.numeroIntentos +1)}.png`;
	}
}