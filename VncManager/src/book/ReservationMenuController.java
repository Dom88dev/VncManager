package book;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import customer.CustomerDataModel;
import customer.CustomerDatas;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import product.ProductDataModel;
import product.ProductDatas;
import rsrc.Db;
import rsrc.Sql;
import rsrc.Util;

public class ReservationMenuController implements Initializable {
	private Db db = new Db();
	private ArrayList<BookDatas> bds = new ArrayList<BookDatas>();
	private ArrayList<BookDataModel> bdms = new ArrayList<BookDataModel>();
	private ArrayList<CustomerDatas> cds = new ArrayList<CustomerDatas>();
	private ArrayList<CustomerDataModel> cdms = new ArrayList<CustomerDataModel>();
	private ArrayList<ProductDatas> pds = new ArrayList<ProductDatas>();
	private ArrayList<ProductDataModel> pdms = new ArrayList<ProductDataModel>();
	
	private int bookingNo1=0, bookingNo2=0;
	
	//choiceBox의 선택항목 생성
	private ObservableList<String> productKindList = FXCollections.observableArrayList("전체", "비디오", "만화책");
	private ObservableList<String> searchKindList = FXCollections.observableArrayList("회원번호", "이름", "전화번호", "상품번호", "제목");
	private ObservableList<String> cSearchKindList = FXCollections.observableArrayList("회원번호", "이름", "전화번호");
	private ObservableList<String> pSearchKindList = FXCollections.observableArrayList("상품번호", "제목", "장르", "출시일");
	//tableView의 데이터모델 리스트 생성
	private ObservableList<BookDataModel> bookingList = FXCollections.observableArrayList();
	private ObservableList<CustomerDataModel> customerList = FXCollections.observableArrayList();
	private ObservableList<ProductDataModel> productList = FXCollections.observableArrayList();
	
	@FXML private BorderPane reservation;
	@FXML private TabPane tab;
	@FXML private Button b_homeBtn;
	@FXML private Tab b_searchTab;
	@FXML private Tab b_manageTap;
	
	//tab1 - 예약조회/삭제
	@FXML private ChoiceBox<String> pKindChoiceBox;
	@FXML private ComboBox<String> searchKindComboBox;
	@FXML private TextField searchTextField;
	@FXML private Button searchBtn;
	@FXML private TableView<BookDataModel> rsvListTable;
	
	//tab2 - 예약 추가
	@FXML private ComboBox<String> customerSearchKind;
	@FXML private TextField customerSearchText;
	@FXML private Button customerSearchBtn;
	@FXML private TableView<CustomerDataModel> customerSearchTable;
	@FXML private ComboBox<String> productSearchKind;
	@FXML private TextField productSearchText;
	@FXML private Button productSearchBtn;
	@FXML private ChoiceBox<String> productKind;
	@FXML private TableView<ProductDataModel> productSearchTable;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		pKindChoiceBox.setValue("전체");
		pKindChoiceBox.setItems(productKindList);
		searchKindComboBox.setItems(searchKindList);
		customerSearchKind.setItems(cSearchKindList);
		productSearchKind.setItems(pSearchKindList);
		productKind.setValue("전체");
		productKind.setItems(productKindList);
		customerSearchTable.setItems(customerList);// table뷰에 셋팅
		productSearchTable.setItems(productList);
		
