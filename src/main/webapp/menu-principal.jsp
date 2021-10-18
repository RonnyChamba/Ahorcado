<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Ahorcado|Menu</title>
<link rel="stylesheet" href="css/normalize.css" />
<link rel="stylesheet" href="css/styel.css" />
</head>
<body>

	<c:set value="${usuario.tipo}" var="tipoLogin" />
	<div class="container container--menu">
		<div class="content content--menu">
			<header class="header">
				<h1 class="title">El Ahorcado</h1>
				<ul class="lista lista-menu">
					<li class="lista-menu__item">Bienvenido <c:out
							value="${usuario.nombre}" />
					</li>
					<li class="lista-menu__item">Cerrar Sesi&oacute;n</li>
				</ul>
			</header>
			<div class="row grid">
				<div class="grid__item">
					<h3 class="grid__opcion">
						<a href="CMain?action=jugar">Jugar</a>
					</h3>
					<img src="img/Azuay.png" alt="" />
				</div>

				<c:if test="${tipoLogin !=null && tipoLogin.equalsIgnoreCase('ADMIN')}">
					<div class="grid__item">
						<h3 class="grid__opcion">
							<a href="CJugador?action=formJugador">Jugador</a>
						</h3>
						<img src="img/El Oro.jpg" alt="" />
					</div>

				</c:if>

				<div class="grid__item">
					<h3 class="grid__opcion">
						<a href="CMain?action=formCategoria">Categorias</a>
					</h3>
					<img src="img/guayas.jpg" alt="" />
				</div>
				<div class="grid__item">
					<h3 class="grid__opcion">
						<a href="CPalabra?action=formPalabra">Palabras</a>
					</h3>
					<img src="img/santoDomingo.jpg" alt="" />
				</div>
			</div>
		</div>
		<footer class="footer">
			<div class="column column--33">
				<c:set var="today" value="<%=new java.util.Date()%>" />
				<p>
					Fecha:
					<fmt:formatDate type="date" value="${today}" pattern="yyyy-MM-dd" />
				</p>

			</div>
			<div class="column column--33">
				<p>
					Puntaje:
					<c:out value="${usuario.puntaje}" />
				</p>
			</div>
			<div class="column column--33">
				<p>Puntaje</p>
			</div>
		</footer>
	</div>
</body>
</html>
