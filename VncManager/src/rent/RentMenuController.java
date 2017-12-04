package rent;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import rsrc.Db;

public class RentMenuController implements Initializable {
	private Db db = new Db();
	private ArrayList<RentReturnDatas> rds = new ArrayList<RentReturnDatas>();
	private ArrayList<RentReturnDatasModel> rdms = new ArrayList<RentReturnDatasModel>();
	
	private ObservableList<String> productKindList = FXCollections.observableArrayList("전체", "대여중", "반납");
	private ObservableList<String> searchKindList = FXCollections.observableArrayList("상품번호", "제목", "회원번호", "이름", "전화번호");
	private ObservableList<RentReturnDatasModel> rentReturnList = FXCollections.observableArrayList();
	
	private ObservableList<String> infoTitleList = FXCollections.observableArrayList();
	private ObservableList<String> infoDataList = FXCollections.observableArrayList();
	
	@FXML private BorderPane rent;
	@FXML private Button r_homeBtn;
	@FXML private ChoiceBox<String> rent_t1_pKindChoiceBox;
	@FXML private ComboBox<String> rent_t1_searchKindComboBox;
	@FXML private TextField rent_t1_searchText;
	@FXML private Button rent_t1_searchBtn;
	@FXML private TableView<RentReturnDatasModel> rentListTable;
	@FXML private ListView<String> rent_t1_infoTitleListView;
	@FXML private ListView<String> rent_t1_infoDataListView;
	
	@FXML private ComboBox<String> rent_t3_searchKindComboBox;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		rent_t1_pKindChoiceBox.setValue("전체");
		rent_t1_pKindChoiceBox.setItems(productKindList);
		rent_t1_searchKindComboBox.setItems(searchKindList);
		
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
			
		rds = db.selectRentReturnDatas();
		for(RentReturnDatas rd : rds){
			rdms.add(new RentReturnDatasModel(rd));
		}
		rentReturnList.addAll(rdms);
		rentListTable.setItems(rentReturnList);
		
		rentListTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RentReturnDatasModel>() {

			@Override
			public void changed(ObservableValue<? extends RentReturnDatasModel> observable, RentReturnDatasModel oldValue,
					RentReturnDatasModel newValue) {
				// TODO Auto-generated method stub
				if(newValue!=null) {
					if(!infoTitleList.isEmpty()) {
						infoTitleList.removeAll("대여일", "반납예정일", "반납일", "연체일", "연체료");
						infoDataList.removeAll(oldValue.getRentDate().toString(), oldValue.getDueDate().toString(),
								oldValue.getReturnDate().toString(), String.valueOf(oldValue.getLateDays()), String.valueOf(oldValue.getOverdueFee()));
					}
					
					infoTitleList.addAll("대여일", "반납예정일", "반납일", "연체일", "연체료");
					rent_t1_infoTitleListView.setItems(infoTitleList);
					infoDataList.addAll(newValue.getRentDate().toString(), newValue.getDueDate().toString(),
							newValue.getReturnDate().toString(), String.valueOf(newValue.getLateDays()), String.valueOf(newValue.getOverdueFee()));
					rent_t1_infoDataListView.setItems(infoDataList);
				}
			}
			
		});
		
		rent_t1_pKindChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if(newValue!=null){
					rentReturnList.removeAll(rdms);
					if(newValue.equals("전체")) {
						rentReturnList.addAll(rdms);
					} else {
						for(RentReturnDatasModel bd : rdms) {
							switch(newValue) {
							case "비디오":
								if(bd.getKind().equals("V"))	rentReturnList.add(bd);
								break;
							case "만화책":
								if(bd.getKind().equals("C"))	rentReturnList.add(bd);
								break;
							}
						}
					}
				}
				
			}
		});
		
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

}
