package book;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BookDataModel {
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private SimpleStringProperty phone;
	private SimpleIntegerProperty pid;
	private SimpleStringProperty title;
	private SimpleIntegerProperty rsvNo;
	private SimpleStringProperty returnDate;
	private SimpleStringProperty kind;
	
	public BookDataModel(BookDatas bd) {
		this.id = new SimpleIntegerProperty(bd.getId());
		this.name = new SimpleStringProperty(bd.getName());
		this.phone = new SimpleStringProperty(bd.getPhone());
		this.pid = new SimpleIntegerProperty(bd.getP_id());
		this.title = new SimpleStringProperty(bd.getTitle());
		this.rsvNo = new SimpleIntegerProperty(bd.getRsvNo());
		this.returnDate = new SimpleStringProperty(bd.getReturnDate().toString());
		this.kind = new SimpleStringProperty(bd.getKind());
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
	public String getPhone() {
		return phone.get();
	}
	public void setPhone(String phone) {
		this.phone.set(phone);
	}
	public int getPid() {
		return pid.get();
	}
	public void setPid(int pid) {
		this.pid.set(pid);
	}
	public String getTitle() {
		return title.get();
	}
	public void setTitle(String title) {
		this.title.set(title);
	}
	public int getRsvNo() {
		return rsvNo.get();
	}
	public void setRsvNo(int rsvNo) {
		this.rsvNo.set(rsvNo);
	}
	public Date getReturnDate() {
		return Date.valueOf(returnDate.get());
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate.set(returnDate.toString());
	}
	public String getKind() {
		return kind.get();
	}
	public void setKind(String kind) {
		this.kind.set(kind);
	}
	
}
