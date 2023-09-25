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
import model.HouseBeans;
import model.MovingBeans;

/**
 * Servlet implementation class AllServlet
 */
@WebServlet("/All")
public class AllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HouseDao houseDao = new HouseDao();
		//検索フォームの建物タイプ設定
		// 1. データベースからhousing_typeの一覧を取得
		List<String> housingTypeList =houseDao.findHousingTypes();

		// 2. 空白や重複を削除して整理
		List<String> uniqueHousingTypes = new ArrayList<>();
		for (String housingType : housingTypeList) {
			if (housingType != null && !housingType.trim().isEmpty() && !uniqueHousingTypes.contains(housingType)) {
				uniqueHousingTypes.add(housingType);
			}
		}
		// 3. uniqueHousingTypes をJSPに渡す
		HttpSession session = request.getSession();
		session.setAttribute("uniqueHousingTypes", uniqueHousingTypes);

		// HouseDaoを使ってデータを取得
		/*HouseDao houseDao = new HouseDao();*/
		List<HouseBeans> houseList = houseDao.findAll();
		// リクエスト属性にデータを設定
		request.setAttribute("houseList", houseList);

		//一覧		
		MovingDao movingDao = new MovingDao();
		List<MovingBeans> movingList = movingDao.searchAll();
		request.setAttribute("movingList", movingList);
		
		/*//テスト
		for (MovingBeans moving : movingList) {
			System.out.println("Moving Date: " + moving.getMoving_date());
			if (moving.getHouse() != null) {
				System.out.println("Order Number: " + moving.getHouse().getOrder_number());
				System.out.println("House Name: " + moving.getHouse().getHouse_name());
				System.out.println("Housing Type: " + moving.getHouse().getHousing_type());
			} else {
				System.out.println("No associated HouseBeans for this moving!");
			}
		}
		*/
		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/all.jsp");
		dispatcher.forward(request, response);

	}
}

