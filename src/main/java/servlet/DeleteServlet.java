package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HouseDao;
import dao.MovingDao;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/Delete")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		//搬入情報がない時の処理
		String houseIdParam = request.getParameter("house_id");
		if (houseIdParam != null && !houseIdParam.isEmpty()) {
			int house_id = Integer.parseInt(houseIdParam);     

			HouseDao houseDao = new HouseDao();
			houseDao.delete(id);

			MovingDao movingDao = new MovingDao();
			movingDao.deleteMoving2(house_id);
		}
		/*//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/list.jsp");
		dispatcher.forward(request, response);*/
		// リダイレクト
	    response.sendRedirect("/house_list/ListGet");


	}

}
