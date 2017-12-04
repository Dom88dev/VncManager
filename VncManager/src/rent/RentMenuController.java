package rent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import customer.CustomerDataModel;
import customer.CustomerDatas;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.scene.control.ListView;
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

public class RentMenuController implements Initializable {
	private Db db = new Db();
	//================================= tab1 =============================================
	private ArrayList<RentReturnDatas> rds = new ArrayList<RentReturnDatas>();
	private ArrayList<RentReturnDatasModel> rdms = new ArrayList<RentReturnDatasModel>();
	// 대여목록 선택시 해당 대여정보의 상품번호와 회원번호를 답아놓을 인스턴스
	private int returnPid=0, returnId=0, lateReturnFee = 0;
	private boolean isReturn = false;
	
	// 대여조회화면 콤보박스 및 초이스박스 선택항목 리스트
	private ObservableList<String> rentKindList = FXCollections.observableArrayList("전체", "대여중", "반납");
	private ObservableList<String> searchKindList = FXCollections.observableArrayList("상품번호", "제목", "회원번호", "이름", "전화번호");
	// 대여조회화면 조회 결과 테이블의 검색 결과값을 담아놓기위한 리스트
	private ObservableList<RentReturnDatasModel> rentReturnList = FXCollections.observableArrayList();
	
	//tab1 죄회/반납 - 컨트롤 연결
	@FXML private BorderPane rent;
	@FXML private TabPane tab;
	@FXML private Button r_homeBtn;
	@FXML private ChoiceBox<String> rent_t1_pKindChoiceBox;
	@FXML private ComboBox<String> rent_t1_searchKindComboBox;
	@FXML private TextField rent_t1_searchText;
	@FXML private Button rent_t1_searchBtn;
	@FXML private TableView<RentReturnDatasModel> rentListTable;
	@FXML private ListView<String> rent_t1_infoTitleListView;
	@FXML private ListView<String> rent_t1_infoDataListView;
		
		
		
	//================================== tab2 =============================================
	private ArrayList<ProductDatas> pds = new ArrayList<ProductDatas>();
	private ArrayList<ProductDataModel> pdms = new ArrayList<ProductDataModel>();
	private ArrayList<CustomerDatas> cds = new ArrayList<CustomerDatas>();
	private ArrayList<CustomerDataModel> cdms = new ArrayList<CustomerDataModel>();
	// 회원목록과 상품목록 선택시 해당정보의 상품번호와 회원번호를 답아놓을 인스턴스
	private int rentPid=0, rentId=0, rentCost=0, rentAge=0, rentAgeLimit=0;
	
	// 대여화면 콤보박스 및 초이스박스 선택항목 리스트
	private ObservableList<String> cSearchKindList = FXCollections.observableArrayList("회원번호", "이름", "전화번호");
	private ObservableList<String> pSearchKindList = FXCollections.observableArrayList("상품번호", "제목", "장르", "출시일");
	private ObservableList<String> rentAvailableKindList = FXCollections.observableArrayList("전체", "대여가능", "대여불가");
	// 대여화면 조회 결과 테이블의 검색 결과값을 담아놓기위한 리스트
	private ObservableList<CustomerDataModel> customerList = FXCollections.observableArrayList();
	private ObservableList<ProductDataModel> productList = FXCollections.observableArrayList();
	
	
	private ObservableList<String> infoTitleList = FXCollections.observableArrayList();
	private ObservableList<String> infoDataList = FXCollections.observableArrayList();
	
	//tab2 - 대여 컨트롤 연결
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
		rent_t1_pKindChoiceBox.setValue("전체");
		rent_t1_pKindChoiceBox.setItems(rentKindList);
		rent_t1_searchKindComboBox.setItems(searchKindList);
		//대여화면의 콤보박스 및 초이스박스 아이템 설정
		customerSearchKind.setItems(cSearchKindList);
		productSearchKind.setItems(pSearchKindList);
		productKind.setValue("전체");
		productKind.setItems(rentAvailableKindList);
		customerSearchTable.setItems(customerList);// table뷰에 셋팅
		productSearchTable.setItems(productList);
		
		TableColumn<RentReturnDatasModel, Integer> tcP_id = (TableColumn<RentReturnDatasModel, Integer>) rentListTable.getColumns().get(0);
		TableColumn<RentReturnDatasModel, String> tcTitle = (TableColumn<RentReturnDatasModel, String>) rentListTable.getColumns().get(1);
		TableColumn<RentReturnDatasModel, Integer> tcId = (TableColumn<RentReturnDatasModel, Integer>) rentListTable.getColumns().get(2);
		TableColumn<RentReturnDatasModel, String> tcName = (TableColumn<RentReturnDatasModel, String>) rentListTable.getColumns().get(3);
		TableColumn<RentReturnDatasModel, String> tcPhone = (TableColumn<RentReturnDatasModel, String>) rentListTable.getColumns().get(4);
		
