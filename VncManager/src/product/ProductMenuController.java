package product;

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


public class ProductMenuController implements Initializable {
	private Db db = new Db();
	private ArrayList<ProductDatas> pds = new ArrayList<ProductDatas>();
	
	private ObservableList<String> productKindList = FXCollections.observableArrayList("전체", "비디오", "만화책");
	private ObservableList<String> searchKindList = FXCollections.observableArrayList("상품번호", "제목", "장르", "연령 등급", "출시일");
	private ObservableList<ProductDataModel> productList = FXCollections.observableArrayList();
	
	@FXML private BorderPane product;
	@FXML private Button p_homeBtn;
	@FXML private Tab p_searchTab;
	@FXML private Tab p_addTap;
	@FXML private ChoiceBox<String> pKindChoiceBox;
	@FXML private ComboBox<String> searchKindComboBox;
	@FXML private TextField searchText;
	@FXML private Button searchBtn;
	@FXML private TableView<ProductDataModel> rsvListTable;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		pKindChoiceBox.setValue("전체");
		pKindChoiceBox.setItems(productKindList);
		searchKindComboBox.setItems(searchKindList);
		
		TableColumn<ProductDataModel, Integer> tcP_id = (TableColumn<ProductDataModel, Integer>) rsvListTable.getColumns().get(0);
		TableColumn<ProductDataModel, String> tcKind = (TableColumn<ProductDataModel, String>) rsvListTable.getColumns().get(1);
		TableColumn<ProductDataModel, String> tcTitle = (TableColumn<ProductDataModel, String>) rsvListTable.getColumns().get(2);
		TableColumn<ProductDataModel, String> tcGenre = (TableColumn<ProductDataModel, String>) rsvListTable.getColumns().get(3);
		TableColumn<ProductDataModel, String> tcRdate = (TableColumn<ProductDataModel, String>) rsvListTable.getColumns().get(4);
		TableColumn<ProductDataModel, Integer> tcIsrental = (TableColumn<ProductDataModel, Integer>) rsvListTable.getColumns().get(5);
		TableColumn<ProductDataModel, Integer> tcRentalCnt = (TableColumn<ProductDataModel, Integer>) rsvListTable.getColumns().get(6);
		tcP_id.setCellValueFactory(new PropertyValueFactory<ProductDataModel, Integer>("p_id"));
		tcKind.setCellValueFactory(new PropertyValueFactory<ProductDataModel, String>("kind"));
		tcTitle.setCellValueFactory(new PropertyValueFactory<ProductDataModel, String>("title"));
		tcGenre.setCellValueFactory(new PropertyValueFactory<ProductDataModel, String>("genre"));
		tcRdate.setCellValueFactory(new PropertyValueFactory<ProductDataModel, String>("rdate"));
		tcIsrental.setCellValueFactory(new PropertyValueFactory<ProductDataModel, Integer>("isrental"));
		tcRentalCnt.setCellValueFactory(new PropertyValueFactory<ProductDataModel, Integer>("rentalCnt"));
		
		//pds = pd.selectProductDatas();
		for(ProductDatas pd : pds){
			productList.add(new ProductDataModel(pd));
		}
		rsvListTable.setItems(productList);
		
	}
	public void handleSearchBtn(ActionEvent e) {
		String search = searchText.getText();
		
	}
	public void gotoHome(ActionEvent e) {
		try {
			StackPane root = (StackPane) p_homeBtn.getScene().getRoot();

			//애니메이션 처리 - fade out효과
			product.setOpacity(1);
			Timeline timeline = new Timeline();
			KeyValue keyValue = new KeyValue(product.opacityProperty(), 0);
			KeyFrame keyFrame = new KeyFrame(Duration.millis(300),
					new EventHandler<ActionEvent>(){

							@Override
							public void handle(ActionEvent arg0) {
								// TODO Auto-generated method stub
								root.getChildren().remove(product);
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
