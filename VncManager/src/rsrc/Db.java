package rsrc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import book.BookDatas;
import customer.CustomerDatas;
import product.ProductDatas;
import rent.RentReturnDatas;
/**
 * JDBC 를 각 기능별로 메소드화 한 클래스<br><br>
 * JDBC를 해야하는 클래스에서 선언을 하고 conncet() 메소드를 사용하면 DB와 연결된다.<br>
 * DB에서 처리할 작업을 각 메소드를 이용한 후 더이상 DB와의 연결이 필요하지 않을 때 close()메소드를 호출해준다.
 * 
 * @author Dominic
 *
 */
public class Db {
	private Connection con = null;
	private PreparedStatement stmt = null;	
	private CallableStatement callStmt = null;	// DB에 있는 프로시저 불러오는 클래스
	private ResultSet rs = null;
	
	/**
	 * DB와 연결하는 메소드
	 */
	public void connect() {
		try {
			Class.forName(Sql.DB_DRIVER);
			con = DriverManager.getConnection(Sql.DB_ADDR, Sql.DB_USER_NAME, Sql.DB_USER_PW);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	/**
	 * DB와의 커넥션을 닫아주는 클래스
	 */
	public void close() {
		try {
			con.close();
			if(stmt != null) stmt.close();
			if(callStmt != null) callStmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * DB에 생성되어 있는 회원가입 프로시져를 실행하는 메소드.
	 * @param datas
	 * 회원의 정보를 담은 CustomerDatas객체
	 * @return
	 * 처리 결과에 따라 다른 값이 반환된다.<br>
	 * 1 : 해당 프로시저의 작동이 정상완료 되었음.<br>
	 * 2 : 입력 값이 존재하지 않는다.<br>
	 * 3 : 일반 오류
	 */
	public int usp_register(CustomerDatas datas) {
		int result = 0;
		try {
			connect();
			callStmt = con.prepareCall(Sql.USP_REGISTER);
			callStmt.setString(1, datas.getName());
			callStmt.setString(2, datas.getTel());
			callStmt.setString(3, datas.getAddr());
			callStmt.setDate(4, datas.getBirth());
	        callStmt.registerOutParameter(5, Types.NUMERIC);
	        callStmt.executeQuery();
	        
	        result = callStmt.getInt(5);
	        close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int usp_register(String name, String tel, String addr, String birth) {
		int result = 0;
		try {
			connect();
			callStmt = con.prepareCall(Sql.USP_REGISTER);
			callStmt.setString(1, name);
			callStmt.setString(2, tel);
			callStmt.setString(3, addr);
			callStmt.setDate(4, Util.transformDate(birth));
	        callStmt.registerOutParameter(5, Types.NUMERIC);
	        callStmt.executeQuery();
	        
	        result = callStmt.getInt(5);
	        close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	/**
	 * DB에 생성되어 있는 비디오 추가 프로시저를 실행하는 메소드.
	 * @param datas
	 * product데이터 중 kind가 V(비디오)인 정보를 담은 ProductDatas객체
	 * @return
	 * 처리 결과에 따라 다른 값이 반환된다.<br>
	 * 1 : 해당 프로시저의 작동이 정상완료 되었음.<br>
	 * 2 : 입력 값이 존재하지 않는다.<br>
	 * 3 : 일반 오류
	 */
	public int usp_addv(ProductDatas datas) {
		int result = 0;
		try {
			connect();
			callStmt = con.prepareCall(Sql.USP_ADDV);
			callStmt.setString(1, datas.getTitle());
			callStmt.setString(2, datas.getGenre());
			callStmt.setInt(3, datas.getAge_grade());
			callStmt.setDate(4, datas.getRelease());
			callStmt.setInt(5, datas.getSupply());
			callStmt.setString(6, datas.getDirector());
			callStmt.setString(7, datas.getActor());
	        callStmt.registerOutParameter(8, Types.NUMERIC);
	        callStmt.executeQuery();
	        
	        result = callStmt.getInt(8);
	        
	        close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	/**
	 * DB에 생성되어 있는 만화책 추가 프로시저를 실행하는 메소드.
	 * @param datas
	 * product데이터 중 kind가 C(만화책)인 정보를 담은 ProductDatas객체
	 * @return
	 * 처리 결과에 따라 다른 값이 반환된다.<br>
	 * 1 : 해당 프로시저의 작동이 정상완료 되었음.<br>
	 * 2 : 입력 값이 존재하지 않는다.<br>
	 * 3 : 일반 오류
	 */
	public int usp_addc(ProductDatas datas) {
		int result = 0;
		try {
			connect();
			callStmt = con.prepareCall(Sql.USP_ADDC);
			callStmt.setString(1, datas.getTitle());
			callStmt.setString(2, datas.getGenre());
			callStmt.setInt(3, datas.getAge_grade());
			callStmt.setDate(4, datas.getRelease());
			callStmt.setInt(5, datas.getSupply());
			callStmt.setString(6, datas.getWriter());
	        callStmt.registerOutParameter(7, Types.NUMERIC);
	        callStmt.executeQuery();
	        
	        result = callStmt.getInt(7);
	        
	        close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	/**
	 * DB에 생성되어 있는 대여, 반납, 예약 중 하나의 프로시저를 실행하는 메소드
	 * @param p_id
	 * 처리되어 질 상품번호를 담은 int객체
	 * @param id
	 * 서비스를 행하는 회원번호를 담은 int객체
	 * @param procedure
	 * 사용하려는 프로시져에 따라 Sql클래스의 USP_RENT, USP_RETURN, USP_BOOK중 하나를 입력한다.
	 * @return
	 * 처리 결과에 따라 다른 값이 반환된다.<br>
	 * 1 : 프로시저가 정상적으로 실행 완료되었음.<br>
	 * 2 : 잘못된 상품번호다.<br>
	 * 3 : 일반오류
	 * 4 : 대여 프로시저일 경우 이미 다른 회원이 예약한 상품을 대여하려는 경우, <br>예약의 경우 이미 예약한 상품을 다시 예약하려는 경우에 반환되는 값이며, 프로시져는 처리되지 않는다.
	 */
	public int usp_rrb(int p_id, int id, String procedure) {
		int result = 0;
		try {
			connect();
			callStmt = con.prepareCall(procedure);
			callStmt.setInt(1, p_id);
			callStmt.setInt(2, id);
	        callStmt.registerOutParameter(3, Types.NUMERIC);
	        callStmt.executeQuery();
	        
	        result = callStmt.getInt(3);
	        close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	//TODO 데이터값 불러오는 메소드 작성
	public ArrayList<BookDatas> selectBookDatas() {
		ArrayList<BookDatas> bookDatas = new ArrayList<BookDatas>();
		connect();
		try {
			stmt = con.prepareStatement(Sql.SELECT_BOOKDATA);
	        rs = stmt.executeQuery();
	        
	        while(rs.next()) {
	        	bookDatas.add(new BookDatas(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getDate(7), rs.getString(8)));
	        }
	        close();
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bookDatas;
	}
	
	
	// DB의 customer 테이블 값을 가져오는 메소드
	public ArrayList<CustomerDatas> selectCustomerDatas(){
		ArrayList<CustomerDatas> customerDatas = new ArrayList<CustomerDatas>();
		connect();
		try {
			stmt = con.prepareStatement(Sql.SELECT_CUSTOMER);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				customerDatas.add(new CustomerDatas(rs.getInt(1), rs.getNString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getInt(6), rs.getInt(7), rs.getInt(8)));
			}
			close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return customerDatas;
	}
	
	public ArrayList<CustomerDatas> selectCustomerDatasOption(String column, String optionValue){
		ArrayList<CustomerDatas> customerDatas = new ArrayList<CustomerDatas>();
		connect();
		try {
			
			stmt = con.prepareStatement(column.equals("id")?Sql.SELECT_CUSTOMER_ID:column.equals("name")?Sql.SELECT_CUSTOMER_NAME:Sql.SELECT_CUSTOMER_TEL);
			stmt.setString(1, "%"+optionValue+"%");
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				customerDatas.add(new CustomerDatas(rs.getInt(1), rs.getNString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getInt(6), rs.getInt(7), rs.getInt(8)));
			}
			close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return customerDatas;
	}
	
	public ArrayList<ProductDatas> selectProductDatas(){
		ArrayList<ProductDatas> productDatas = new ArrayList<ProductDatas>();
		connect();
		try {
			stmt = con.prepareStatement(Sql.SELECT_PRODUCT);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				productDatas.add(new ProductDatas(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
							rs.getDate(6), rs.getString(7), rs.getInt(8)==1, rs.getInt(9),rs.getInt(10),
							rs.getString(11),rs.getString(12), rs.getString(13)));
			}
			close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return productDatas;
	}
	
	
	public ArrayList<ProductDatas> selectProductDatasOption(String column, String optionValue){
		ArrayList<ProductDatas> productDatas = new ArrayList<ProductDatas>();
		connect();
		try {
			stmt = con.prepareStatement(column.equals("p_id")?Sql.SELECT_PRODUCT_PID:column.equals("title")?Sql.SELECT_PRODUCT_TITLE:column.equals("genre")?Sql.SELECT_PRODUCT_GENRE:Sql.SELECT_PRODUCT_RDATE);
			stmt.setString(1, "%"+optionValue+"%");
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				productDatas.add(new ProductDatas(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
							rs.getDate(6), rs.getString(7), rs.getInt(8)==1, rs.getInt(9),rs.getInt(10),
							rs.getString(11),rs.getString(12), rs.getString(13)));
			}
			close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return productDatas;
	}
	
	public ArrayList<RentReturnDatas> selectRentReturnDatas(){
		ArrayList<RentReturnDatas> rentreturnDatas = new ArrayList<RentReturnDatas>();
		connect();
		try {
			stmt = con.prepareStatement(Sql.SELECT_RENT);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				rentreturnDatas.add(new RentReturnDatas(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getDate(5),
							rs.getDate(6), rs.getDate(7), rs.getInt(8), rs.getInt(9), rs.getString(10), rs.getString(11), rs.getString(12)));
				
			}
			close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rentreturnDatas;
	}
	
	
	//delete문
	
	public int deleteBooking(int id, int pid) {
		int result = 0;
		try {
			connect();
			stmt = con.prepareStatement(Sql.DELETE_BOOK);
			stmt.setInt(1, id);
			stmt.setInt(2, pid);
			result = stmt.executeUpdate();
	        
	        close();
	        return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	
	//회원가입 직후 회원번호를 가져오는 메소드
	public int getMemberId(){
		int id = 0;
		connect();
		try {
			stmt = con.prepareStatement(Sql.SELECT_ID_AFTER_REGISTER);
			rs = stmt.executeQuery();
			int maxId = rs.getInt(0);
			
			close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return id;
	}
	
	
	
}


