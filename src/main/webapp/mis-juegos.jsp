<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

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
			<h1 class="title">Mis Juegos</h1>
			<div class="row">
				<a href="" class="link link--return">Regresar</a>
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
							<tr>
								<td class="table__celda table__celda--td">00</td>
								<td class="table__celda table__celda--td">2021-04-12</td>
								<td class="table__celda table__celda--td">10s</td>
								<td class="table__celda table__celda--td">300</td>
								<td class="table__celda table__celda--td">LEON</td>
								<td class="table__celda table__celda--btn table__celda--td">
									<input type="submit" data-id="1" data-action="delete"
									value="Delete" name="delete" class="btn btn--danger table__btn" />
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</form>
			<div class="row datos">
				<p>Numero de juegos:20</p>
			</div>
		</div>
	</div>
</body>
</html>
