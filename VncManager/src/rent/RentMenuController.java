package rent;

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
	private ObservableList<String> searchKindList = FXCollections.observableArrayList("상품번호", "제목", "이름", "대여일", "반납예정일", "전화번호");
	private ObservableList<RentReturnDatasModel> rentReturnList = FXCollections.observableArrayList();
	
	private ObservableList<String> infoTitleList = FXCollections.observableArrayList();
	private ObservableList<String> infoDataList = FXCollections.observableArrayList();
	
	@FXML private BorderPane rent;
	@FXML private Button r_homeBtn;
	@FXML private ChoiceBox<String> pKindChoiceBox;
	@FXML private ComboBox<String> searchKindComboBox;
	@FXML private TextField searchText;
	@FXML private Button searchBtn;
	@FXML private TableView<RentReturnDatasModel> rentListTable;
	@FXML private ListView<String> infoTitleListView;
	@FXML private ListView<String> infoDataListView;
	@FXML private Button r_rentBtn;
	@FXML private Button r_cancleBtn;
	@FXML private Label r_addinfleBtn;
	
	@FXML private ComboBox<String> re_addinfChoiceBox;
	@FXML private TextField re_searchText;
	@FXML private Button re_searchBtn;
	@FXML private ChoiceBox<String> re_pKindChoiceBox;
	@FXML private TableView<RentReturnDatasModel> re_rentListTable;
	@FXML private TextArea re_Texta;
	@FXML private Button re_returnhBtn;
	@FXML private Button re_cancleBtn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		pKindChoiceBox.setValue("전체");
		pKindChoiceBox.setItems(productKindList);
		searchKindComboBox.setItems(searchKindList);
		
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
					infoTitleListView.setItems(infoTitleList);
					infoDataList.addAll(newValue.getRentDate().toString(), newValue.getDueDate().toString(),
							newValue.getReturnDate().toString(), String.valueOf(newValue.getLateDays()), String.valueOf(newValue.getOverdueFee()));
					infoDataListView.setItems(infoDataList);
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
