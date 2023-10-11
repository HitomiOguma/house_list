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
 * Servlet implementation class ListServlet
 */
@WebServlet("/List")
public class ListServlet extends HttpServlet {
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

		//予定一覧		
		MovingDao movingDao = new MovingDao();
		List<MovingBeans>movingList = movingDao.findAll();
		request.setAttribute("movingList", movingList);

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/list.jsp");
		dispatcher.forward(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

		//予定一覧		
		MovingDao movingDao = new MovingDao();
		List<MovingBeans>movingList = movingDao.findAll();
		request.setAttribute("movingList", movingList);

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/list.jsp");
		dispatcher.forward(request, response);
	}
}
