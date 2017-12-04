package customer;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.util.Duration;
import rsrc.Db;

/**
 * @author Hee
 * @since 2017. 12. 4.
 * @version 1.0
 * 
 * 비디오&만화 대여점 프로그램 중 회원관리에 관한 Controller Class
 * 
 */

public class CustomerMenuController implements Initializable{
	/**
	 *DB에 대한 설명.
	 *@param db DataBase를 연결해주는 Db 클래스의 db메소드 호출하는 참조 변수
	 */
	
	private Db db = new Db();
	
	/**
	 * bds에 대한 설명.
	 * @param bds CustomerDatas 데이터타입인 ArrayList 컬렉션을 호출하는 참조 변수
	 * @see CustomerDatas
	 */
	
	private ArrayList<CustomerDatas> bds = new ArrayList<CustomerDatas>();
	
	/**
	 * bdms에 대한 설명
	 * @param bdms CustomerDataModel 데이터타입인 ArriList 컬렉션 호출하는 참조 변수
	 * @see CustomerDataModel
	 */
	
	private ArrayList<CustomerDataModel> bdms = new ArrayList<CustomerDataModel>();
	

	/**
	 * searchKindList에 대한 설명
	 * @param 조회/수정/삭제 창 조회 탭에서 콤보박스의 검색 카테고리 값을 저장해주는 FXCollections 참조 변수
	 */
	
	private ObservableList<String> searchKindList = FXCollections.observableArrayList("회원번호", "이름", "전화번호", "주소");
	
	/**
	 * searchFamilyKindList에 대한 설명
	 * @param 조회/수정/삭제 창 회원 가족 관리 탭에서 콤보박스의 검색 카테고리 값을 저장해주는 FXCollections 참조 변수
	 */
	
	private ObservableList<String> searchFamilyKindList = FXCollections.observableArrayList("회원번호", "이름", "전화번호");
	
	/**
	 * familyKindList에 대한 설명
	 * @param 조회/수정/삭제 창 조회탭에서 초이스박스의 카테고리 값을 저장해주는 FXCollections 참조 변수
	 */

	private ObservableList<String> familyKindList = FXCollections.observableArrayList("전체", "가족 유", "가족 무");
	
	/**
	 * customerList에 대한 설명
	 * @param 조회/수정/삭제 창 조회탭에서 DataBase에서 불러운 값을 CustomerDataModel 형시에 따라 저장해주는 FXCollections 참조 변수
	 */
	
	private ObservableList<CustomerDataModel> customerList = FXCollections.observableArrayList();
	
	/**
	 * infoTitleList에 대한 설명
	 * @param 조회/수정/삭제 창 조회탭의 추가 정보란에서 추가 정보 데이터(이름)을 담는 FXCollections 참조 변수
	 */
	
	private ObservableList<String> infoTitleList = FXCollections.observableArrayList();
	
	
	/**
	 * infoDataList에 대한 설명
	 * @param 조회/수정/삭제 창 조회탭의 추가 정보란에서 추가 정보 데이터(추가 정보)를 담는 FXCollections 참조 변수
	 */
	
	private ObservableList<String> infoDataList = FXCollections.observableArrayList();
	
