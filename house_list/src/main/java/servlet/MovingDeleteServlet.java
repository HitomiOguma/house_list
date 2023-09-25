package servlet;

import java.io.IOException;
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
 * Servlet implementation class MovingServlet
 */
@WebServlet("/MovingDelete")
public class MovingDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		int house_id = Integer.parseInt(request.getParameter("house_id"));

		MovingDao movingDao = new MovingDao();
		movingDao.deleteMoving(id);

		// 検索した搬入情報を搬入情報更新ページに渡す			
		List<MovingBeans> oneMovingList = movingDao.oneMoving(house_id);
		//リクエストスコープ
		request.setAttribute("oneMovingList", oneMovingList);		

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/movingUpdate.jsp");
		dispatcher.forward(request, response);
	}
}
