<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Login</title>
<link rel="stylesheet" href="css/normalize.css" />
<link rel="stylesheet" href="css/styel.css" />
</head>
<body>
	<div class="container container--flex">
		<div class="content content--500">
			<h1 class="title">Ingresar al Sistema</h1>
			<form action="CJugador" class="form" autocomplete="off"
				name="form-login" method="post">
				<input type="hidden" name="action" value="login" />
				<div class="row row--flex">
					<label for="name" class="form__label">Usuario:</label> <input
						type="text" name="user" id="name" class="form__input" />
				</div>
				<div class="row row--flex">
					<label for="pass" class="form__label">Contraseña:</label> <input
						type="password" name="clave" id="clave"
						class="form__input form__input--pass" />
				</div>
				<div class="row row--flex row--flex-star">
					<!-- Label de relleno, inicia display none y en 468px se hace block -->
					<label for="" class="form__label form__label--none"></label>
					<div class="row--flex row--flex--register">
						<div>
							<input type="checkbox" class="form__input form__input--checkbox"
								id="showpass" name="showpass" /> <label for="showpass"
								class="form__label">Mostrar contraseña</label>

						</div>
						<a href="nuevo-jugador.jsp" class="link" target="_blank">Registrarse</a>

					</div>
				</div>
				<div class="row">
					<input type="submit" value="Ingresar" class="btn form__btn"
						id="btn-login" />
				</div>
			</form>

			<div class="mensaje">
				<c:if test="${mensaje !=null}">
					<p>
						<c:out value="${mensaje}" />
					</p>
				</c:if>
			</div>
		</div>
	</div>
	<script  src="js/script_login.js"  type="module"></script>
</body>
</html>
