package book;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import javafx.util.Duration;
import rsrc.Db;

public class ReservationMenuController implements Initializable {
	private Db db = new Db();
	private ArrayList<BookDatas> bds = new ArrayList<BookDatas>();
	
	//choiceBox의 선택항목 생성
	private ObservableList<String> productKindList = FXCollections.observableArrayList("전체", "비디오", "만화책");
	private ObservableList<String> searchKindList = FXCollections.observableArrayList("회원번호", "이름", "전화번호", "상품번호", "제목");
	private ObservableList<BookDataModel> bookingList = FXCollections.observableArrayList();
	
	@FXML private BorderPane reservation;
	@FXML private Button b_homeBtn;
	@FXML private Tab b_searchTab;
	@FXML private Tab b_manageTap;
	@FXML private ChoiceBox<String> pKindChoiceBox;
	@FXML private ComboBox<String> searchKindComboBox;
	@FXML private TextField searchText;
	@FXML private Button searchBtn;
	@FXML private TableView<BookDataModel> rsvListTable;
	
	
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
			bookingList.add(new BookDataModel(bd));
		}
		rsvListTable.setItems(bookingList);
		
	}

	public void handleSearchBtn(ActionEvent e) {
		String search = searchText.getText();
		
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
	
}
