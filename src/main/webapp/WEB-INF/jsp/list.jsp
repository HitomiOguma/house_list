<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">
<meta charset="UTF-8">
<title>物件予定一覧</title>
</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">
			<div class="collapse navbar-collapse justify-content-end">
				<ul class="navbar-nav">
				<li class="nav-item">
						<form action="/house_list/All" method="post">
							<button type="submit" class="nav-link active" aria-current="page">物件一覧(すべて)</button>
						</form>
					<li class="nav-item">
						<form action="/house_list/List" method="post">
							<button type="submit" class="nav-link active" aria-current="page">物件予定一覧</button>
						</form>
					<li class="nav-item">
						<form action="/house_list/Logout" method="post">
							<button type="submit" class="nav-link active" aria-current="page">ログアウト</button>
						</form>
					</li>
				</ul>
			</div>
		</div>
	</nav>
	<form action="/house_list/Search" method="post">
		<input type="text" id="order_number" name="order_number"
			placeholder="受注番号を入力"> <input type="text" id="house_name"
			name="house_name" placeholder="物件名を入力"><br> <label
			for="housing_type"></label> <select id="housing_type"
			name="housing_type">
			<option value="">物件タイプを選択</option>
			<c:forEach items="${uniqueHousingTypes}" var="housing_type">
				<option value="${housing_type}">${housing_type}</option>
			</c:forEach>
		</select> 
	 	<input type="submit" value="検索">
	</form>
	<form action="/house_list/new.jsp">
		<input type="submit" value="新規物件入力">
	</form>

	<table border="1">
		<tr>
			<th>受注番号</th>
			<th>物件名(様邸)</th>
			<th>建物タイプ</th>
			<th>搬入日(第1便)</th>
			<th>製作完了</th>
		</tr>

		<c:forEach items="${movingList}" var="moving">
			<c:set var="house" value="${moving.house}" />

			<c:choose>
				<c:when test="${house.is_completed == 1}">
					<c:set var="bgColor" value="table-secondary" />
				</c:when>
				<c:otherwise>
					<c:set var="bgColor" value="" />
				</c:otherwise>
			</c:choose>

			<tr class="${bgColor}">
				
				<td><a href="/house_list/Detail?order_number=${house.order_number}&id=${house.id}">${house.order_number}</a></td>
				<td>${house.house_name}</td>
				<td>${house.housing_type}</td>
				<td>${moving.moving_date}</td>
				<td><c:choose>
					<c:when test="${house.is_completed == 1}">
					完了
					</c:when>
					<c:otherwise>
					未
					</c:otherwise>
					</c:choose></td>
			</tr>
		</c:forEach>
	</table>





<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>



</body>
</html>