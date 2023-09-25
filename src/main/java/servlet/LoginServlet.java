package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.LoginDao;
import model.LoginBeans;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String password = request.getParameter("password");

		LoginDao loginDao = new LoginDao();
		LoginBeans login= loginDao.findAccount(name, password);

		//エラー処理
		if (login==null){
			request.setAttribute("error", "アカウント名またはパスワードが正しくありません");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);

		}else{

			//セッションスコープ
			HttpSession session = request.getSession();
			session.setAttribute("login", login);

			//ListServletへフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/List");
			dispatcher.forward(request, response);

		}
	}
}


