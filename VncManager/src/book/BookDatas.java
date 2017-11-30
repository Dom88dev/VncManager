package book;

import java.sql.Date;

import rsrc.Util;

public class BookDatas {
	private int p_id;
	private int id;
	private Date bookDate;
	
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
	public Date getBookDate() {
		return bookDate;
	}
	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}
	public void setBookDate(String bookDate) {
		this.bookDate = Util.transformDate(bookDate);
	}
	
	
}
