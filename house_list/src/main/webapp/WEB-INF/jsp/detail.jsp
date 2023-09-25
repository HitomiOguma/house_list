<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<meta charset="UTF-8">
<title>物件詳細</title>
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
<form id="myForm" method="post">
<c:forEach items="${oneHouseList}" var="oneHouse">
    <input type="hidden" id="id" name="id" value="${oneHouse.id}"readonly>
    受注番号<input type="text" id="order_number" name="order_number" value="${oneHouse.order_number}"readonly>
    受注日<input type="date" id="order_date" name="order_date" value="${oneHouse.order_date}"readonly><br>
    物件名(様邸)<input type="text" id="house_name" name="house_name" value="${oneHouse.house_name}"readonly>
    建物タイプ<input type="text" id="housing_type" name="housing_type" value="${oneHouse.housing_type}"readonly><br>
    住所<input type="text" id="address" name="address" value="${oneHouse.address}"readonly><br>
    監督名<input type="text" id="supervisor" name="supervisor" value="${oneHouse.supervisor}"readonly>
    施工会社<input type="text" id="construction_company" name="construction_company" value="${oneHouse.construction_company}"readonly><br>
    備考欄<input type="text" id="remarks" name="remarks" value="${oneHouse.remarks}"readonly><br>
    製作完了 <input type="checkbox" id="is_completed" name="is_completed" value="1" <c:if test="${oneHouse.is_completed == 1}">checked</c:if>>
   			<input type="hidden" id="is_completed" name="is_completed" value="0"value = "${oneHouse.is_completed}">
</c:forEach>
<input type="submit" name="action" value="物件情報更新" onclick="setFormAction('/house_list/Update')">
<input type="submit" name="action" value="搬入情報更新" onclick="setFormAction('/house_list/Moving')">
</form>

<script>
function setFormAction(action) {
    document.getElementById('myForm').action = action;
}
</script>
<table border="1">
    <tr>
      <th>搬入日</th>
      <th>搬入物</th>
      <th>トラック</th>
    </tr>
    
<c:forEach items="${oneMovingList}" var="oneMoving">
    <tr>
      <td>${oneMoving.moving_date}</td>
      <td>${oneMoving.moving_item}</td>
      <td>${oneMoving.truck_type}</td>
    </tr>
    </c:forEach>
  </table>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>

</body>
</html>