		//table view setting
		//table column setting
		//예약목록 테이블
		TableColumn<BookDataModel, Integer> tcId = (TableColumn<BookDataModel, Integer>) rsvListTable.getColumns().get(0);
		TableColumn<BookDataModel, String> tcName = (TableColumn<BookDataModel, String>) rsvListTable.getColumns().get(1);
		TableColumn<BookDataModel, String> tcPhone = (TableColumn<BookDataModel, String>) rsvListTable.getColumns().get(2);
		TableColumn<BookDataModel, Integer> tcPid = (TableColumn<BookDataModel, Integer>) rsvListTable.getColumns().get(3);
		TableColumn<BookDataModel, String> tcTitle = (TableColumn<BookDataModel, String>) rsvListTable.getColumns().get(4);
		TableColumn<BookDataModel, Integer> tcRsvNo = (TableColumn<BookDataModel, Integer>) rsvListTable.getColumns().get(5);
		TableColumn<BookDataModel, String> tcReturnDate = (TableColumn<BookDataModel, String>) rsvListTable.getColumns().get(6);
		tcId.setCellValueFactory(new PropertyValueFactory<BookDataModel, Integer>("id"));
		tcName.setCellValueFactory(new PropertyValueFactory<BookDataModel, String>("name"));
		tcPhone.setCellValueFactory(new PropertyValueFactory<BookDataModel, String>("phone"));
		tcPid.setCellValueFactory(new PropertyValueFactory<BookDataModel, Integer>("pid"));
		tcTitle.setCellValueFactory(new PropertyValueFactory<BookDataModel, String>("title"));
		tcRsvNo.setCellValueFactory(new PropertyValueFactory<BookDataModel, Integer>("rsvNo"));
		tcReturnDate.setCellValueFactory(new PropertyValueFactory<BookDataModel, String>("returnDate"));
		
		//회원검색 테이블 
		TableColumn<CustomerDataModel, Integer> tccId = (TableColumn<CustomerDataModel, Integer>) customerSearchTable.getColumns().get(0);
		TableColumn<CustomerDataModel, String> tccName = (TableColumn<CustomerDataModel, String>) customerSearchTable.getColumns().get(1);
		TableColumn<CustomerDataModel, String> tccAge = (TableColumn<CustomerDataModel, String>) customerSearchTable.getColumns().get(2);
		TableColumn<CustomerDataModel, Integer> tccPhone = (TableColumn<CustomerDataModel, Integer>) customerSearchTable.getColumns().get(3);
		TableColumn<CustomerDataModel, String> tccBirth = (TableColumn<CustomerDataModel, String>) customerSearchTable.getColumns().get(4);
		TableColumn<CustomerDataModel, Integer> tccAddr = (TableColumn<CustomerDataModel, Integer>) customerSearchTable.getColumns().get(5);
		tccId.setCellValueFactory(new PropertyValueFactory<CustomerDataModel, Integer>("id"));
		tccName.setCellValueFactory(new PropertyValueFactory<CustomerDataModel, String>("name"));
		tccAge.setCellValueFactory(new PropertyValueFactory<CustomerDataModel, String>("age"));
		tccPhone.setCellValueFactory(new PropertyValueFactory<CustomerDataModel, Integer>("tel"));
		tccBirth.setCellValueFactory(new PropertyValueFactory<CustomerDataModel, String>("birth"));
		tccAddr.setCellValueFactory(new PropertyValueFactory<CustomerDataModel, Integer>("addr"));
		
		//상품검색테이블
		TableColumn<ProductDataModel, Integer> tcpPid = (TableColumn<ProductDataModel, Integer>) productSearchTable.getColumns().get(0);
		TableColumn<ProductDataModel, String> tcpKind = (TableColumn<ProductDataModel, String>) productSearchTable.getColumns().get(1);
		TableColumn<ProductDataModel, String> tcpTitle = (TableColumn<ProductDataModel, String>) productSearchTable.getColumns().get(2);
		TableColumn<ProductDataModel, String> tcpGenre = (TableColumn<ProductDataModel, String>) productSearchTable.getColumns().get(3);
		TableColumn<ProductDataModel, String> tcpRelease = (TableColumn<ProductDataModel, String>) productSearchTable.getColumns().get(4);
		tcpPid.setCellValueFactory(new PropertyValueFactory<ProductDataModel, Integer>("p_id"));
		tcpKind.setCellValueFactory(new PropertyValueFactory<ProductDataModel, String>("kind"));
		tcpTitle.setCellValueFactory(new PropertyValueFactory<ProductDataModel, String>("title"));
		tcpGenre.setCellValueFactory(new PropertyValueFactory<ProductDataModel, String>("genre"));
		tcpRelease.setCellValueFactory(new PropertyValueFactory<ProductDataModel, String>("release"));
		
