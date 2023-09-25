package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/Update")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		int house_id = Integer.parseInt(request.getParameter("id"));
		String order_date_string = request.getParameter("order_date");
		String order_number = request.getParameter("order_number");

		// SimpleDateFormatを利用して文字列をDateオブジェクトに変換
		// 文字列からDate型への変換
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false); 
		java.util.Date utilOrderDate = null;
		try {
			utilOrderDate = sdf.parse(order_date_string);
			// java.sql.Dateに変換
			java.sql.Date order_date = new java.sql.Date(utilOrderDate.getTime());

			HouseDao houseDao = new HouseDao();
			//新規物件ページで入力した物件の情報を物件詳細ページにわたす
			HouseBeans oneHouse = houseDao.findOne(order_number, order_date);
			List<HouseBeans> oneHouseList = new ArrayList<>();
			oneHouseList.add(oneHouse);
			//セッションスコープ
			HttpSession session = request.getSession();
			session.setAttribute("oneHouseList", oneHouseList);

			// 検索した搬入情報を搬入情報更新ページに渡す
			MovingDao movingDao = new MovingDao();
			List<MovingBeans> oneMovingList = movingDao.oneMoving(house_id);

			//リクエストスコープ
			request.setAttribute("oneMovingList", oneMovingList);

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/update.jsp");
			dispatcher.forward(request, response);

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}		
}