	/**
	 * 조회/수정/삭제 탭의 Xml의 fx:id 속성을 가진 Controller의 @FXML 어노테이션
	 * @param customer 전체 pane
	 * @param fKindChoiceBox 가족 유, 무, 전체 카테고리를 가진 ChoiceBox
	 * @param searchKindComboBox 검색 카테고리 ComboBox
	 * @param c_searchTab 조회/수정/삭제 Tab
	 * @param c_homeBtn  회원관리 창에서 처음 창으로 돌아가는 Button
	 * @param customerListTable 조회/수정/삭제의 회원정보를 나타내는 TableView.
	 * @param c_searchBtn  조회/수정/삭제 탭의 검색 Button
	 * @param c_searchFText  조회/수정/삭제 탭의 검색 TextField
	 * @param c_addInfoBtn  조회/수정/삭제 탭의 추가 수정 정보 Button
	 * @param c_addInfoListView1   조회/수정/삭제 탭의 추가 정보 란에서 테이블에서 선택된 레이블의 정보를 출력하는 ListView
	 * @param c_addInfoListView2  조회/수정/삭제 탭의 추가 정보 란에서 테이블에서 선택된 레이블의 정보를 출력해주는 ListView
	 * @param c_modifyId   조회/수정/삭제 탭에 회원정보의 아이디를 알려주는 라벨
	 * @param c_modifyNameFtext  조회/수정/삭제 탭에서 회원정보의 이름을 수정해주는 텍스트 필드 
	 * @param c_modifyAddrFtext  조회/수정/삭제 탭에서 회원정보의 이름을 수정해주는 텍스트 필드
	 * @param c_modifyTelFtext  조회/수정/삭제 탭에서 회원정보의 전화번호를 수정해주는 텍스트 필드
	 * @param c_modifyBirthFtext  조회/수정/삭제 탭에서 회원정보의 생년월일을 수정해주는 텍스트 필드
	 * @param c_modifyPwFtext  조회/수정/삭제 탭에서 회원정보의 비밀번호를 수정해주는 텍스트 필드
	 * @param c_modifyFnameFText //  조회/수정/삭제 탭의 가족 이름를 추가하거나 수정하는 텍스트 필드
	 * @param c_modifyFrelationFText   조회/수정/삭제 탭의 가족 관계를 추가하거나 수정하는 텍스트 필드
	 * @param c_modifyBtn   조회/수정/삭제 탭에 회원정보 수정을 실행해주는 '수정' 버튼
	 * @param c_modifyCancleBtn   조회/수정/삭제 탭에 회원정보 수정하는 중 취소하는 '취소' 버튼
	 * @param c_modifyAddBtn  조회/수정/삭제 탭에 가족 정보 추가하는 '추가' 버튼
	 * @param c_modifyFixBtn  조회/수정/삭제 탭에 가족 정보 수정하는 '수정' 버튼
	 * @param c_modifyDelBtn  조회/수정/삭제 탭에 가족 정보 삭제하는 '삭제' 버튼
	 * @param c_customerDropBtn  조회/수정/삭제 탭에 회원 수 중 최소를 실행해주는 '취소'버튼
	 */                                 
	
	// 조회/수정/삭제 탭 
	@FXML private BorderPane customer;                                              
	@FXML private ChoiceBox<String> fKindChoiceBox;                         
	@FXML private ComboBox<String> searchKindComboBox;               
	@FXML private Tab c_searchTab;                                                     
    @FXML private Button c_homeBtn;                                                  
	@FXML private TableView<CustomerDataModel> customerListTable;  
	@FXML private Button c_searchBtn;                                                 
	@FXML private TextField c_searchFText;                                          
	@FXML private Button c_addInfoBtn;                                               
	@FXML private ListView<String> c_addInfoListView1;                                      
	@FXML private ListView<String> c_addInfoListView2;
	@FXML private Label c_modifyId;                                                   
	@FXML private TextField c_modifyNameFtext;                                  
	@FXML private TextField c_modifyAddrFtext;                                   
	@FXML private TextField c_modifyTelFtext;                                      
	@FXML private TextField c_modifyBirthFtext;                                   
	@FXML private TextField c_modifyPwFtext;                                      
	@FXML private TextField c_modifyFnameFText;                                
	@FXML private TextField c_modifyFrelationFText;                            
	@FXML private Button c_modifyBtn;                                                
	@FXML private Button c_modifyCancleBtn;                                      
	@FXML private Button c_modifyAddBtn;                                          
	@FXML private Button c_modifyFixBtn;                                              
	@FXML private Button c_modifyDelBtn;                                           
	@FXML private Button c_customerDropBtn;    
	