		//tab1 에약조회 화면
		//예약목록 초기 로딩 처리 ====시작
		bds = db.selectBookDatas();
		for(BookDatas bd : bds){
			bdms.add(new BookDataModel(bd));
			}
		bookingList.addAll(bdms);
		rsvListTable.setItems(bookingList);
		//====끝
		
		//테이블 뷰 및 선택박스 리스너 바인딩 ====시작
		pKindChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if(newValue!=null){
					bookingList.removeAll(bdms);
					if(newValue.equals("전체")) {
						bookingList.addAll(bdms);
					} else {
						for(BookDataModel bd : bdms) {
							switch(newValue) {
							case "비디오":
								if(bd.getKind().equals("비디오"))	bookingList.add(bd);
								break;
							case "만화책":
								if(bd.getKind().equals("만화책"))	bookingList.add(bd);
								break;
							}
						}
					}
				}
				
			}
			
		});
		//==== 끝
	
		//tab2 예약추가 화면 
		//테이블 뷰 , 선택박스에 리스너 바인딩 ====시작
		productKind.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
	
			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if(newValue!=null){
					productList.removeAll(pdms);
					if(newValue.equals("전체")) {
						productList.addAll(pdms);
					} else {
						for(ProductDataModel pd : pdms) {
							switch(newValue) {
							case "비디오":
								if(pd.getKind().equals("비디오"))	productList.add(pd);
								break;
							case "만화책":
								if(pd.getKind().equals("만화책"))	productList.add(pd);
								break;
							}
						}
					}
				}
				
			}
			
		});
	
	
		customerSearchTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerDataModel>() {
	
			@Override
			public void changed(ObservableValue<? extends CustomerDataModel> arg0, CustomerDataModel old,
					CustomerDataModel newValue) {
				// TODO Auto-generated method stub
				if(newValue!=null)	bookingNo1 = newValue.getId();
			}
			
		});
		
		productSearchTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProductDataModel>(){
	
			@Override
			public void changed(ObservableValue<? extends ProductDataModel> arg0, ProductDataModel arg1,
					ProductDataModel newValue) {
				// TODO Auto-generated method stub
				if(newValue!=null)	bookingNo2 = newValue.getP_id();
			}
			
		});
		//====== 끝
		
		//테이블 플레이스홀더 지정
		//====시작
		Label label = new Label("검색한 결과가 없습니다.");
		rsvListTable.setPlaceholder(label);
		customerSearchTable.setPlaceholder(label);
		productSearchTable.setPlaceholder(label);
		//====끝
		
		
		//tab change Listener 구현
		tab.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

			@Override
			public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab newTab) {
				// TODO Auto-generated method stub
				if(newTab.getId().equals("searchTab"))	refreshList();
			}
			
		});
		
	}
	
	public void handleSearchBtn(ActionEvent e) {
		String search = searchTextField.getText();
		String sKind = searchKindComboBox.getValue();
		if(sKind==null) {
			popNoti("조건을 선택하고 검색하세요.");
			return;
		}
		bookingList.removeAll(bdms);
		if(search.equals("")) {
			bookingList.addAll(bdms);
		} else {
			for(BookDataModel bd : bdms) {
				switch(sKind){
				case "회원번호":
					if(String.valueOf(bd.getId()).equals(search))	bookingList.add(bd);
					break;
				case "이름":
					if(bd.getName().toLowerCase().contains(search.toLowerCase()))	bookingList.add(bd);
					break;
				case "전화번호":
					if(bd.getPhone().contains(search))	bookingList.add(bd);
					break;
				case "상품번호":
					if(String.valueOf(bd.getPid()).equals(search))	bookingList.add(bd);
					break;
				case "제목":
					if(bd.getTitle().toLowerCase().contains(search.toLowerCase()))	bookingList.add(bd);
					break;
				}
			}
		}
		
	}
	
	public void removeReservation(ActionEvent e) {
		if(rsvListTable.getSelectionModel().getSelectedItem() != null){
			int id = rsvListTable.getSelectionModel().getSelectedItem().getId();
			int pid = rsvListTable.getSelectionModel().getSelectedItem().getPid();
			
			int result = db.deleteBooking(id, pid);
			if(result>0) {
				popNoti("예약을 취소하셨습니다.");
				refreshList();
			} else {
				popNoti("에약취소를 실패하셨습니다.");
			}
			
		} else {
			popNoti("먼저 취소할 예약을 선택해 주십시오.");
		}	
		
	}
	
	//예약추가메뉴의 회원조회 메소드
	public void findCustomer() {
		String column = customerSearchKind.getValue();//검색조건
		String customerSearch = customerSearchText.getText();//검색어
		
		if(column==null) {
			popNoti("조건을 선택하고 검색하세요.");
			return;
		}
		//선택한 촘보박스값에 따라 칼럼명을 설정해준다.
		column = (column.equals("회원번호")?"id":column.equals("이름")?"name":"tel");
		
		
		//회원 검색결과 테이블을 비워준다.
		customerList.removeAll(cdms);
		cdms = new ArrayList<>();
		
		//select 조건문 실행
		cds = db.selectCustomerDatasOption(column, customerSearch);
		for(CustomerDatas cd : cds){//select문 결과값을 arrayList에 담아준다
			cdms.add(new CustomerDataModel(cd));
		}
		customerList.addAll(cdms);
		
	}
	
	public void findProduct() {
		String column = productSearchKind.getValue();
		String productSearch = productSearchText.getText();
		
		if(column==null) {
			popNoti("조건을 선택하고 검색하세요.");
			return;
		}
		column = (column.equals("상품번호")?"p_id":column.equals("제목")?"title":column.equals("장르")?"genre":"r_date");
		productList.removeAll(pdms);
		pdms = new ArrayList<>();
		pds = db.selectProductDatasOption(column, productSearch);
		for(ProductDatas pd : pds){
			pdms.add(new ProductDataModel(pd));
		}
		productList.addAll(pdms);
		
	}
	
	
	public void confirmBook(ActionEvent e) {
		if(bookingNo2<1 || bookingNo1<1) {
			popNoti("상품과 회원을 선택하고 예약하십시오.");
			return;
		}
		int result = db.usp_rrb(bookingNo2, bookingNo1, Sql.USP_BOOK);
		if(result == 1) {
			popNoti("예약이 접수 되었습니다.");
		} else if(result == 4) {
			popNoti("이미 예약한 상품입니다.");
		} else if(result ==2) {
			popNoti("없는 상품입니다.");
		} else if(result == 5) {
			popNoti("현재 대여 가능한 상품입니다.");
		} else {
			popNoti("예약중 오류가 발생했습니다.");
		}
	}
	
	//예약목록 새로고침 메소드
	public void refreshList() {
		bookingList.removeAll(bdms);
		bdms = new ArrayList<BookDataModel>();
		bds = db.selectBookDatas();
		for(BookDatas bd : bds){
			bdms.add(new BookDataModel(bd));
		}
		bookingList.addAll(bdms);
		rsvListTable.setItems(bookingList);
	}
	
	public void gotoHome(ActionEvent e) {
		try {
			StackPane root = (StackPane) b_homeBtn.getScene().getRoot();
			
			//애니메이션 처리 - fade out효과
			reservation.setOpacity(1);
			Timeline timeline = new Timeline();
			KeyValue keyValue = new KeyValue(reservation.opacityProperty(), 0);
			KeyFrame keyFrame = new KeyFrame(Duration.millis(300),
					new EventHandler<ActionEvent>(){

							@Override
							public void handle(ActionEvent arg0) {
								// TODO Auto-generated method stub
								root.getChildren().remove(reservation);
							}
							
						},
					keyValue);//0.3초간 애니메이션 실행
			timeline.getKeyFrames().add(keyFrame);
			timeline.play();
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void popNoti(String notice) {
		Util.popNoti(notice, this.getClass().getName(), (Stage)reservation.getScene().getWindow());
	}

	
}
