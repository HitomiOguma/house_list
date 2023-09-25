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
 * Servlet implementation class houseUpdateServlet
 */
@WebServlet("/HouseUpdate")
public class HouseUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		String house_name = request.getParameter("house_name");
		String housing_type = request.getParameter("housing_type");
		String address = request.getParameter("address");
		String supervisor = request.getParameter("supervisor");
		String construction_company = request.getParameter("construction_company");
		String remarks = request.getParameter("remarks");
		String order_date_string = request.getParameter("order_date");
		String order_number = request.getParameter("order_number");

		String isCompletedParam = request.getParameter("is_completed");
		int is_completed;

		if (isCompletedParam != null && !isCompletedParam.isEmpty()) {
			is_completed = Integer.parseInt(isCompletedParam);
		} else {
			is_completed = 0; 
		}

		// SimpleDateFormatを利用して文字列をDateオブジェクトに変換
		// 文字列からDate型への変換
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false); 
		java.util.Date utilOrderDate = null;
		try {
			utilOrderDate = sdf.parse(order_date_string);
		} catch (ParseException e) {
			e.printStackTrace();
			// エラーハンドリング：日付の解析に失敗した場合、エラーメッセージを設定して適切に処理
			request.setAttribute("error", "受注日の形式が正しくありません");
			request.setAttribute("inputHouse_name", house_name); 
			request.setAttribute("inputHousing_type", housing_type); 
			request.setAttribute("inputAddress", address); 
			request.setAttribute("inputSupervisor", supervisor); 
			request.setAttribute("inputConstruction_company", construction_company); 
			request.setAttribute("inputIs_completed", is_completed); 
			request.setAttribute("inputRemarks", remarks); 
			request.setAttribute("inputOrder_number", order_number);  

			RequestDispatcher dispatcher = request.getRequestDispatcher("/new.jsp");
			dispatcher.forward(request, response);
			return; // 以降の処理を中止
		}
		// java.sql.Dateに変換
		java.sql.Date order_date = new java.sql.Date(utilOrderDate.getTime());

		//エラー処理
		if(order_number.isEmpty()||order_number.length()>10){
			request.setAttribute("error", "受注番号は10文字以内で必ず入力してください");
			request.setAttribute("inputHouse_name", house_name); 
			request.setAttribute("inputHousing_type", housing_type); 
			request.setAttribute("inputAddress", address); 
			request.setAttribute("inputSupervisor", supervisor); 
			request.setAttribute("inputConstruction_company", construction_company); 
			request.setAttribute("inputIs_completed", is_completed); 
			request.setAttribute("inputRemarks", remarks); 
			request.setAttribute("inputOrder_date", order_date); 

			RequestDispatcher dispatcher = request.getRequestDispatcher("/new.jsp");
			dispatcher.forward(request, response);  

		}else {

			HouseDao houseDao = new HouseDao();
			houseDao.updateHouse(id,order_number, house_name, housing_type, address, 
					supervisor, construction_company, is_completed, remarks, order_date);

			//新規物件ページで入力した物件の情報を物件詳細ページにわたす
			HouseBeans oneHouse = houseDao.findOne(order_number, order_date);
			List<HouseBeans> oneHouseList = new ArrayList<>();
			oneHouseList.add(oneHouse);
			//セッションスコープ
			HttpSession session = request.getSession();
			session.setAttribute("oneHouseList", oneHouseList);

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
}

