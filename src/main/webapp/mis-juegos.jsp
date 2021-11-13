<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Mis Juegos</title>
<link rel="stylesheet" href="css/normalize.css" />
<link rel="stylesheet" href="css/styel.css" />
</head>
<body>
	<div class="container">
		<div class="content content--juegos">

			<div class="row row--flex row--flex-space-between">
				<span> <a href="menu-principal.jsp">Regresar</a>
				</span>
				<h1 class="title title--grow-1">Mis Juegos</h1>
			</div>
			
		</div>
		<div class="content content--table">
			<form action="CMain" class="form form--table"
				name="form-table-juegos" method="post">

				<h2 class="form__title">Juegos Registrados</h2>
				<div class="row row--content">
					<table class="table table--juegos">
						<thead class="table__head">
							<tr>
								<th class="table__celda table__celda--small table__celda--th">
									Id</th>
								<th class="table__celda table__celda--media table__celda--th">
									Fecha</th>
								<th class="table__celda table__celda--media table__celda--th">
									Tiempo</th>
								<th class="table__celda table__celda--media table__celda--th">
									Puntaje</th>
								<th class="table__celda table__celda--large table__celda--th">
									Palabra</th>
								<th class="table__celda table__celda--th">Funciones</th>
							</tr>
						</thead>
						<tbody class="table__body">

							<c:forEach var="element" items="${usuario.juegos}">

								<tr>
									<td class="table__celda table__celda--td"><c:out
											value="${element.idJuego}" /></td>
									<td class="table__celda table__celda--td"><c:out
											value="${element.fecha}" /></td>
									<td class="table__celda table__celda--td"><c:out
											value="${element.tiempo}" /> sg</td>
									<td class="table__celda table__celda--td"><c:out
											value="${element.puntaje}" /></td>
									<td class="table__celda table__celda--td"><c:out
											value="${element.palabra.nombre}" /></td>
									<td class="table__celda table__celda--btn table__celda--td">
										<input type="submit" data-id="${element.idJuego}"
										data-action="delete" value="Delete" name="delete"
										class="btn btn--danger table__btn" />
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form>
			<div class="row datos">
				<p>
					Total juegos:
					<c:out value="${numeroJuegos}" default="0" />
				</p>
				<c:if test="${mensaje !=null}">
					<p>
						<c:out value="${mensaje}"></c:out>
					</p>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>
