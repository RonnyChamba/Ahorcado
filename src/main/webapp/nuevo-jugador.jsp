<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Nuevo Jugador</title>
<link rel="stylesheet" href="css/normalize.css" />
<link rel="stylesheet" href="css/styel.css" />
</head>
<body>
	<div class="container container--flex" id="container-jugador">
		<div class="content content--500">
			<h1 class="title">Nuevo Jugador</h1>
			<form action="CJugador" class="form" autocomplete="off"
				name="form-jugador" method="post">
				<input type="hidden" name="id" /> 
				<input type="hidden"
					name="action" value="nuevoJugador" />

				<c:if test="${admin !=null}">
					<div class="row row--flex">
						<label for="tipo" class="form__label">Tipo</label> <select
							name="tipoJugador" id="tipo" class="form__input">
							<option value="ADMIN">Admin</option>
							<option value="JUGADOR">Jugador</option>
						</select>
					</div>
				</c:if>

				<div class="row row--flex">
					<label for="dni" class="form__label">Dni</label> <input type="text"
						id="dni" class="form__input" name="dni" />
				</div>

				<div class="row row--flex">
					<label for="name" class="form__label">Nombre</label> <input
						type="text" id="name" class="form__input" name="name" />
				</div>
				<div class="row row--flex">
					<label for="ciudad" class="form__label">Ciudad</label> <input
						type="text" id="ciudad" class="form__input" name="ciudad" />
				</div>
				<div class="row row--flex">
					<label for="clave" class="form__label">Clave</label> <input
						type="password" id="clave" class="form__input" name="clave" />
				</div>
				<div class="row row--flex row--flex-star">
					<!-- Label de relleno, inicia display none y en 468px se hace block -->
					<label for="" class="form__label form__label--none"></label>
					<div class="div">
						<input type="checkbox" class="form__input form__input--checkbox"
							id="showpass" name="showpass" /> <label for="showpass"
							class="form__label">Mostrar contraseña</label>
					</div>
				</div>
				<div class="row row--flex">
					<label for="puntaje" class="form__label">Puntaje</label> <input
						type="number" id="puntaje" class="form__input" name="puntaje" />
				</div>
				<div class="row row--btn row--flex">
					<input type="reset" value="Limpiar" class="btn btn--reset form__btn" />
				    <input type="submit" value="Guardar" class="btn btn--send form__btn" />
				</div>
			</form>
			<div class="mensaje"></div>
			<div class="row">
				<a href="menu-principal.jsp">Regresar</a>
			</div>

		</div>
		
		<div class="alert" id="alert"></div>
	</div>
	<script  src="js/script_jugador.js" type="module"></script>
</body>
</html>