	/**
	 * 추가 탭의 Xml의 fx:id 속성을 가진 Controller의 @FXML 어노테이션
	 * 	@param c_joinTab 가입 Tab
	 * @param c_addNameFtext 회원가입 탭에서 회원정보 중 이름을 추가해주는 TextField
	 * @param c_addAddrFText 회원가입 탭에서 회원정보 중 주소를 추가해주는 TextField
	 * @param c_addTelFText 회원가입 탭에서 회원정보 중 전화번호를 추가해주는 TextField
	 * @param c_addBirthFText 회원가입 탭에서 회원정보 중 생년월일을 추가해주는 TextField
	 * @param c_JoinBtn  회원가입 탭에 회원 가입의 가입을 실행해주는 '가입' Button
	 * @param c_JoinCancleBtn  회원가입 탭에 회원 가입 중 취소를 실행해주는 '취소' Button
	 */
	
	
	@FXML private Tab c_joinTab;  
	@FXML private TextField c_addNameFtext;                                        
	@FXML private TextField c_addAddrFText;                                         
	@FXML private TextField c_addTelFText;                                            
	@FXML private TextField c_addBirthFText;                                         
	@FXML private Button c_JoinBtn;                                                    
	@FXML private Button c_JoinCancleBtn;                                          

	/**
	 * 회원가족관리 탭의 Xml의 fx:id 속성을 가진 Controller의 @FXML 어노테이션
	 * @param c_modifyTab 회원가족관리 Tab 
	 * @param c_searchFamilyField 회원가족관리 검색 TextField
	 * @param c_searchFamilyComboBox 회원가족관리 탭에서 회원 검색에 쓰이는 ComboBox
	 * @param c_searchFamilyBtn 회원가족관리 탭에서 검색을 실행하는 Button
	 * @param c_customerFamilyNameField 회원가족관리 탭에서 회원의 가족 이름을 알려주는 TextField
	 * @param c_customerFamilyRelationField 회원가족관리 탭에서 회원의 가족 관계를 알려주는 TextField
	 * @param c_customerFamilyModiBtn 회원가족관리 탭에 회원 가입 중 수정을 실행해주는 '수정' Button
	 * @param c_customerFamilyCancleBtn  회원가족관리 탭에 회원 가입 중 수정을 취소해주는 '취소' Button
	 */
	
	// 가족 조회 수정 탭
	@FXML private Tab c_modifyTab;   
	@FXML private TextField c_searchFamilyField;
	@FXML private ComboBox <String> c_searchFamilyComboBox;
	@FXML private Button c_searchFamilyBtn;
	@FXML private TextField c_customerFamilyNameField;
	@FXML private TextField c_customerFamilyRelationField;
	@FXML private Button c_customerFamilyModiBtn;
	@FXML private Button c_customerFamilyCancleBtn;
	
	
	/**
	 * initialize() 메소드
	 * 메인 클래스의 실행 매개값을 얻어 애플리케이션을 이용할 수 있게 해준다.
	 * 이 메소드는 컨트롤러 객체가 생성되고 나서 호출되는데, 주로 UI 컨트롤의 초기화, 이벤트 핸들러 등록, 속성 감시 등의 코드가 작성된다.
	 */
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		/**
		 * 가족 유, 무, 전체 값을 가진 초이스 박스의 디폴트 값을 저장하는 메소드
		 */
		
		fKindChoiceBox.setValue("전체");
		
		/**
		 * 콤보박스의 값을 fKindChoiceBox에 담는 메소드
		 */
		
		fKindChoiceBox.setItems(familyKindList);
		
		/**
		 * 조회/수정/삭제 탭의 콤보 박스에서 쓸 값이 담겨있는 searchKindList를 searchKindComboBox 넣은 메소드
		 */
		
		searchKindComboBox.setItems(searchKindList);
		
		/**
		 * 회원가족관리 탭에서 콤보박스에서 쓸 값이 담겨있는 searchFamilyKindList를 searchKindComboBox 넣은 메소드
		 */
		
		c_searchFamilyComboBox.setItems(searchFamilyKindList);
		
		/**
		 * 
		 */
		
