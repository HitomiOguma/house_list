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

import dao.HouseDao;
import dao.MovingDao;
import model.HouseBeans;
import model.MovingBeans;

/**
 * Servlet implementation class DetailServlet
 */
@WebServlet("/Detail")
public class DetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String order_number = request.getParameter("order_number");

		HouseDao houseDao = new HouseDao();

		//物件の情報を物件詳細ページにわたす
		HouseBeans oneHouse = houseDao.findOne1(order_number);
		List<HouseBeans> oneHouseList = new ArrayList<>();
		oneHouseList.add(oneHouse);
		//リクエストスコープ
		request.setAttribute("oneHouseList", oneHouseList);

		//搬入情報を物件詳細ページに渡す
		int house_id = Integer.parseInt(request.getParameter("id"));
		MovingDao movingDao = new MovingDao();
		List<MovingBeans> oneMovingList = movingDao.oneMoving(house_id);
		//リクエストスコープ
		request.setAttribute("oneMovingList", oneMovingList);

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/detail.jsp");
		dispatcher.forward(request, response);

	}
}
