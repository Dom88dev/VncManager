package product;

import java.sql.Date;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 * 상품 정보를 담기 위한 데이터 클래스.
 * 
 * @author Dominic
 *
 */
public class ProductDataModel {
	private SimpleIntegerProperty p_id;
	private SimpleStringProperty kind;
	private SimpleStringProperty title;
	private SimpleStringProperty genre;
	private SimpleIntegerProperty age_grade;
	private SimpleStringProperty release;
	private SimpleStringProperty edition;
	private SimpleStringProperty isRental;
	private SimpleIntegerProperty rentalCnt;
	private SimpleIntegerProperty supply;
	private SimpleStringProperty director;
	private SimpleStringProperty actor;
	private SimpleStringProperty writer;
	
	public ProductDataModel(ProductDatas pd) {
		this.p_id = new SimpleIntegerProperty(pd.getP_id());
		this.kind = new SimpleStringProperty(pd.getKind());
		this.title = new SimpleStringProperty(pd.getTitle());
		this.genre = new SimpleStringProperty(pd.getGenre());
		this.age_grade = new SimpleIntegerProperty(pd.getAge_grade());
		this.release = new SimpleStringProperty(pd.getRelease().toString());
		this.edition = new SimpleStringProperty(pd.getEdition());
		this.isRental = new SimpleStringProperty((pd.isRental()==true?"대여중":"대여가능"));
		this.rentalCnt = new SimpleIntegerProperty(pd.getRentalCnt());
		this.supply = new SimpleIntegerProperty(pd.getSupply());
		this.director = new SimpleStringProperty(pd.getDirector());
		this.actor = new SimpleStringProperty(pd.getActor());
		this.writer = new SimpleStringProperty(pd.getWriter());
		
	}
			
	
	
	public int getP_id() {
		return p_id.get();
	}
	public void setP_id(int p_id) {
		this.p_id.set(p_id) ;
	}
	public String getKind() {
		return kind.get();
	}
	public void setKind(String kind) {
		this.kind.set(kind);
	}
	public String getTitle() {
		return title.get();
	}
	public void setTitle(String title) {
		this.title.set(title);
	}
	public String getGenre() {
		return genre.get();
	}
	public void setGenre(String genre) {
		this.genre.set(genre);
	}
	public int getAge_grade() {
		return age_grade.get();
	}
	public void setAge_grade(int age_grade) {
		this.age_grade.set(age_grade);
	}
	public Date getRelease() {
		return Date.valueOf(release.get());
	}
	public void setRelease(Date release) {
		this.release.set(release.toString());
	}
	
	public String getEdition() {
		return edition.get();
	}
	public void setEdition(String edition) {
		this.edition.set(edition);
	}
	public String getIsRental() {
		return isRental.get();
	}
	public void setRental(boolean isRental) {
		this.isRental.set((isRental?"대여중":"대여가능"));
	}
	public int getRentalCnt() {
		return rentalCnt.get();
	}
	public void setRentalCnt(int rentalCnt) {
		this.rentalCnt.set(rentalCnt);
	}
	public int getSupply() {
		return supply.get();
	}
	public void setSupply(int supply) {
		this.supply.set(supply);
	}
	public String getDirector() {
		return director.get();
	}
	public void setDirector(String director) {
		this.director.set(director);
	}
	public String getActor() {
		return actor.get();
	}
	public void setActor(String actor) {
		this.actor.set(actor);
	}
	public String getWriter() {
		return writer.get();
	}
	public void setWriter(String writer) {
		this.writer.set(writer);
	}
	
}