		// 조회 탭의 테이블 뷰의 각각의 테이블 컬럼을 지정해주는 메소드들.
		TableColumn<CustomerDataModel, Integer> tcId = (TableColumn<CustomerDataModel, Integer>)customerListTable.getColumns().get(0);
		TableColumn<CustomerDataModel, String> tcName = (TableColumn<CustomerDataModel, String>)customerListTable.getColumns().get(1);
		TableColumn<CustomerDataModel, String> tcAddr = (TableColumn<CustomerDataModel, String>)customerListTable.getColumns().get(2);
		TableColumn<CustomerDataModel, String> tcTel = (TableColumn<CustomerDataModel, String>)customerListTable.getColumns().get(3);
		TableColumn<CustomerDataModel, String> tcBirth = (TableColumn<CustomerDataModel, String>)customerListTable.getColumns().get(4);
		TableColumn<CustomerDataModel, Integer> tcAge = (TableColumn<CustomerDataModel, Integer>)customerListTable.getColumns().get(5);
		TableColumn<CustomerDataModel, Integer> tcCountFamily = (TableColumn<CustomerDataModel, Integer>)customerListTable.getColumns().get(6);
		
		// CustomerDataMdel 클래스를 이용하여, 조회 탭의 테이블 뷰의 각 테이블 컬럼에 어떤 데이터타입의 데이터가 들어갈지 정해주는 메소드.
		tcId.setCellValueFactory(new PropertyValueFactory<CustomerDataModel, Integer>("id"));
		tcName.setCellValueFactory(new PropertyValueFactory<CustomerDataModel, String>("name"));
		tcAddr.setCellValueFactory(new PropertyValueFactory<CustomerDataModel, String>("addr"));
		tcTel.setCellValueFactory(new PropertyValueFactory<CustomerDataModel, String>("tel"));
		tcBirth.setCellValueFactory(new PropertyValueFactory<CustomerDataModel, String>("birth"));
		tcAge.setCellValueFactory(new PropertyValueFactory<CustomerDataModel, Integer>("age"));
		tcCountFamily.setCellValueFactory(new PropertyValueFactory<CustomerDataModel, Integer>("countFamily"));
		
		// DB에서 가져온 값을 저장하는 bds ArratList 컬렉션 메소드.
		bds = db.selectCustomerDatas();
		for(CustomerDatas cd: bds) {
			bdms.add(new CustomerDataModel(cd));
		}
		
		// DB 값을 저장한 bds 값을 FX 컬렉션에 저장하는 메소드.
		customerList.addAll(bdms);
		
