package customer;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomerDataModel {
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private SimpleStringProperty addr;
	private SimpleStringProperty tel;
	private SimpleStringProperty birth;
	private SimpleIntegerProperty age;
	private SimpleIntegerProperty countFamily;
	private SimpleIntegerProperty pw;
	
	public CustomerDataModel(CustomerDatas bd) {
		this.id = new SimpleIntegerProperty(bd.getId());
		this.name = new SimpleStringProperty(bd.getName());
		this.addr = new SimpleStringProperty(bd.getAddr());
		this.tel = new SimpleStringProperty(bd.getTel());
		this.birth = new SimpleStringProperty(bd.getBirth().toString());
		this.age = new SimpleIntegerProperty(bd.getAge());
		this.countFamily = new SimpleIntegerProperty(bd.getCountFamily());
		this.pw = new SimpleIntegerProperty(bd.getPw());
	
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
	public String getAddr() {
		return addr.get();
	}
	public void setAddr(String addr) {
		this.addr.set(addr);
	}
	public String getTel() {
		return tel.get();
	}
	public void setTel(String tel) {
		this.tel.set(tel);
	}
	public Date getBirth() {
		return Date.valueOf(birth.get());
	}
	public void setBirth(Date birth) {
		this.birth.set(birth.toString());
	}
	public int getAge() {
		return age.get();
	}
	public void setAge(int age) {
		this.age.set(age);
	}
	
	public int getCountFamily() {
		return countFamily.get();
	}
	
	public void seCountFamily(int countFamily) {
		this.countFamily.set(countFamily);
	}
	
	public int getPw() {
		return pw.get();
	}
	
	public void setPw(int pw) {
		this.pw.get();
	}
	
}
