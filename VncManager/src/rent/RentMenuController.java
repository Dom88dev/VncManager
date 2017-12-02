package rent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.scene.control.TableView;
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
	
	private ObservableList<String> productKindList = FXCollections.observableArrayList("전체", "비디오", "만화책");
	private ObservableList<String> searchKindList = FXCollections.observableArrayList("상품번호", "제목", "이름", "대여일", "반납예정일", "전화번호");
	private ObservableList<RentReturnDatasModel> rentReturnList = FXCollections.observableArrayList();
	
	
	@FXML private BorderPane rent;
	@FXML private Button r_homeBtn;
	@FXML private Tab r_searchTab;
	@FXML private Tab r_rentalTab;
	@FXML private Tab r_returnTab;
	@FXML private ChoiceBox<String> pKindChoiceBox;
	@FXML private ComboBox<String> searchKindComboBox;
	@FXML private TextField searchText;
	@FXML private Button searchBtn;
	@FXML private TableView<RentReturnDatasModel> rentListTable;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		TableColumn<RentReturnDatasModel, Integer> tcP_id = (TableColumn<RentReturnDatasModel, Integer>) rentListTable.getColumns().get(0);
		TableColumn<RentReturnDatasModel, Integer> tcId = (TableColumn<RentReturnDatasModel, Integer>) rentListTable.getColumns().get(1);
		TableColumn<RentReturnDatasModel, String> tcName = (TableColumn<RentReturnDatasModel, String>) rentListTable.getColumns().get(2);
		TableColumn<RentReturnDatasModel, String> tcTitle = (TableColumn<RentReturnDatasModel, String>) rentListTable.getColumns().get(3);
		TableColumn<RentReturnDatasModel, String> tcRentDate = (TableColumn<RentReturnDatasModel, String>) rentListTable.getColumns().get(4);
		TableColumn<RentReturnDatasModel, String> tcDueDate = (TableColumn<RentReturnDatasModel, String>) rentListTable.getColumns().get(5);
		TableColumn<RentReturnDatasModel, String> tcReturnDate = (TableColumn<RentReturnDatasModel, String>) rentListTable.getColumns().get(6);
		TableColumn<RentReturnDatasModel, Integer> tcLateDays = (TableColumn<RentReturnDatasModel, Integer>) rentListTable.getColumns().get(7);
		TableColumn<RentReturnDatasModel, Integer> tcOverdueFee = (TableColumn<RentReturnDatasModel, Integer>) rentListTable.getColumns().get(8);
		
		tcP_id. setCellValueFactory(new PropertyValueFactory<RentReturnDatasModel, Integer>("P_id"));
		tcId.setCellValueFactory(new PropertyValueFactory<RentReturnDatasModel, Integer>("name"));
		tcName.setCellValueFactory(new PropertyValueFactory<RentReturnDatasModel, String>("name"));
		tcTitle.setCellValueFactory(new PropertyValueFactory<RentReturnDatasModel, String>("title"));
		tcRentDate.setCellValueFactory(new PropertyValueFactory<RentReturnDatasModel, String>("rentdate"));
		tcDueDate.setCellValueFactory(new PropertyValueFactory<RentReturnDatasModel, String>("duedate"));
		tcReturnDate.setCellValueFactory(new PropertyValueFactory<RentReturnDatasModel, String>("returndate"));
		tcLateDays.setCellValueFactory(new PropertyValueFactory<RentReturnDatasModel, Integer>("latedays"));
		tcOverdueFee.setCellValueFactory(new PropertyValueFactory<RentReturnDatasModel, Integer>("overduefee"));
			
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

}
