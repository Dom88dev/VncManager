package book;

import java.sql.Date;

import rsrc.Util;

public class BookDatas {
	private int p_id;
	private int id;
	private Date returnDate;
	private String name;
	private String phone;
	private String title;
	private int rsvNo;
	private String kind;
	
	public BookDatas(int id, String name, String phone, int pid, String title, int bno, Date date, String kind) {
		setP_id(pid);
		setId(id);
		setReturnDate(date);
		setName(name);
		setPhone(phone);
		setTitle(title);
		setRsvNo(bno);
		setKind(kind);
		System.out.println(kind);
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
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date bookDate) {
		this.returnDate = bookDate;
	}
	public void setReturnDate(String bookDate) {
		this.returnDate = Util.transformDate(bookDate);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getRsvNo() {
		return rsvNo;
	}
	public void setRsvNo(int rsvNo) {
		this.rsvNo = rsvNo;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
	
	
}
