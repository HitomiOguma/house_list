package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MovingDao;
import model.MovingBeans;

/**
 * Servlet implementation class MovingUpdateServlet
 */
@WebServlet("/MovingUpdate")
public class MovingUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		int house_id = Integer.parseInt(request.getParameter("house_id"));
		String order_number = request.getParameter("order_number");
		String moving_date_string = request.getParameter("moving_date");
		String moving_item = request.getParameter("moving_item");
		String truck_type = request.getParameter("truck_type");

		// SimpleDateFormatを利用して文字列をDateオブジェクトに変換
		// 文字列からDate型への変換
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false); 
		java.util.Date utilMovingDate = null;
		try {
			utilMovingDate = sdf.parse(moving_date_string); 

			// java.sql.Dateに変換
			java.sql.Date moving_date = new java.sql.Date(utilMovingDate.getTime());

			MovingDao movingDao = new MovingDao();
			movingDao.addMoving(house_id, order_number, moving_date, moving_item, truck_type);

			// 検索した搬入情報を物件詳細ページに渡す
			List<MovingBeans> oneMovingList = movingDao.oneMoving(house_id);

			//リクエストスコープ
			request.setAttribute("oneMovingList", oneMovingList); 
			System.out.println(oneMovingList);

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/detail.jsp");
			dispatcher.forward(request, response);
		} catch (ParseException e) {
			e.printStackTrace();

		}

	}
}