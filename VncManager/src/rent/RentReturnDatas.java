package rent;

import java.sql.Date;

import rsrc.Util;

public class RentReturnDatas {
	private int p_id;
	private int id;
	private String name;
	private String title;
	private Date rentDate;
	private Date dueDate;
	private Date returnDate;
	private int lateDays;
	private int overdueFee;
	
	public RentReturnDatas(int P_id, int id, String name, String title,
			Date rentDate, Date dueDate, Date returnDate,
			int lateDays, int overdueFee) {
		setP_id(p_id);
		setId(id);
		setName(name);
		setTitle(title);
		setRentDate(rentDate);
		setDueDate(dueDate);
		setReturnDate(returnDate);
		setLateDays(lateDays);
		setOverdueFee(overdueFee);
		
	}
	
	public int getP_id() {
		return p_id;
	}
	public void setP_id(int p_id) {
		this.p_id = p_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getRentDate() {
		return rentDate;
	}
	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}
	public void setRentDate(String rentDate) {
		this.rentDate = Util.transformDate(rentDate);
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = Util.transformDate(dueDate);
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = Util.transformDate(returnDate);
	}
	public int getLateDays() {
		return lateDays;
	}
	public void setLateDays(int lateDays) {
		this.lateDays = lateDays;
	}
	public int getOverdueFee() {
		return overdueFee;
	}
	public void setOverdueFee(int overdueFee) {
		this.overdueFee = overdueFee;
	}
	
	
}
