package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.HouseDao;
import dao.MovingDao;
import model.MovingBeans;

/**
 * Servlet implementation class SearchSarvlet
 */
@WebServlet("/Search")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HouseDao houseDao = new HouseDao();
		//検索フォームの建物タイプ設定
		// 1. データベースからhousing_typeの一覧を取得
		List<String> housingTypeList = houseDao.findHousingTypes();

		// 2. 空白や重複を削除して整理
		List<String> uniqueHousingTypes = new ArrayList<>();
		for (String housing_type : housingTypeList) {
			if (housing_type != null && !housing_type.trim().isEmpty() && !uniqueHousingTypes.contains(housing_type)) {
				uniqueHousingTypes.add(housing_type);
			}
		}
		// 3. uniqueHousingTypes をJSPに渡す
		HttpSession session = request.getSession();
		session.setAttribute("uniqueHousingTypes", uniqueHousingTypes);

		/*//★★テスト
		System.out.println("Search housing_type: " + request.getParameter("housing_type"));
		System.out.println("Search house_name: " + request.getParameter("house_name"));
		System.out.println("Search horder_number: " + request.getParameter("order_number"));*/

		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String order_number = request.getParameter("order_number");
		String house_name = request.getParameter("house_name");
		String housing_type = request.getParameter("housing_type");

		// デフォルト値の設定（条件が指定されていない場合）
		if (order_number == null) {
			order_number = "";
		}
		if (house_name == null) {
			house_name = "";
		}
		if (housing_type == null || housing_type.isEmpty()) {
			housing_type = "";
		}

		String idParam = request.getParameter("id");
		int id = 0; // デフォルト値を設定
		if (idParam != null && !idParam.isEmpty()) {
			try {
				id = Integer.parseInt(idParam);
			} catch (NumberFormatException e) {
				// 数値に変換できない場合のエラーハンドリング
				e.printStackTrace();
			}
		}

		//一覧		
		MovingDao movingDao = new MovingDao();
		List<MovingBeans> movingList = movingDao.findSearch(id, order_number, house_name, housing_type);
		request.setAttribute("movingList", movingList);

		/*//★★テスト
		System.out.println(movingList);

		//★★テスト
		for (MovingBeans moving : movingList) {
			System.out.println("Moving Date: " + moving.getMoving_date());
			if (moving.getHouse() != null) {
				System.out.println("Order Number: " + moving.getHouse().getOrder_number());
				System.out.println("House Name: " + moving.getHouse().getHouse_name());
				System.out.println("Housing Type: " + moving.getHouse().getHousing_type());
			} else {
				System.out.println("No associated HouseBeans for this moving!");
			}
		}*/

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/searchResult.jsp");
		dispatcher.forward(request, response);
	}

}
