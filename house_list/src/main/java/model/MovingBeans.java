package model;
import java.io.Serializable;
import java.util.Date;

public class MovingBeans implements Serializable{

	private int id;
	private int house_id;
	private String order_number;
	private Date moving_date;
	private String moving_item;
	private String truck_type;
	private HouseBeans house;

	public MovingBeans() {}
	public MovingBeans(int id, int house_id, String order_number, Date moving_date, String moving_item, String truck_type){
		this.id = id;
		this.house_id = house_id;
		this.order_number = order_number;
		this.moving_date = moving_date;
		this.moving_item = moving_item;
		this.truck_type = truck_type;

	}	
		public MovingBeans(int id, int house_id, String order_number, Date moving_date, String moving_item, String truck_type, HouseBeans house){
			this.id = id;
			this.house_id = house_id;
			this.order_number = order_number;
			this.moving_date = moving_date;
			this.moving_item = moving_item;
			this.truck_type = truck_type;
			this.house = house;	

	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHouse_id() {
		return house_id;
	}
	public void setHouse_id(int house_id) {
		this.house_id = house_id;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public Date getMoving_date() {
		return moving_date;
	}
	public void setMoving_date(Date moving_date) {
		this.moving_date = moving_date;
	}
	public String getMoving_item() {
		return moving_item;
	}
	public void setMoving_item(String moving_item) {
		this.moving_item = moving_item;
	}
	public String getTruck_type() {
		return truck_type;
	}
	public void setTruck_type(String truck_type) {
		this.truck_type = truck_type;
	}
	public HouseBeans getHouse() {
		return house;
	}
	public void setHouse(HouseBeans house) {
		this.house = house;
	}


}
