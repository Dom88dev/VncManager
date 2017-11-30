package product;

import java.sql.Date;

import rsrc.Util;
/**
 * 상품 정보를 담기 위한 데이터 클래스.
 * 
 * @author Dominic
 *
 */
public class ProductDatas {
	private int p_id;
	private String kind;
	private String title;
	private String genre;
	private int age_grade;
	private Date release;
	private String edition;
	private boolean isRental;
	private int rentalCnt;
	private int supply;
	private String director;
	private String actor;
	private String writer;
	
	public int getP_id() {
		return p_id;
	}
	public void setP_id(int p_id) {
		this.p_id = p_id;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public int getAge_grade() {
		return age_grade;
	}
	public void setAge_grade(int age_grade) {
		this.age_grade = age_grade;
	}
	public Date getRelease() {
		return release;
	}
	public void setRelease(Date release) {
		this.release = release;
	}
	/**
	 * String타입의 날짜값을 java.sql.Date타입으로 바꾸어 birth에 저장하는 setter메소드
	 * @param release
	 * yyyymmdd형의 날짜 String
	 * 
	 */
	public void setRelease(String release) {
		this.release = Util.transformDate(release);
	}
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public boolean isRental() {
		return isRental;
	}
	public void setRental(boolean isRental) {
		this.isRental = isRental;
	}
	public int getRentalCnt() {
		return rentalCnt;
	}
	public void setRentalCnt(int rentalCnt) {
		this.rentalCnt = rentalCnt;
	}
	public int getSupply() {
		return supply;
	}
	public void setSupply(int supply) {
		this.supply = supply;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	
}
