<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Nueva Categoria</title>
<link rel="stylesheet" href="css/normalize.css" />
<link rel="stylesheet" href="css/styel.css" />
</head>
<body>
	<div class="container container--categoria" id="container-categoria">
		<div class="content">

			<div class="row row--flex row--flex-space-between">
				<span> <a href="menu-principal.jsp">Regresar</a>
				</span>
				<h1 class="title title--grow-1">Admin Categoria</h1>
			</div>
			<form action="CMain" class="form form--categoria"
				name="form-categoria" autocomplete="off" method="post">
				<h2 class="form__title">Nueva Categoria</h2>
				<div class="row row--content">
					<div class="row row--content__item row--content__item-70">
						<div class="row row--flex">
							<label for="id" class="form__label">Codigo</label> <input
								type="text" id="id" class="form__input" name="id" readonly />
						</div>
						<div class="row row--flex">
							<label for="name" class="form__label">Nombre</label> <input
								type="text" id="name" class="form__input" name="name" />
						</div>
						<div class="row row--flex">
							<label for="descripcion" class="form__label">Descripci&oacute;n</label>
							<input type="text" id="descripcion" class="form__input"
								name="descripcion" />
						</div>
					</div>
					<div
						class="
                row row--btn row--flex
                row--content__item row--content__item-30
              ">
						<input type="reset" value="Limpiar"
							class="btn btn--reset form__btn" name="reset" /> <input
							type="submit" data-action="insertCategoria" data-id=""
							name="guardar" value="Guardar" class="btn btn--send form__btn" />
					</div>
					<div class="mensaje">
						<c:if test="${mensaje !=null}">
							<p>
								<c:out value="${mensaje}" />
							</p>
						</c:if>

					</div>
				</div>
			</form>
		</div>

		<c:choose>

			<c:when test="${categorias !=null}">
				<div class="content content--table">
					<form action="CMain" class="form form--table"
						name="form-table-categoria" method="post">
						<h2 class="form__title">Categorias registradas</h2>
						<div class="row row--content">
							<table class="table">
								<thead class="table__head">
									<tr>
										<th class="table__celda table__celda--small table__celda--th">
											Codigo</th>
										<th class="table__celda table__celda--media table__celda--th">
											Nombre</th>
										<th class="table__celda table__celda--large table__celda--th">
											Descripci&oacute;n</th>
										<th class="table__celda table__celda--th">Funciones</th>
									</tr>
								</thead>
								<tbody class="table__body " id="table-body">

									<c:forEach var="item" items="${categorias}">
										<tr>
											<td class="table__celda table__celda--td"><c:out
													value="${item.idCategoria}" /></td>
											<td class="table__celda table__celda--td"><c:out
													value="${item.nombre}" /></td>
											<td class="table__celda table__celda--td"><c:out
													value="${item.descripcion}" /></td>

											<td class="table__celda table__celda--btn table__celda--td">
												<input type="submit" name="action"
												data-id="${item.idCategoria}"
												data-action="formUpdateCategoria" value="Update"
												class="btn btn--info table__btn" /> <input type="submit"
												name="action" value="Delete"
												class="btn btn--danger table__btn"
												data-id="${item.idCategoria}"
												data-action="actionDeleteCategoria" />

											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</form>
					
					<div>
						<p>Total: <c:out value="${fn:length(categorias)}" default="0"/> </p>
					
					</div>				
				</div>

			</c:when>

			<c:when test="${mensaje !=null}">
				<div>
					<p>
						<c:out value="${mensaje}" />
					</p>
				</div>
			</c:when>
			<c:otherwise><p>Error inesperado</p></c:otherwise>

		</c:choose>
		<div class="alert" id="alert"></div>
	</div>
	<script src="js/script_categoria.js" type="module"></script>
</body>
</html>

