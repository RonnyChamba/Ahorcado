<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Jugar</title>
<link
	href="https://file.myfontastic.com/JUwFAwWE8oUWFGdiTZSWaX/icons.css"
	rel="stylesheet" />
<link rel="stylesheet" href="css/normalize.css" />
<link rel="stylesheet" href="css/styel.css" />
</head>
<body>
	<div class="container container--juego">
		<div class="content content--juego">
			<aside class="aside-menu">
				<div class="aside-menu__item">
					<h2>Datos Partida</h2>
					<div class="row">
						<span>Categoria:</span>
						<div class="aside-menu__option aside-menu__option--flex">
							<p class="help-juego help-categoria">sasasdsdf</p>
							<span class="icon-eye"></span>
						</div>
					</div>
					<div class="row">
						<span>Desc Categoria:</span>
						<div class="aside-menu__option aside-menu__option--flex">
							<p class="help-juego help-desc-categoria">sasasdsdf</p>
							<span class="icon-eye"></span>
						</div>
					</div>
					<div class="row">
						<span>Desc Palabra:</span>
						<div class="aside-menu__option aside-menu__option--flex">
							<p class="help-juego help-desc-palabra">sasasdsdf</p>
							<span class="icon-eye"></span>
						</div>
					</div>
				</div>
				<div class="aside-menu__item">
					<h2>Mis Juegos</h2>
				</div>
			</aside>
			<section class="jugar">
				<div class="jugar__item jugar__item--flex">
					<span> <a href="#">Regresar</a>
					</span>
					<h1 class="jugar__title title">Jugar</h1>
				</div>
				<div class="jugar__item jugar__item--flex">
					<div class="jugar__opcion" id="btn-jugar-partida">
						<button
							class="
                  icon-play
                  btn btn__juego btn__juego--opcion btn__juego--play
                "
							id="btn-play">Iniciar</button>
						<button
							class="
                  icon-pause
                  btn btn__juego btn__juego--opcion btn__juego--pause
                "
							id="btn-pause">Detener</button>
					</div>
					<div class="jugar__opcion">
						<div class="jugar__time">
							<span class="icon-time"></span> <span id="time-js">00</span>
						</div>
						<button
							class="icon-help btn btn__juego btn__juego--opcion btn__juego--info"
							id="btn-info">Info</button>

					</div>
				</div>
				<div class="jugar__item jugar__item--img-palabra">
					<div class="jugar__img">
						<img src="img/img-estado/ahor0.png" alt="Ahorcado"
							id="img-juego-main" />
					</div>
					<div class="jugar__palabra">
						<p class="palabra-buscar" id="palabra-juego-main">COMIENZA A
							JUGAR</p>
					</div>
				</div>
				<div class="jugar__item jugar__item--letras">
					<div class="jugar__item-fila">
						<button class="btn btn__juego btn__juego--letra">Q</button>
						<button class="btn btn__juego btn__juego--letra">W</button>
						<button class="btn btn__juego btn__juego--letra">E</button>
						<button class="btn btn__juego btn__juego--letra">R</button>
						<button class="btn btn__juego btn__juego--letra">T</button>
						<button class="btn btn__juego btn__juego--letra">Y</button>
						<button class="btn btn__juego btn__juego--letra">U</button>
						<button class="btn btn__juego btn__juego--letra">I</button>
						<button class="btn btn__juego btn__juego--letra">O</button>
						<button class="btn btn__juego btn__juego--letra">P</button>
					</div>
					<div class="jugar__item-fila">
						<button class="btn btn__juego btn__juego--letra">A</button>
						<button class="btn btn__juego btn__juego--letra">S</button>
						<button class="btn btn__juego btn__juego--letra">D</button>
						<button class="btn btn__juego btn__juego--letra">F</button>
						<button class="btn btn__juego btn__juego--letra">G</button>
						<button class="btn btn__juego btn__juego--letra">H</button>
						<button class="btn btn__juego btn__juego--letra">J</button>
						<button class="btn btn__juego btn__juego--letra">K</button>
						<button class="btn btn__juego btn__juego--letra">L</button>
						<button class="btn btn__juego btn__juego--letra">Ñ</button>
					</div>
					<div class="jugar__item-fila">
						<button class="btn btn__juego btn__juego--letra">Z</button>
						<button class="btn btn__juego btn__juego--letra">X</button>
						<button class="btn btn__juego btn__juego--letra">C</button>
						<button class="btn btn__juego btn__juego--letra">V</button>
						<button class="btn btn__juego btn__juego--letra">B</button>
						<button class="btn btn__juego btn__juego--letra">N</button>
						<button class="btn btn__juego btn__juego--letra">M</button>
					</div>
					<div class="jugar__item--letras-load"></div>
				</div>
			</section>
		</div>

		<div class="alert" id="alert"></div>
	</div>
	<div class="modal" id="modal">
		<div class="modal__content" id="modal-content">
			<h2 class="modal__title"></h2>
			<div class="modal__item modal__item-content"></div>
			<div class="modal__item modal__item-botones">
				<button class="btn btn--send" id="modal-btn-aceptar"
					data-action="modal-btn-aceptar">Aceptar</button>
				<button class="btn btn--danger" id="modal-btn-cancelar"
					data-action="modal-btn-cancelar">Cancelar</button>
			</div>
		</div>

		<div class="modal__load">
			<p class="modal__load-barra">Cargando datos......</p>
		</div>
		<div class="modal__close" id="modal-close">X</div>
	</div>
	<script src="js/script_juego.js" type="module"></script>
</body>
</html>
