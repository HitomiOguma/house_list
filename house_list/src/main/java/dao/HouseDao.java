package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.HouseBeans;

public class HouseDao {
	private final String JDBC_URL = "jdbc:h2:tcp://localhost/~/house_list";
	private final String DB_USER = "sa";
	private final String DB_PASS = "";

	/**
	 * 新規物件追加
	 * 
	 * @param order_number
	 * @param house_name
	 * @param housing_type
	 * @param address
	 * @param supervisor
	 * @param construction_company
	 * @param is_completed
	 * @param remarks
	 * @param order_date
	 */
	public void addHouse(String order_number, String house_name, String housing_type, String address,
			String supervisor, String construction_company, int is_completed, String remarks, Date order_date) {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql = "INSERT INTO HOUSE(ORDER_NUMBER, HOUSE_NAME, HOUSING_TYPE, ADDRESS, SUPERVISOR, "
					+ "CONSTRUCTION_COMPANY, IS_COMPLETED, REMARKS, ORDER_DATE) VALUES(?,?,?,?,?,?,?,?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, order_number);
			pStmt.setString(2, house_name);
			pStmt.setString(3, housing_type);
			pStmt.setString(4, address);
			pStmt.setString(5, supervisor);
			pStmt.setString(6, construction_company);
			pStmt.setInt(7, is_completed);
			pStmt.setString(8, remarks);
			pStmt.setDate(9, order_date);

			pStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 物件検索
	 * 
	 * @param order_number
	 * @param order_date
	 * @return HouseBeans
	 */
	public HouseBeans findOne(String order_number, Date order_date) {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql = "SELECT ID,ORDER_NUMBER, HOUSE_NAME, HOUSING_TYPE, ADDRESS, SUPERVISOR, "
					+ "CONSTRUCTION_COMPANY, IS_COMPLETED, REMARKS, ORDER_DATE FROM HOUSE WHERE ORDER_NUMBER=? AND ORDER_DATE=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, order_number);
			pStmt.setDate(2, order_date);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String house_name = rs.getString("house_name");
				String housing_type = rs.getString("housing_type");
				String address = rs.getString("address");
				String supervisor = rs.getString("supervisor");
				String construction_company = rs.getString("construction_company");
				int is_completed = rs.getInt("is_completed");
				String remarks = rs.getString("remarks");

				HouseBeans house = new HouseBeans(id, order_number, house_name, housing_type, address,
						supervisor, construction_company, is_completed, remarks, order_date);
				return house;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

		/**
		 * 物件検索(受注番号クリック）
		 * 
		 * @param order_number
		 * @return HouseBeans
		 */
		public HouseBeans findOne1(String order_number) {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql = "SELECT ID,ORDER_NUMBER, HOUSE_NAME, HOUSING_TYPE, ADDRESS, SUPERVISOR, "
					+ "CONSTRUCTION_COMPANY, IS_COMPLETED, REMARKS, ORDER_DATE FROM HOUSE WHERE ORDER_NUMBER=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, order_number);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String house_name = rs.getString("house_name");
				String housing_type = rs.getString("housing_type");
				String address = rs.getString("address");
				String supervisor = rs.getString("supervisor");
				String construction_company = rs.getString("construction_company");
				int is_completed = rs.getInt("is_completed");
				String remarks = rs.getString("remarks");
				Date order_date = rs.getDate("order_date");

				HouseBeans house = new HouseBeans(id, order_number, house_name, housing_type, address,
						supervisor, construction_company, is_completed, remarks, order_date);
				return house;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 物件更新
	 * 
	 * @param id
	 * @param order_number
	 * @param house_name
	 * @param housing_type
	 * @param address
	 * @param supervisor
	 * @param construction_company
	 * @param is_completed
	 * @param remarks
	 * @param order_date
	 */
	public void updateHouse(int id, String order_number, String house_name, String housing_type, String address,
			String supervisor, String construction_company, int is_completed, String remarks, Date order_date) {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql = "UPDATE HOUSE SET ORDER_NUMBER = ?, HOUSE_NAME = ?, HOUSING_TYPE = ?, ADDRESS = ?, SUPERVISOR = ?, CONSTRUCTION_COMPANY = ?, IS_COMPLETED = ?, REMARKS = ?, ORDER_DATE = ? WHERE ID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, order_number);
			pStmt.setString(2, house_name);
			pStmt.setString(3, housing_type);
			pStmt.setString(4, address);
			pStmt.setString(5, supervisor);
			pStmt.setString(6, construction_company);
			pStmt.setInt(7, is_completed);
			pStmt.setString(8, remarks);
			pStmt.setDate(9, order_date);
			pStmt.setInt(10, id);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 物件削除
	 * 
	 * @param id
	 */
	public void delete(int id) {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql = "DELETE FROM HOUSE WHERE ID =?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
 
	/**
	 * 物件タイプのデータを取得
	 * 
	 * @return String型のList
	 */
	public List<String> findHousingTypes() {
		List<String> housingTypeList = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql = "SELECT HOUSING_TYPE FROM HOUSE";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				String housingType = rs.getString("housing_type");
				housingTypeList.add(housingType);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return housingTypeList;
	}

	/**
	 * 第一便が今日以降の情報の取得
	 * 
	 * @param id
	 * @return　HouseBeans
	 */
	public HouseBeans findOne(int id) {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql = "SELECT ID,ORDER_NUMBER, HOUSE_NAME, HOUSING_TYPE, ADDRESS, SUPERVISOR, "
					+ "CONSTRUCTION_COMPANY, IS_COMPLETED, REMARKS, ORDER_DATE FROM HOUSE WHERE id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setInt(1, id);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				String order_number = rs.getString("order_number");
				Date order_date = rs.getDate("order_date");
				String house_name = rs.getString("house_name");
				String housing_type = rs.getString("housing_type");
				String address = rs.getString("address");
				String supervisor = rs.getString("supervisor");
				String construction_company = rs.getString("construction_company");
				int is_completed = rs.getInt("is_completed");
				String remarks = rs.getString("remarks");

				HouseBeans house = new HouseBeans(id, order_number, house_name, housing_type, address,
						supervisor, construction_company, is_completed, remarks, order_date);
				return house;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 検索結果
	 * 
	 * @param id
	 * @param order_number
	 * @param house_name
	 * @param housing_type
	 * @return HouseBeansのArrayList
	 */
	public List<HouseBeans> findSearchResult(int id, String order_number, String house_name, String housing_type) {
		List<HouseBeans> houseList = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<>();

		sql.append(
				"SELECT ID, ORDER_NUMBER, HOUSE_NAME, HOUSING_TYPE, ADDRESS, SUPERVISOR, CONSTRUCTION_COMPANY, IS_COMPLETED, REMARKS, ORDER_DATE FROM HOUSE WHERE 1=1 ");

		if (id > 0) {
			sql.append("AND ID = ? ");
			params.add(id);
		}
		if (!order_number.isEmpty()) {
			sql.append("AND ORDER_NUMBER = ? ");
			params.add(order_number);
		}
		if (!house_name.isEmpty()) {
			sql.append("AND HOUSE_NAME = ? ");
			params.add(house_name);
		}
		if (!housing_type.isEmpty()) {
			sql.append("AND HOUSING_TYPE = ? ");
			params.add(housing_type);
		}

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			PreparedStatement pStmt = conn.prepareStatement(sql.toString());

			for (int i = 0; i < params.size(); i++) {
				pStmt.setObject(i + 1, params.get(i));
			}

			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				Date order_date = rs.getDate("order_date");
				String address = rs.getString("address");
				String supervisor = rs.getString("supervisor");
				String construction_company = rs.getString("construction_company");
				int is_completed = rs.getInt("is_completed");
				String remarks = rs.getString("remarks");

				HouseBeans house = new HouseBeans(id, order_number, house_name, housing_type, address,
						supervisor, construction_company, is_completed, remarks, order_date);
				houseList.add(house);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return houseList;
	}
	
		/**
		 *すべて表示 
		 * 
		 * @return HouseBeansのArrayList
		 */
		public List<HouseBeans> findAll() {
			List<HouseBeans> houseList = new ArrayList<>();
			try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
				String sql = "SELECT ID, ORDER_NUMBER, HOUSE_NAME, HOUSING_TYPE, ADDRESS, SUPERVISOR, "
		                + "CONSTRUCTION_COMPANY, IS_COMPLETED, REMARKS, ORDER_DATE FROM HOUSE";
		        PreparedStatement pStmt = conn.prepareStatement(sql);
		        ResultSet rs = pStmt.executeQuery();
		        while (rs.next()) {  
		            int id = rs.getInt("id");
					String order_number = rs.getString("order_number");
					Date order_date = rs.getDate("order_date");
					String house_name = rs.getString("house_name");
					String housing_type = rs.getString("housing_type");
					String address = rs.getString("address");
					String supervisor = rs.getString("supervisor");
					String construction_company = rs.getString("construction_company");
					int is_completed = rs.getInt("is_completed");
					String remarks = rs.getString("remarks");

					HouseBeans house = new HouseBeans(id, order_number, house_name, housing_type, address,
		                    supervisor, construction_company, is_completed, remarks, order_date);
		            houseList.add(house);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return houseList;
		}

}
