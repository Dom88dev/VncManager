package rent;

import java.sql.Date;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import rsrc.Util;

public class RentReturnDatasModel {
	private SimpleIntegerProperty p_id;
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private SimpleStringProperty title;
	private SimpleStringProperty rentDate;
	private SimpleStringProperty dueDate;
	private SimpleStringProperty returnDate;
	private SimpleIntegerProperty lateDays;
	private SimpleIntegerProperty overdueFee;
	
	public RentReturnDatasModel(RentReturnDatas rrd) {
		this.p_id = new SimpleIntegerProperty(rrd.getP_id());
		this.id = new SimpleIntegerProperty(rrd.getId());
		this.name = new SimpleStringProperty(rrd.getName());
		this.title = new SimpleStringProperty(rrd.getTitle());
		this.rentDate = new SimpleStringProperty(rrd.getRentDate().toString());
		this.dueDate = new SimpleStringProperty(rrd.getDueDate().toString());
		this.returnDate = new SimpleStringProperty(rrd.getReturnDate().toString());
		this.lateDays = new SimpleIntegerProperty(rrd.getLateDays());
		this.overdueFee = new SimpleIntegerProperty(rrd.getOverdueFee());	
	}
	
	public int getP_id() {
		return p_id.get();
	}
	public void setP_id(int p_id) {
		this.p_id.set(p_id);
	}
	public int getId() {
		return id.get();
	}
	public void setId(int id) {
		this.id.set(id);
	}
	public String getName() {
		return name.get();
	}
	public void setName(String name) {
		this.name.set(name);
	}
	public String getTitle() {
		return title.get();
	}
	public void setTitle(String title) {
		this.title.set(title);
	}
	public Date getRentDate() {
		return Date.valueOf(rentDate.get());
	}
	public void setRentDate(Date rentDate) {
		this.rentDate.set(rentDate.toString());
	}
	
	public Date getDueDate() {
		return Date.valueOf(dueDate.get());
	}
	public void setDueDate(Date dueDate) {
		this.dueDate.set(dueDate.toString());
	}
	
	public Date getReturnDate() {
		return Date.valueOf(returnDate.get());
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate.set(returnDate.toString());
	}

	public int getLateDays() {
		return lateDays.get();
	}
	public void setLateDays(int lateDays) {
		this.lateDays.set(lateDays);
	}
	public int getOverdueFee() {
		return overdueFee.get();
	}
	public void setOverdueFee(int overdueFee) {
		this.overdueFee.set(overdueFee);
	}
	
	
}
