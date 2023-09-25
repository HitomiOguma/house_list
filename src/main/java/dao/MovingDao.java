package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import model.HouseBeans;
import model.MovingBeans;

public class MovingDao {
	private final String JDBC_URL = "jdbc:h2:tcp://localhost/~/house_list";
	private final String DB_USER = "sa";
	private final String DB_PASS = "";

	/**
	 * 搬入情報検索
	 * 
	 * @param house_id
	 * @return MovingBeansのArrayList
	 */
	public List<MovingBeans> oneMoving(int house_id) {
		List<MovingBeans> movingList = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql = "SELECT ID, HOUSE_ID, ORDER_NUMBER, MOVING_DATE, MOVING_ITEM, TRUCK_TYPE "
					   + "FROM MOVING "
					   + "WHERE HOUSE_ID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, house_id);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String order_number = rs.getString("order_number");
				Date moving_date = rs.getDate("moving_date");
				String moving_item = rs.getString("moving_item");
				String truck_type = rs.getString("truck_type");
				MovingBeans moving = new MovingBeans(id, house_id, order_number, moving_date, moving_item, truck_type);
				movingList.add(moving);

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return movingList;
	}

	/**
	 * 搬入情報追加
	 * 
	 * @param house_id
	 * @param order_number
	 * @param moving_date
	 * @param moving_item
	 * @param truck_type
	 */
	public void addMoving(int house_id, String order_number, Date moving_date, String moving_item, String truck_type) {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql = "INSERT INTO MOVING(HOUSE_ID, ORDER_NUMBER, MOVING_DATE, MOVING_ITEM, TRUCK_TYPE) "
					   + "VALUES(?,?,?,?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, house_id);
			pStmt.setString(2, order_number);
			pStmt.setDate(3, moving_date);
			pStmt.setString(4, moving_item);
			pStmt.setString(5, truck_type);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 搬入情報削除(MovingDeleteServletより)
	 * 
	 * @param id
	 */
	public void deleteMoving(int id) {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql = "DELETE FROM MOVING "
					   + "WHERE ID= ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 搬入情報削除(DeleteServletより)
	 * 
	 * @param house_id
	 */
	public void deleteMoving2(int house_id) {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql = "DELETE FROM MOVING "
					+ "WHERE HOUSE_ID= ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, house_id);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * house_idが同じものは第一便のデータのみ取得し、その中で第一便が今日以降のリスト検索
	 * 
	 * @return MovingBeansのArrayList
	 */
	public List<MovingBeans> findAll() {
		List<MovingBeans> movingList = new ArrayList<>();

		String sql = "SELECT M.ID, M.HOUSE_ID, M.ORDER_NUMBER, M.MOVING_DATE, M.MOVING_ITEM, M.TRUCK_TYPE "
				   + "FROM MOVING M "
				   + "JOIN ("
				   + "  SELECT HOUSE_ID, MIN(MOVING_DATE) AS MIN_MOVING_DATE "
				   + "  FROM MOVING "
				   + "  GROUP BY HOUSE_ID"
				   + ") MinMovingDates "
				   + "ON M.HOUSE_ID = MinMovingDates.HOUSE_ID AND M.MOVING_DATE = MinMovingDates.MIN_MOVING_DATE "
				   + "WHERE M.MOVING_DATE > ?";
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			// プレースホルダに今日の日付を設定
			Calendar today = Calendar.getInstance();
			pStmt.setDate(1, new java.sql.Date(today.getTime().getTime()));
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				int house_id = rs.getInt("house_id");
				String order_number = rs.getString("order_number");
				Date moving_date = rs.getDate("moving_date");
				String moving_item = rs.getString("moving_item");
				String truck_type = rs.getString("truck_type");
				MovingBeans moving = new MovingBeans(id, house_id, order_number, moving_date, moving_item, truck_type);

				// Houseテーブルから該当の情報を取得
				HouseDao houseDao = new HouseDao(); 
				HouseBeans house = houseDao.findOne(house_id); 
				if (house != null) {
					// MovingBeansにHouseの情報を追加
					moving.setHouse(house);
				}

				movingList.add(moving);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movingList;
	}

	/**
	 * house_idが同じものは第一便のデータのみ取得し、検索
	 * 
	 * @param house_id
	 * @param order_number
	 * @param house_name
	 * @param housing_type
	 * @return MovingBeansのArrayList
	 */
	public List<MovingBeans> findSearch(int house_id, String order_number, String house_name, String housing_type) {
		List<MovingBeans> movingList = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<>();

		sql.append("SELECT M.ID, M.HOUSE_ID, M.ORDER_NUMBER, M.MOVING_DATE, M.MOVING_ITEM, M.TRUCK_TYPE, H.ORDER_NUMBER AS H_ORDER_NUMBER, H.HOUSE_NAME AS H_HOUSE_NAME, H.HOUSING_TYPE AS H_HOUSING_TYPE, H.IS_COMPLETED ")
		   .append("FROM HOUSE H ")
		   .append("LEFT JOIN (")
		   .append("  SELECT HOUSE_ID, MIN(MOVING_DATE) AS MIN_MOVING_DATE_FROM_MOVING ")
		   .append("  FROM MOVING GROUP BY HOUSE_ID ")
		   .append(") MinMovingDates ON H.ID = MinMovingDates.HOUSE_ID ")
		   .append("LEFT JOIN MOVING M ON H.ID = M.HOUSE_ID AND M.MOVING_DATE = MinMovingDates.MIN_MOVING_DATE_FROM_MOVING ")
		   .append("WHERE 1 = 1");


		if (house_id > 0) {
			sql.append("AND M.HOUSE_ID = ? ");
			params.add(house_id);
		}
		if (!order_number.isEmpty()) {
			sql.append("AND H.ORDER_NUMBER = ? ");
			params.add(order_number);
		}
		if (!house_name.isEmpty()) {
			sql.append("AND H.HOUSE_NAME = ? ");
			params.add(house_name);
		}
		if (!housing_type.isEmpty()) {
			sql.append("AND H.HOUSING_TYPE = ? ");
			params.add(housing_type);
		}

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			PreparedStatement pStmt = conn.prepareStatement(sql.toString());

			//パラメータの動的セット
			for (int i = 0; i < params.size(); i++) {
				pStmt.setObject(i + 1, params.get(i));
			}

			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				MovingBeans moving = new MovingBeans();
				HouseBeans house = new HouseBeans();

				house.setOrder_number(rs.getString("H_ORDER_NUMBER"));
				house.setHouse_name(rs.getString("H_HOUSE_NAME"));
				house.setHousing_type(rs.getString("H_HOUSING_TYPE"));
				house.setIs_completed(rs.getInt("IS_COMPLETED")); 

				if (rs.getObject("ID") != null) { // MOVINGテーブルのIDが存在する場合のみセット
					moving.setId(rs.getInt("ID"));
					moving.setHouse_id(rs.getInt("HOUSE_ID"));
					moving.setOrder_number(rs.getString("ORDER_NUMBER"));
					moving.setMoving_date(rs.getDate("MOVING_DATE"));
					moving.setMoving_item(rs.getString("MOVING_ITEM"));
					moving.setTruck_type(rs.getString("TRUCK_TYPE"));
				}

				moving.setHouse(house);
				movingList.add(moving);

			}


		} catch (SQLException e) {
			e.printStackTrace();
		}

		return movingList;
	}
	/**
	 * house_idが同じものは第一便のデータのみ取得し、すべて表示
	 * 
	 * @return MovingBeansのArrayList
	 */
	public List<MovingBeans> searchAll() {
		List<MovingBeans> movingList = new ArrayList<>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT M.ID, M.HOUSE_ID, M.ORDER_NUMBER, M.MOVING_DATE, M.MOVING_ITEM, M.TRUCK_TYPE, H.ORDER_NUMBER AS H_ORDER_NUMBER, H.HOUSE_NAME AS H_HOUSE_NAME, H.HOUSING_TYPE AS H_HOUSING_TYPE, H.IS_COMPLETED ")
		   .append("FROM HOUSE H ")
		   .append("LEFT JOIN (")
		   .append("  SELECT HOUSE_ID, MIN(MOVING_DATE) AS MIN_MOVING_DATE_FROM_MOVING ")
		   .append("  FROM MOVING GROUP BY HOUSE_ID")
		   .append(") MinMovingDates ON H.ID = MinMovingDates.HOUSE_ID ")
		   .append("LEFT JOIN MOVING M ON H.ID = M.HOUSE_ID AND M.MOVING_DATE = MinMovingDates.MIN_MOVING_DATE_FROM_MOVING ")
		   .append("WHERE 1 = 1");

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			PreparedStatement pStmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				MovingBeans moving = new MovingBeans();
				HouseBeans house = new HouseBeans();

				house.setOrder_number(rs.getString("H_ORDER_NUMBER"));
				house.setHouse_name(rs.getString("H_HOUSE_NAME"));
				house.setHousing_type(rs.getString("H_HOUSING_TYPE"));
				house.setIs_completed(rs.getInt("IS_COMPLETED")); 

				moving.setId(rs.getInt("ID"));
				moving.setHouse_id(rs.getInt("HOUSE_ID"));
				moving.setOrder_number(rs.getString("ORDER_NUMBER"));
				moving.setMoving_date(rs.getDate("MOVING_DATE"));
				moving.setMoving_item(rs.getString("MOVING_ITEM"));
				moving.setTruck_type(rs.getString("TRUCK_TYPE"));
				moving.setHouse(house);

				movingList.add(moving);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return movingList;
	}

}
