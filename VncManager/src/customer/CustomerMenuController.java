package customer;

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
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import rsrc.Db;

public class CustomerMenuController implements Initializable{
	private Db db = new Db();
	private ArrayList<CustomerDatas> bds = new ArrayList<CustomerDatas>();

	private ObservableList<String> productKindList = FXCollections.observableArrayList("전체", "비디오", "만화책");
	private ObservableList<String> searchKindList = FXCollections.observableArrayList("회원번호", "이름", "전화번호", "주소");
	private ObservableList<String> familyKindList = FXCollections.observableArrayList("전체", "가족 유", "가족 무");
	private ObservableList<CustomerDataModel> customerList = FXCollections.observableArrayList();
	
	@FXML private BorderPane customer;
	@FXML private ChoiceBox<String> fKindChoiceBox;
	@FXML private ComboBox<String> searchKindComboBox;
	@FXML private ChoiceBox<String> pKindChoiceBox;
	@FXML private Tab c_searchTab;
	@FXML private Tab c_joinTab;
	@FXML private Tab c_modifyTab;
	@FXML private Button c_homeBtn;
	@FXML private TableView<CustomerDataModel> customerListTable;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		pKindChoiceBox.setValue("전체");
		pKindChoiceBox.setItems(productKindList);
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
		for(CustomerDatas bd: bds) {
			customerList.add(new CustomerDataModel(bd));
		}
		customerListTable.setItems(customerList);
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

}