		// bdms의 값을 CustomerDataModel을 데이터 타입으로 한 테이블 뷰에 값을  넣은 로직.
		customerListTable.setItems(customerList);
		
		
		// 추가 정보란에 이름과 비밀번호 출력하는 이벤트
		customerListTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerDataModel>() {

			@Override
			public void changed(ObservableValue<? extends CustomerDataModel> observable, CustomerDataModel oldValue,
					CustomerDataModel newValue) {
				// TODO 추가 정보란에 이름과 비밀번호 출력하는 이벤트
				if(newValue!=null) {
					if(!infoTitleList.isEmpty()) {
						infoTitleList.removeAll("이름", "비밀번호");
						infoDataList.removeAll(oldValue.getName().toString(), String.valueOf(oldValue.getPw()));
					}
					
					infoTitleList.addAll("이름", "비밀번호");
					c_addInfoListView1.setItems(infoTitleList);
					infoDataList.addAll(newValue.getName().toString(), String.valueOf(newValue.getPw()));
					c_addInfoListView2.setItems(infoDataList);
				}
			}
			
		});
		
		customerListTable.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		// 조회 탭의 테이블 뷰에서 데이터를 찾을 수 없을 때 나타내 주는 라벨과 메소드
		Label label = new Label("검색한 결과가 없습니다.");
		customerListTable.setPlaceholder(label);
		
		
		// <메소드> - 조회 탭에서 가족 유, 가족무, 전체를 체크하는 초이스 박스 메소드
		fKindChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				// TODO 가족 유무 체크하는 초이스 박스
				if(newValue!=null){
					customerList.removeAll(bdms);
					if(newValue.equals("전체")) {
						customerList.addAll(bdms);
					} else {
						for(CustomerDataModel bd : bdms) {
							switch(newValue) {
							case "가족 유":
								if(bd.getCountFamily()>0)	customerList.add(bd);
								break;
							case "가족 무":
								if(bd.getCountFamily() == 0)	customerList.add(bd);
								break;
							}
						}
					}
				}
				
			}
			
		});
	}
	
	
	// <메소드> - 가입 탭에 회원정보의 추가 버튼을 실행하는 메소드
	public void handleJoinAction(ActionEvent e) {
		String jName = c_addNameFtext.getText();
		String jAddr = c_addAddrFText.getText();
		String jTel = c_addTelFText.getText();
		String jBirth = c_addBirthFText.getText();
		
		int result = db.usp_register(jName, jTel, jAddr, jBirth);
		if(result >0) {
			int joinId = db.getMemberId();
			popNoti(jName+"님 " + "회원 가입이 되었습니다." + "\nID는 " + joinId + " 입니다.");
		}
		else {
			popNoti("가입과정에서 오류가 발생했습니다.");
		}
		//TODO 처리된 result값에 따라 다이얼로그로 결과 처리
	}
	
	// 회원 등록시 테이블에 새로고침 해주는 메소드
	public void refreshList() {
		customerList.removeAll(bdms);
		bdms = new ArrayList<CustomerDataModel>();
		bds = db.selectCustomerDatas();
		for(CustomerDatas bd : bds){
			bdms.add(new CustomerDataModel(bd));
		}
		customerList.addAll(bdms);
		customerListTable.setItems(customerList);
	}
	
	// <메소드> - 가입 탭에 회원 가입 중 취소 버튼을 실행하는 메소드 
	public void handleCancleAction(ActionEvent e) {
		c_addNameFtext.setText("");
		c_addAddrFText.setText("");
		c_addTelFText.setText("");
		c_addBirthFText.setText("");
		
		popNoti("가입을 취소 하셨습니다.");
	}
	
	
	
	// <메소드> - 조회 탭에서 검색할 카테고리를 콤보박스에서 선택하고 검색창에 검색어를 입력후 검색 버튼을 눌러 실행하는 메소드
	public void handleSearchBtn(ActionEvent e) {
		String search = c_searchFText.getText();
		String sKind = searchKindComboBox.getValue();
		if(sKind == null) {
			popNoti("조건을 선택하고 검색하세요.");
			return;
		}
		customerList.removeAll(bdms);
		if(search.equals("")) {
			customerList.addAll(bdms);
		}else {
			for(CustomerDataModel cd : bdms) {
				switch(sKind) {
					case "회원번호": 
						if(String.valueOf(cd.getId()).toLowerCase().contains(search))customerList.add(cd);
						break;
					case "이름": 
						if(cd.getName().toLowerCase().contains(search.toLowerCase()))customerList.add(cd);
						break;
					case "전화번호": 
						if(cd.getTel().contains(search))customerList.add(cd);
						break;
					case "주소": 
						if(cd.getAddr().toLowerCase().contains(search.toLowerCase()))customerList.add(cd);
						break;
					case "생년월일": 
						if(cd.getBirth().equals(search.toLowerCase()))customerList.add(cd);
						break;
				}
				
				
			}
		}
		
	}
	
	
	// <메소드> - 각 창으로 넘어갈 때 애니메이션 효과를 나타내주는 메소드
	public void gotoHome(ActionEvent e) {
		try {
			StackPane root = (StackPane) c_homeBtn.getScene().getRoot();

			//애니메이션 처리 - fade out효과
			customer.setOpacity(1);
			Timeline timeline = new Timeline();
			KeyValue keyValue = new KeyValue(customer.opacityProperty(), 0);
			KeyFrame keyFrame = new KeyFrame(Duration.millis(300),
					new EventHandler<ActionEvent>(){

							@Override
							public void handle(ActionEvent arg0) {
								// TODO Auto-generated method stub
								root.getChildren().remove(customer);
							}
							
						},
					keyValue);//0.3초간 애니메이션 실행
			timeline.getKeyFrames().add(keyFrame);
			timeline.play();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// <메소드> - 팝업 창을 나타내주는 메소드
	private void popNoti(String notice) {
		Popup noti = new Popup();
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/view/Notification.fxml"));
			Label noticeText = (Label)parent.lookup("#noticeText");
			noticeText.setText(notice);
			
			noti.getContent().add(parent);
			noti.setAutoHide(true);
			noti.show(customer.getScene().getWindow());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("popNoti메소드 에러");
		}
		
	}

}


