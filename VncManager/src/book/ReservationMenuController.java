package book;

import java.io.IOException;
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

public class ReservationMenuController implements Initializable {
	private Db db = new Db();
	private ArrayList<BookDatas> bds = new ArrayList<BookDatas>();
	private ArrayList<BookDataModel> bdms = new ArrayList<BookDataModel>();
	
	//choiceBox의 선택항목 생성
	private ObservableList<String> productKindList = FXCollections.observableArrayList("전체", "비디오", "만화책");
	private ObservableList<String> searchKindList = FXCollections.observableArrayList("회원번호", "이름", "전화번호", "상품번호", "제목");
	private ObservableList<String> cSearchKindList = FXCollections.observableArrayList("회원번호", "이름", "전화번호");
	private ObservableList<String> pSearchKindList = FXCollections.observableArrayList("상품번호", "제목", "장르", "출시일");
	//tableView의 데이터모델 리스트 생성
	private ObservableList<BookDataModel> bookingList = FXCollections.observableArrayList();
//	private ObservableList<CustomerDataModel> customerList = FXCollections.observableArrayList();
//	private ObservableList<ProductDataModel> productList = FXCollections.observableArrayList();
	
	@FXML private BorderPane reservation;
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
	@FXML private ChoiceBox<String> customerSearchKind;
	@FXML private TextField customerSearchText;
	@FXML private Button customerSearchBtn;
	@FXML private TableView<String> customerSearchTable;
	@FXML private ChoiceBox<String> productSearchKind;
	@FXML private TextField productSearchText;
	@FXML private Button productSearchBtn;
	@FXML private ChoiceBox<String> productKind;
	@FXML private TableView<String> productSearchTable;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		pKindChoiceBox.setValue("전체");
		pKindChoiceBox.setItems(productKindList);
		searchKindComboBox.setItems(searchKindList);
		
		//table view setting
		//table column setting
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
		
		bds = db.selectBookDatas();
		for(BookDatas bd : bds){
			bdms.add(new BookDataModel(bd));
		}
		bookingList.addAll(bdms);
		rsvListTable.setItems(bookingList);
		
		rsvListTable.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent scrollEvent) {
            	
            }
		});
		
		Label label = new Label("검색한 결과가 없습니다.");
		rsvListTable.setPlaceholder(label);
		customerSearchTable.setPlaceholder(label);
		productSearchTable.setPlaceholder(label);
		
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
								if(bd.getKind().equals("V"))	bookingList.add(bd);
								break;
							case "만화책":
								if(bd.getKind().equals("C"))	bookingList.add(bd);
								break;
							}
						}
					}
				}
				
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
		Popup noti = new Popup();
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/view/Notification.fxml"));
			Label noticeText = (Label)parent.lookup("#noticeText");
			noticeText.setText(notice);
			
			noti.getContent().add(parent);
			noti.setAutoHide(true);
			noti.show(reservation.getScene().getWindow());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("popNoti메소드 에러");
		}
	}

	
}
