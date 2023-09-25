<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<title>新規物件入力</title>
</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">
			<div class="collapse navbar-collapse justify-content-end">
				<ul class="navbar-nav">
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
	
<% String error = (String) request.getAttribute("error");
       if (error != null && !error.isEmpty()) { %>
	<div class="error-message">
		<%= error %>
	</div>
	<% } %>
<form action = "/house_list/New" method="post">
   受注番号<input type="text" id="order_number" name="order_number"value="<%= (request.getAttribute("inputOrder_number") != null) ? request.getAttribute("inputOrder_number") : "" %>">
   受注日<input type="date" id="order_date" name="order_date"value="<%= (request.getAttribute("inputOrder_date") != null) ? request.getAttribute("inputOrder_date") : "" %>"><br>
   物件名(様邸)<input type="text" id="house_name" name="house_name" value="<%= (request.getAttribute("inputHouse_name") != null) ? request.getAttribute("inputHouse_name") : "" %>">
   建物タイプ<input type="text" id="housing_type" name="housing_type" value="<%= (request.getAttribute("inputHousing_type") != null) ? request.getAttribute("inputHousing_type") : "" %>"><br>
   住所<input type="text" id="address" name="address"value="<%= (request.getAttribute("inputAddress") != null) ? request.getAttribute("inputAddress") : "" %>"><br>
   監督名<input type="text" id="supervisor" name="supervisor"value="<%= (request.getAttribute("inputSupervisor") != null) ? request.getAttribute("inputSupervisor") : "" %>">
   施工会社<input type="text" id="construction_company" name="construction_company"value="<%= (request.getAttribute("inputConstruction_company") != null) ? request.getAttribute("inputConstruction_company") : "" %>"><br>
   備考欄<input type="text" id="remarks" name="remarks"value="<%= (request.getAttribute("inputRemarks") != null) ? request.getAttribute("inputRemarks") : "" %>"><br>
   製作完了 <input type="checkbox" id="completed_checkbox" name="completed_checkbox" value="1">
			<input type="hidden" id="is_completed" name="is_completed" value="0">

   
   <input type="submit" value="登録">
</form>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>

</body>
</html>