		tcP_id. setCellValueFactory(new PropertyValueFactory<RentReturnDatasModel, Integer>("p_id"));
		tcId.setCellValueFactory(new PropertyValueFactory<RentReturnDatasModel, Integer>("id"));
		tcName.setCellValueFactory(new PropertyValueFactory<RentReturnDatasModel, String>("name"));
		tcTitle.setCellValueFactory(new PropertyValueFactory<RentReturnDatasModel, String>("title"));
		tcPhone.setCellValueFactory(new PropertyValueFactory<RentReturnDatasModel, String>("phone"));
			
		
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
		
		//tqb1 대여목록 조회 화면
		//대여목록 초기 로딩 처리 ====시작
		rds = db.selectRentReturnDatas();
		for(RentReturnDatas rd : rds) {
			rdms.add(new RentReturnDatasModel(rd));
		}
		rentReturnList.addAll(rdms);
		rentListTable.setItems(rentReturnList);
		//====끝
		
	

		//테이블뷰 및 선택박스 리스너 바인딩 ====시작
		rentListTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RentReturnDatasModel>() {

			@Override
			public void changed(ObservableValue<? extends RentReturnDatasModel> observable, RentReturnDatasModel oldValue,
					RentReturnDatasModel newValue) {
					// TODO Auto-generated method stub
					if(newValue!=null) {
						int lateDay = 0;
						if(!infoTitleList.isEmpty()) {
							infoTitleList.removeAll("대여일", "반납예정일", "반납일", "연체일", "연체료");
							lateDay = (int)(System.currentTimeMillis()-Util.trnasformMilliseconds(oldValue.getDueDate()))/(1000*60*60*24);
							if((System.currentTimeMillis()-Util.trnasformMilliseconds(oldValue.getDueDate()))%(1000*60*60*24)!=0) lateDay++;
							if(lateDay>0) {
								infoDataList.removeAll(oldValue.getRentDate().toString(), oldValue.getDueDate().toString(),
										oldValue.getReturnDate().toString(), String.valueOf(lateDay), String.valueOf(lateReturnFee));
							} else {
								infoDataList.removeAll(oldValue.getRentDate().toString(), oldValue.getDueDate().toString(),
										oldValue.getReturnDate().toString(), String.valueOf(oldValue.getLateDays()), String.valueOf(oldValue.getOverdueFee()));
							}
						}
					
						infoTitleList.addAll("대여일", "반납예정일", "반납일", "연체일", "연체료");
						rent_t1_infoTitleListView.setItems(infoTitleList);
						
						lateDay = (int)(System.currentTimeMillis()-Util.trnasformMilliseconds(newValue.getDueDate()))/(1000*60*60*24);
						if((System.currentTimeMillis()-Util.trnasformMilliseconds(newValue.getDueDate()))%(1000*60*60*24)!=0) lateDay++;
						if(lateDay>0) {
							lateReturnFee = lateDay*Util.getLateFee(newValue.getEdition(), newValue.getKind());
							infoDataList.addAll(newValue.getRentDate().toString(), newValue.getDueDate().toString(),
									newValue.getReturnDate().toString(), String.valueOf(lateDay), String.valueOf(lateReturnFee));
							
							
						} else {
							infoDataList.addAll(newValue.getRentDate().toString(), newValue.getDueDate().toString(),
									newValue.getReturnDate().toString(), String.valueOf(newValue.getLateDays()), String.valueOf(newValue.getOverdueFee()));
						}
						rent_t1_infoDataListView.setItems(infoDataList);
						returnPid = newValue.getP_id();
						returnId = newValue.getId();
						isReturn = !newValue.getReturnDate().equals("미반납");
					}
				}
			
			}
		);
		
		rent_t1_pKindChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if(newValue!=null){
					rentReturnList.removeAll(rdms);
					if(newValue.equals("전체")) {
						rentReturnList.addAll(rdms);
					} else {
						for(RentReturnDatasModel rd : rdms) {
							switch(newValue) {
							case "대여중":
								if(rd.getReturnDate().equals("미반납"))	rentReturnList.add(rd);
								break;
							case "반납":
								if(!rd.getReturnDate().equals("미반납"))	rentReturnList.add(rd);
								break;
							}
						}
					}
				}
				
			}
		});
		//====끝
		
		
		//tab2 예약추가 화면 
		//테이블 뷰, 선택박스에 리스너 바인딩 ====시작
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
							case "대여가능":
								if(pd.getIsRental().equals("대여가능"))	productList.add(pd);
								break;
							case "대여불가":
								if(pd.getIsRental().equals("대여중"))	productList.add(pd);
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
				if(newValue!=null)	{
					rentId = newValue.getId();
					rentAge = newValue.getAge();
				}
			}
			
		});
		
		productSearchTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProductDataModel>(){
	
			@Override
			public void changed(ObservableValue<? extends ProductDataModel> arg0, ProductDataModel arg1,
					ProductDataModel newValue) {
				// TODO Auto-generated method stub
				if(newValue!=null) {
					rentPid = newValue.getP_id();
					rentCost = Util.getRentCost(newValue.getEdition(), newValue.getKind());
					rentAgeLimit = newValue.getAge_grade();
				}
			}
			
		});
		//====== 끝
		
		
		//테이블 플레이스홀더 지정
		Label label = new Label("검색한 결과가 없습니다.");
		rentListTable.setPlaceholder(label);
		customerSearchTable.setPlaceholder(label);
		productSearchTable.setPlaceholder(label);
		
		//tab 선택 리스너 등록
		tab.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

			@Override
			public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab newTab) {
				// TODO Auto-generated method stub
				if(newTab.getId().equals("searchTab"))	refreshList();
			}
			
		});
		
	}
	
	//대여화면의 회원조회 메소드
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
	
	//대여목록 조회 버튼 메소드
	public void searchRentList(ActionEvent e) {
		String search = rent_t1_searchText.getText();
		String sKind = rent_t1_searchKindComboBox.getValue();
		if(sKind==null) {
			popNoti("조건을 선택하고 검색하세요.");
			return;
		}
		rentReturnList.removeAll(rdms);
		if(search.equals("")) {
			rentReturnList.addAll(rdms);
		} else {
			for(RentReturnDatasModel rd : rdms) {//상품번호", "제목", "회원번호", "이름", "전화번호
				switch(sKind){
				case "상품번호":
					if(String.valueOf(rd.getP_id()).contains(search))	rentReturnList.add(rd);
					break;
				case "제목":
					if(rd.getTitle().toLowerCase().contains(search.toLowerCase()))	rentReturnList.add(rd);
					break;
				case "회원번호":
					if(String.valueOf(rd.getId()).contains(search))	rentReturnList.add(rd);
					break;
				case "이름":
					if(rd.getName().toLowerCase().contains(search.toLowerCase()))	rentReturnList.add(rd);
					break;
				case "전화번호":
					if(rd.getPhone().contains(search))	rentReturnList.add(rd);
					break;
				}
			}
		}
	}
	
	//반납버튼 메소드
	public void clickReturn(ActionEvent e) {
		if(returnPid<1 || returnId<1) {
			popNoti("반납할 정보를 선택하고 반납처리를 하십시오.");
			return;
		}
		if(isReturn) {
			popNoti("해당 회원에게서 이미 반납된 상품입니다.");
			return;
		}
		int result = db.usp_rrb(returnPid, returnId, Sql.USP_RETURN);
		if(result == 1) {
			if(lateReturnFee>0) popNoti("상품이 반납되었습니다. "+lateReturnFee+"원의 연체료가 있습니다.");
			else popNoti("상품이 반납되었습니다.");
			refreshList();
			refreshList();
		} else if(result ==2) {
			popNoti("없는 상품입니다.");
		} else if(result == 3) {
			popNoti("반납 처리 중 오류가 발생했습니다.");
		}
	}
	
	
	
	//대여버튼 메소드
	public void clickRent(ActionEvent e) {
		if(rentPid<1 || rentId<1) {
			popNoti("상품과 회원을 선택하고 대여처리를 하십시오.");
			return;
		}
		if(rentAge<=rentAgeLimit) {
			popNoti("현재 회원님은 해당 상품의 대여연령에 맞지 않습니다.");
			return;
		}
		int result = db.usp_rrb(rentPid, rentId, Sql.USP_RENT);
		if(result == 1) {
			popNoti("상품이 대여되었습니다. 대여료는 "+rentCost+"원입니다.");
		} else if(result == 4) {
			popNoti("이미 다른 회원에게 예약된 상품입니다.");
		} else if(result ==2) {
			popNoti("없는 상품입니다.");
		} else if(result == 3) {
			popNoti("대여 처리 중 오류가 발생했습니다.");
		}
	}
	
	//대여목록 새로고침 메소드
	public void refreshList() {
		rentReturnList.removeAll(rdms);
		rdms = new ArrayList<RentReturnDatasModel>();
		rds = db.selectRentReturnDatas();
		for(RentReturnDatas rd : rds){
			rdms.add(new RentReturnDatasModel(rd));
		}
		rentReturnList.addAll(rdms);
		rentListTable.setItems(rentReturnList);
	}
		
	public void gotoHome(ActionEvent e) {
		try {
			StackPane root = (StackPane) r_homeBtn.getScene().getRoot();
			
			//애니메이션 처리 - fade out효과
			rent.setOpacity(1);
			Timeline timeline = new Timeline();
			KeyValue keyValue = new KeyValue(rent.opacityProperty(), 0);
			KeyFrame keyFrame = new KeyFrame(Duration.millis(300),
					new EventHandler<ActionEvent>(){

							@Override
							public void handle(ActionEvent arg0) {
								// TODO Auto-generated method stub
								root.getChildren().remove(rent);
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
		Util.popNoti(notice, this.getClass().getName(), (Stage)rent.getScene().getWindow());
		
	}
	
}
