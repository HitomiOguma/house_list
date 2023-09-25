<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<meta charset="UTF-8">
<title>物件情報更新</title>
</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">
			<div class="collapse navbar-collapse justify-content-end">
				<ul class="navbar-nav">
				<li class="nav-item">
						<form action="/house_list/Detail" method="get">
						<c:forEach items="${oneHouseList}" var="oneHouse">
						<input type="hidden" id="order_number" name="order_number" value = "${oneHouse.order_number}">
						
							<button type="submit" class="nav-link active" aria-current="page">戻る</button>
						</c:forEach>	
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
	
<form id="myForm" method="post" action="">
<c:forEach items="${oneHouseList}" var="oneHouse">
<input type="hidden" id="id" name="id" value="${oneHouse.id}">
   受注番号<input type="text" id="order_number" name="order_number" value = "${oneHouse.order_number}">
   受注日<input type="date" id="order_date" name="order_date" value = "${oneHouse.order_date}"><br>
   物件名(様邸)<input type="text" id="house_name" name="house_name" value = "${oneHouse.house_name}">
   建物タイプ<input type="text" id="housing_type" name="housing_type" value = "${oneHouse.housing_type}"><br>
   住所<input type="text" id="address" name="address" value = "${oneHouse.address}"><br>
   監督名<input type="text" id="supervisor" name="supervisor" value = "${oneHouse.supervisor}">
   施工会社<input type="text" id="construction_company" name="construction_company"value = "${oneHouse.construction_company}"><br>
   備考欄<input type="text" id="remarks" name="remarks" value = "${oneHouse.remarks}"><br>
   製作完了 <input type="checkbox" id="is_completed" name="is_completed" value="1" <c:if test="${oneHouse.is_completed == 1}">checked</c:if>>
   <%-- 製作完了 <input type="checkbox" id="completed_checkbox" name="completed_checkbox"value = "${oneHouse.is_completed}"> --%>
   			<input type="hidden" id="is_completed" name="is_completed" value="0"value = "${oneHouse.is_completed}">
   </c:forEach>
     
  <%--  <c:choose>
  <c:when test="${not empty oneMovingList}"> --%>
    <c:forEach items="${oneMovingList}" var="oneMoving">
      <input type="hidden" id="house_id" name="house_id" value="${oneMoving.house_id}">
    </c:forEach>
  <%-- </c:when>
</c:choose> --%>
   </form>
<button type="submit" class="btn btn-primary" name="action" onclick="setFormAction('/house_list/HouseUpdate')">登録</button>
<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
  削除
</button>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h3 class="modal-title" id="exampleModalLabel">この物件を削除します。よろしいですか？</h3>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        この物件を削除します。よろしいですか？
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">戻る</button>
        <!-- 削除ボタンをクリックしたときの処理 -->
        <button type="button" class="btn btn-primary" onclick="deleteItem()">削除</button>
      </div>
    </div>
  </div>
</div>

<script>
// モーダル内の削除ボタンがクリックされたときの処理
function deleteItem() {
  // フォームの送信アクションを設定
  document.getElementById('myForm').action = '/house_list/Delete';
  // フォームを送信
  document.getElementById('myForm').submit();
  // モーダルを閉じる
  $('#exampleModal').modal('hide');
}

// 登録ボタンがクリックされたときの処理
function setFormAction(action) {
  // フォームの送信アクションを設定
  document.getElementById('myForm').action = action;
  // フォームを送信
  document.getElementById('myForm').submit();
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
      <%-- <td hidden id="id" name="id">${oneMoving.id}</td>
      <td hidden id="house_id" name="house_id">${oneMoving.house_id}</td> --%>
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
