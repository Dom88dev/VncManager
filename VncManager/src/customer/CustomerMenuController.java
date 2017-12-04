package customer;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import book.BookDataModel;
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
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.util.Duration;
import rent.RentReturnDatasModel;
import rsrc.Db;


public class CustomerMenuController implements Initializable{
	
	// DB 연결해주는 Db 클래스의 db메소드 호출
	private Db db = new Db();
	
	// CustomerDates 데이터타입인 ArrayList 컬렉션 메소드
	private ArrayList<CustomerDatas> bds = new ArrayList<CustomerDatas>();
	
	// CustomerDataModel 데이터타입인 ArriList 컬렉션 메소드
	private ArrayList<CustomerDataModel> bdms = new ArrayList<CustomerDataModel>();
	private ArrayList<CustomerDataModel> compare = new ArrayList<CustomerDataModel>();
	
	private int customeringNo =0, customeringNo2=0;
	
	// 조회 탭의 콤보박스의 카테고리의 값을 저장해주는 FX 컬렉션 메소드
	private ObservableList<String> searchKindList = FXCollections.observableArrayList("회원번호", "이름", "전화번호", "주소");
	
	// 조회 탭의 초이스 박스의 카테고리의 값을 저장해주는 FX 컬렉션 메소드
	private ObservableList<String> familyKindList = FXCollections.observableArrayList("전체", "가족 유", "가족 무");
	
	// 조회 탭의 DB에서 불러운 값을 CUstomerDataModel 데이터형식에 따라서 저장해주는 FX 컬렉션 메소드
	private ObservableList<CustomerDataModel> customerList = FXCollections.observableArrayList();
	
	// 조회 탭의 오른쪽 추가 정보 란에서 추가 정보를 나타내는 리스트 뷰의 값을 담는 ArratList 메소드
	private ObservableList<String> infoTitleList = FXCollections.observableArrayList();
	private ObservableList<String> infoDataList = FXCollections.observableArrayList();
	
	@FXML private BorderPane customer;                                         // 전체 판넬
	@FXML private ChoiceBox<String> fKindChoiceBox;                        // 가족 유, 무 초이스 박스
	@FXML private ComboBox<String> searchKindComboBox;                // 검색 카테고리 콤보박스
	@FXML private Tab c_searchTab;                                               // 조회 탭
	@FXML private Tab c_joinTab;                                                   // 가입 탭
	@FXML private Tab c_modifyTab;                                               // 수정/탈퇴 탭 
	@FXML private Button c_homeBtn;                                             // <버튼> - 회원관리 창에서 처음 창으로 돌아가는 버튼
	@FXML private TableView<CustomerDataModel> customerListTable;  // 조회탭의 회원정보를 나타내는 테이블 뷰.
	@FXML private Button c_searchBtn;                                           // 조회 탭의 검색 버튼
	@FXML private TextField c_searchFText;                                     // 조회 탭의 검색 텍스트 필드
	@FXML private Button c_addInfoBtn;                                         // <버튼> -조회 탭의 추가 수정 정보 버튼
	@FXML private ListView c_addInfoListView1;                                // 조회 탭의 추가 정보 란에서 테이블에서 선택된 레이블의 정보를 출력하는 리스트 뷰
	@FXML private ListView c_addInfoListView2;                                // 조회 탭의 추가 정보 란에서 테이블에서 선택된 레이블의 정보를 출력해주는 리스트 뷰
	
	//추가 탭 정보
	@FXML private TextField c_addNameFtext;                                  // 회원가입 탭에서 회원정보 중 이름을 추가해주는 텍스트 필드
	@FXML private TextField c_addAddrFText;                                   // 회원가입 탭에서 회원정보 중 주소를 추가해주는 텍스트 필드
	@FXML private TextField c_addTelFText;                                     // 회원가입 탭에서 회원정보 중 전화번호를 추가해주는 텍스트 필드
	@FXML private TextField c_addBirthFText;                                   // 회원가입 탭에서 회원정보 중 생년월일을 추가해주는 텍스트 필드
	@FXML private Button c_JoinBtn;                                              // <버튼> - 회원가입 탭에 회원 가입의 가입을 실행해주는 '가입' 버튼
	@FXML private Button c_JoinCancleBtn;                                     // <버튼> - 회원가입 탭에 회원 가입 중 취소를 실행해주는 '취소'버튼
	
	//수정 및 탈퇴 정보
	@FXML private Label c_modifyId;                                              // <라벨> - 수정/탈퇴 탭에 회원정보의 아이디를 알려주는 라벨
	@FXML private TextField c_modifyNameFtext;                              // 수정/탈퇴 탭에서 회원정보의 이름을 수정해주는 텍스트 필드
	@FXML private TextField c_modifyAddrFtext;                               // 수정/탈퇴 탭에서 회원정보의 주소를 수정해주는 텍스트 필드
	@FXML private TextField c_modifyTelFtext;                                 // 수정/탈퇴 탭에서 회원정보의 전화번호를 수정해주는 텍스트 필드
	@FXML private TextField c_modifyBirthFtext;                               // 수정/탈퇴 탭에서 회원정보의 생년월일을 수정해주는 텍스트 필드
	@FXML private TextField c_modifyPwFtext;                                 // 수정/탈퇴 탭에서 회원정보의 비밀번호를 수정해주는 텍스트 필드
	@FXML private TextField c_modifyFnameFText;                            // 수정탈퇴 탭의 가족 이름를 추가하거나 수정하는 텍스트 필드
	@FXML private TextField c_modifyFrelationFText;                         // 수정/탈되 탭의 가족 관계를 추가하거나 수정하는 텍스트 필드
	@FXML private Button c_modifyBtn;                                          // <버튼> - 수정/탈퇴탈퇴 탭에 회원정보 수정을 실행해주는 '수정' 버튼
	@FXML private Button c_modifyCancleBtn;                                 // <버튼> - 수정/탈퇴 탭에 회원정보 수정하는 중 취소하는 '취소' 버튼
	@FXML private Button c_modifyAddBtn;                                      // 수정/탈퇴 탭에 가족 정보 추가하는 '추가' 버튼
	@FXML private Button c_modifyFixBtn;                                        // 수정/탈퇴 탭에 가족 정보 수정하는 '수정' 버튼  
	@FXML private Button c_modifyDelBtn;                                      //  수정/탈퇴 탭에 가족 정보 삭제하는 '삭제' 버튼
	@FXML private Button c_customerDropBtn;                                //  수정/탈퇴 탭에 회원 수 중 최소를 실행해주는 '취소'버튼
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		// 가족 유, 무 초이스 박스의 디폴트 값을 저장하는 메소드
		fKindChoiceBox.setValue("전체");
		fKindChoiceBox.setItems(familyKindList);
		
		// 조회 탭의 콤보 박스에서 쓸 값이 담겨있는 searchKindList 켈렉션 메소드를 콤보박스에 넣은 메소드 
		searchKindComboBox.setItems(searchKindList);
		
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
	// <메소드> - 가입 탭에 회원 가입 중 취소 버튼을 실행하는 메소드 
	public void handleCancleAction(ActionEvent e) {
		String jName = c_addNameFtext.getText();
		String jAddr = c_addAddrFText.getText();
		String jTel = c_addTelFText.getText();
		String jBirth = c_addBirthFText.getText();
		
		jName = null;
		jAddr = null;
		jTel = null;
		jBirth = null;
		
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


