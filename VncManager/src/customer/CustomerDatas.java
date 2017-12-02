package customer;

import java.sql.Date;

import rsrc.Util;
/**
 * DB의 CUSTOMER테이블의 데이터들을 담기 위한 클래스
 * @author Dominic
 *
 */
public class CustomerDatas {
	private int id;
	private String name;
	private String addr;
	private String tel;
	private Date birth;
	private int age;
	private int countFamily;
	private int pw;
	
	
	public CustomerDatas(int id, String name, String addr, String tel, Date birth, int age, int countFamily, int pw) {
		setId(id);
		setName(name);
		setAddr(addr);
		setTel(tel);
		setBirth(birth);
		setAge(age);
		setCountFamily(countFamily);
		setPw(pw);
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

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}
	/**
	 * String타입의 날짜값을 java.sql.Date타입으로 바꾸어 birth에 저장하는 setter메소드
	 * @param birth
	 * yyyymmdd형의 날짜 String
	 * 
	 */
	public void setBirth(String birth) {
		this.birth = Util.transformDate(birth);
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public int getCountFamily() {
		return countFamily;
	}
	
	public void setCountFamily(int countFamily) {
		this.countFamily = countFamily;
	}
	
	public int getPw() {
		return pw;
	}
	
	public void setPw(int pw) {
		this.pw=pw;
	}

}
