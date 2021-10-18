<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Nueva Palabra</title>
<link rel="stylesheet" href="css/normalize.css" />
<link rel="stylesheet" href="css/styel.css" />
</head>
<body>
	<div class="container container--palabra">
		<div class="content">
			<h1 class="title">Admin Palabra</h1>
			<form action="#" class="form" autocomplete="off" name="form-palabra" >
				<h2 class="form__title">Nueva Palabra</h2>

				<div class="row row--content">
					<div class="row row--content__item row--content__item-70">
						<div class="row row--flex">
							<label for="id" class="form__label">Id</label> <input type="text"
								id="id" class="form__input" name="id" readonly />
						</div>
						<div class="row row--flex">
							<label for="name" class="form__label">Nombre</label> <input
								type="text" id="name" class="form__input" name="id" />
						</div>
						<div class="row row--flex">
							<label for="descripcion" class="form__label">Descripci&oacute;n</label>
							<input type="text" id="descripcion" class="form__input"
								name="descripcion" />
						</div>
						<div class="row row--flex">
							<label for="categoria" class="form__label">Categoria</label> <select
								name="categoria" id="categoria" class="form__input">
								<option value="">DEPORTES</option>
								<option value="">ANIMALES</option>
								<option value="">FRUTAS</option>
							</select>
						</div>
					</div>
					<div
						class="
                row row--btn row--flex
                row--content__item row--content__item-30
              ">
              
              			<input type="reset" value="Limpiar" class="btn btn--reset form__btn" name="reset" /> 
						<input type="submit" data-action="insertCategoria"  data-id=""  name="guardar" value="Guardar" class="btn btn--send form__btn" />
					</div>
				</div>
			</form>
		</div>
		<div class="content content--table">
			<form action="" class="form">
				<h2 class="form__title">Palabras registradas</h2>
				<div class="row row--content">
					<table class="table table--palabra">
						<thead class="table__head">
							<tr>
								<th class="table__celda table__celda--small table__celda--th">
									Id</th>
								<th class="table__celda table__celda--media table__celda--th">
									Nombre</th>
								<th class="table__celda table__celda--large table__celda--th">
									Descripci&oacute;n</th>
								<th class="table__celda table__celda--media table__celda--th">
									Categoria</th>
								<th class="table__celda table__celda--th">Funciones</th>
							</tr>
						</thead>
						<tbody class="table__body">
							<tr>
								<td class="table__celda table__celda--td">00</td>
								<td class="table__celda table__celda--td">Animales</td>
								<td class="table__celda table__celda--td">Es una des</td>
								<td class="table__celda table__celda--td">Animales</td>
								<td class="table__celda table__celda--btn table__celda--td">
									<input type="submit" value="Update"
									class="btn btn--info table__btn" /> <input
									type="submit" value="Delete" class="btn btn--danger table__btn" />
								</td>

							</tr>
						</tbody>
					</table>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
