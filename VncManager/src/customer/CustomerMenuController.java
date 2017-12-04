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
	private Db db = new Db();
	private ArrayList<CustomerDatas> bds = new ArrayList<CustomerDatas>();
	private ArrayList<CustomerDataModel> bdms = new ArrayList<CustomerDataModel>();
	private ArrayList<CustomerDatas> join = new ArrayList<CustomerDatas>();
	
	private ObservableList<String> productKindList = FXCollections.observableArrayList("전체", "비디오", "만화책");
	private ObservableList<String> searchKindList = FXCollections.observableArrayList("회원번호", "이름", "전화번호", "주소");
	private ObservableList<String> familyKindList = FXCollections.observableArrayList("전체", "가족 유", "가족 무");
	
	private ObservableList<CustomerDataModel> customerList = FXCollections.observableArrayList();
	
	private ObservableList<String> infoTitleList = FXCollections.observableArrayList();
	private ObservableList<String> infoDataList = FXCollections.observableArrayList();
	
	@FXML private BorderPane customer;
	@FXML private ChoiceBox<String> fKindChoiceBox;
	@FXML private ComboBox<String> searchKindComboBox;
	@FXML private Tab c_searchTab;
	@FXML private Tab c_joinTab;
	@FXML private Tab c_modifyTab;
	@FXML private Button c_homeBtn;
	@FXML private TableView<CustomerDataModel> customerListTable;
	@FXML private Button c_searchBtn;
	@FXML private TextField c_searchFText;
	@FXML private Button c_addInfoBtn;
	@FXML private ListView c_addInfoListView1;
	@FXML private ListView c_addInfoListView2;
	
	//추가 탭 정보
	@FXML private TextField c_addNameFtext;
	@FXML private TextField c_addAddrFText;
	@FXML private TextField c_addTelFText;
	@FXML private TextField c_addBirthFText;
	@FXML private Button c_JoinBtn;
	@FXML private Button c_JoinCancleBtn;
	
	//수정 및 탈퇴 정보
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
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		fKindChoiceBox.setValue("전체");
		fKindChoiceBox.setItems(familyKindList);
		searchKindComboBox.setItems(searchKindList);
		
		TableColumn<CustomerDataModel, Integer> tcId = (TableColumn<CustomerDataModel, Integer>)customerListTable.getColumns().get(0);
		TableColumn<CustomerDataModel, String> tcName = (TableColumn<CustomerDataModel, String>)customerListTable.getColumns().get(1);
		TableColumn<CustomerDataModel, String> tcAddr = (TableColumn<CustomerDataModel, String>)customerListTable.getColumns().get(2);
		TableColumn<CustomerDataModel, String> tcTel = (TableColumn<CustomerDataModel, String>)customerListTable.getColumns().get(3);
		TableColumn<CustomerDataModel, String> tcBirth = (TableColumn<CustomerDataModel, String>)customerListTable.getColumns().get(4);
		TableColumn<CustomerDataModel, Integer> tcAge = (TableColumn<CustomerDataModel, Integer>)customerListTable.getColumns().get(5);
		TableColumn<CustomerDataModel, Integer> tcCountFamily = (TableColumn<CustomerDataModel, Integer>)customerListTable.getColumns().get(6);
		
		
		tcId.setCellValueFactory(new PropertyValueFactory<CustomerDataModel, Integer>("id"));
		tcName.setCellValueFactory(new PropertyValueFactory<CustomerDataModel, String>("name"));
		tcAddr.setCellValueFactory(new PropertyValueFactory<CustomerDataModel, String>("addr"));
		tcTel.setCellValueFactory(new PropertyValueFactory<CustomerDataModel, String>("tel"));
		tcBirth.setCellValueFactory(new PropertyValueFactory<CustomerDataModel, String>("birth"));
		tcAge.setCellValueFactory(new PropertyValueFactory<CustomerDataModel, Integer>("age"));
		tcCountFamily.setCellValueFactory(new PropertyValueFactory<CustomerDataModel, Integer>("countFamily"));
		
		
		bds = db.selectCustomerDatas();
		for(CustomerDatas cd: bds) {
			bdms.add(new CustomerDataModel(cd));
		}
		customerList.addAll(bdms);
		customerListTable.setItems(customerList);
		
		customerListTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerDataModel>() {

			@Override
			public void changed(ObservableValue<? extends CustomerDataModel> observable, CustomerDataModel oldValue,
					CustomerDataModel newValue) {
				// TODO Auto-generated method stub
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
		
		Label label = new Label("검색한 결과가 없습니다.");
		customerListTable.setPlaceholder(label);
		
		
		fKindChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				// TODO Auto-generated method stub
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
	
	
	
	public void handleJoinAction(ActionEvent e) {
		String jName = c_addNameFtext.getText();
		String jAddr = c_addAddrFText.getText();
		String jTel = c_addTelFText.getText();
		String jBirth = c_addBirthFText.getText();
		
		int result = db.usp_register(jName, jTel, jAddr, jBirth);
		
		//TODO 처리된 result값에 따라 다이얼로그로 결과 처리

	}
	
	
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


