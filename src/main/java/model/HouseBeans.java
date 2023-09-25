package model;

import java.io.Serializable;
import java.util.Date;

public class HouseBeans implements Serializable{

	private int id;
	private String order_number;
	private String house_name;
	private String housing_type;
	private String address;
	private String supervisor;
	private String construction_company;
	private int is_completed;
	private String remarks;
	private Date order_date;
	
	public HouseBeans() {}
	public HouseBeans(int id, String order_number, String house_name, String housing_type, String address, 
			String supervisor, String construction_company, int is_completed, String remarks, Date order_date) {
		this.id = id;
		this.order_number = order_number;
		this.house_name = house_name;
		this.housing_type = housing_type;
		this.address = address;
		this.supervisor = supervisor;
		this.construction_company = construction_company;
		this.is_completed = is_completed;
		this.remarks = remarks;
		this.order_date = order_date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public String getHouse_name() {
		return house_name;
	}

	public void setHouse_name(String house_name) {
		this.house_name = house_name;
	}

	public String getHousing_type() {
		return housing_type;
	}

	public void setHousing_type(String housing_type) {
		this.housing_type = housing_type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getConstruction_company() {
		return construction_company;
	}

	public void setConstruction_company(String construction_company) {
		this.construction_company = construction_company;
	}

	public int getIs_completed() {
		return is_completed;
	}

	public void setIs_completed(int is_completed) {
		this.is_completed = is_completed;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	
}
