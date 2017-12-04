package rsrc;
/**
 * JDBC 드라이버와 사용될 쿼리문을 String형의 필드로 모아놓은 클래스
 * @author Dominic
 *
 */
public class Sql {
	//드라이버 정보
	public static final String DB_DRIVER = "oracle.jdbc.OracleDriver";
//	public static final String DB_ADDR = "jdbc:oracle:thin:@70.12.113.130:1521:orcl";
//	public static final String DB_USER_NAME = "m2";
//	public static final String DB_USER_PW = "1111";
	
	public static final String DB_ADDR = "jdbc:oracle:thin:@localhost:1521:xe";
	public static final String DB_USER_NAME = "user1";
	public static final String DB_USER_PW = "user1";
	
	//프로시저 쿼리문
	public static final String USP_REGISTER = "{call usp_register(?,?,?,?,?)}";
	public static final String USP_ADDV = "{call usp_addv(?,?,?,?,?,?,?,?)}";
	public static final String USP_ADDC = "{call usp_addc(?,?,?,?,?,?,?)}";
	public static final String USP_RENT = "{call usp_rent(?,?,?)}";
	public static final String USP_RETURN = "{call usp_return(?,?,?)}";
	public static final String USP_BOOK = "{call usp_book(?,?,?)}";
	
	//select문
	public static final String SELECT_CUSTOMER = "select c.id, c.name, c.addr, c.tel, c.birth, c.age, nvl(d.cntid, 0), c.pw "
			+ "from customer c left outer join (select id, count(id) cntid "
			+ "from family group by id) d on c.id=d.id";
	
	public static final String SELECT_FAMILY = "select name, relation from family where id = ?";
	public static final String SELECT_ID_AFTER_REGISTER = "select max(id) from customer";
	
	public static final String SELECT_CUSTOMER_ID = "select c.id, c.name, c.addr, c.tel, c.birth, c.age, nvl(d.cntid, 0), c.pw "
			+ "from customer c left outer join (select id, count(id) cntid "
			+ "from family group by id) d on c.id=d.id where c.id like ?";
	public static final String SELECT_CUSTOMER_NAME = "select c.id, c.name, c.addr, c.tel, c.birth, c.age, nvl(d.cntid, 0), c.pw "
			+ "from customer c left outer join (select id, count(id) cntid "
			+ "from family group by id) d on c.id=d.id where c.name like ?";
	public static final String SELECT_CUSTOMER_TEL = "select c.id, c.name, c.addr, c.tel, c.birth, c.age, nvl(d.cntid, 0), c.pw "
			+ "from customer c left outer join (select id, count(id) cntid "
			+ "from family group by id) d on c.id=d.id where c.tel like ?";
	
	
	public static final String SELECT_PRODUCT = 
			"select p_id, kind, title, genre, age_grade, "
			+ "r_date, edition, is_rental, rental_count, "
			+ "supply_c_id, director, main_actor, writer from product";
	
	public static final String SELECT_PRODUCT_PID = 
			"select p_id, kind, title, genre, age_grade, "
			+ "r_date, edition, is_rental, rental_count, "
			+ "supply_c_id, director, main_actor, writer from product where p_id like ?";
	
	public static final String SELECT_PRODUCT_TITLE = 
			"select p_id, kind, title, genre, age_grade, "
			+ "r_date, edition, is_rental, rental_count, "
			+ "supply_c_id, director, main_actor, writer from product where title like ?";
	public static final String SELECT_PRODUCT_GENRE = 
			"select p_id, kind, title, genre, age_grade, "
			+ "r_date, edition, is_rental, rental_count, "
			+ "supply_c_id, director, main_actor, writer from product where genre like ?";
	public static final String SELECT_PRODUCT_RDATE = 
			"select p_id, kind, title, genre, age_grade, "
			+ "r_date, edition, is_rental, rental_count, "
			+ "supply_c_id, director, main_actor, writer from product where r_date like ?";
	
	
	public static final String SELECT_RENT =  
			"select r.p_id, r.id, c.name, p.title, r.rent_date, r.due_date, nvl(r.return_date, to_date(19010101)), "
			+ "nvl(r.late_days, 0), nvl(r.overdue_fee, 0), c.tel , p.kind, p.edition from product p right outer join rent_return r on "
			+ "p.p_id = r.p_id left outer join customer c on r.id = c.id"; 
	public static final String SELECT_BOOKDATA = 
			"select b.ID, c.name, c.tel, b.p_id, p.title, b.b_no, r.due_date, p.kind"
			+ " from customer c join booking b on b.id = c.id"
			+ " join product p on b.p_id = p.p_id"
			+ " join rent_return r on p.p_id = r.p_id";
	
	//delete문
	public static final String DELETE_BOOK = "delete booking where id=? and P_id=?";
	
	
}
