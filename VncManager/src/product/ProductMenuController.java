
package product;

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
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import rsrc.Db;
import rsrc.Util;


public class ProductMenuController implements Initializable {
	private Db db = new Db();
	private ArrayList<ProductDatas> pds = new ArrayList<ProductDatas>();
	private ArrayList<ProductDataModel> pdms = new ArrayList<ProductDataModel>();
	
	private ObservableList<String> productKindListForModi = FXCollections.observableArrayList("비디오", "만화책");
	private ObservableList<String> productKindList = FXCollections.observableArrayList("전체", "비디오", "만화책");
	private ObservableList<String> searchKindList = FXCollections.observableArrayList("상품번호", "제목", "장르", "연령 등급", "출시일");
	private ObservableList<ProductDataModel> productList = FXCollections.observableArrayList();
	private ObservableList<String> infoTitleList = FXCollections.observableArrayList();
	private ObservableList<String> infoDataList = FXCollections.observableArrayList();
	
	//tab1 - 조회 컨트롤
	@FXML private BorderPane product;
	@FXML private Button p_homeBtn;
	@FXML private Tab p_searchTab;
	@FXML private Tab p_addTab;
	@FXML private ChoiceBox<String> pKindChoiceBox;
	@FXML private ComboBox<String> searchKindComboBox;
	@FXML private TextField searchTextField;
	@FXML private Button searchBtn;
	@FXML private TableView<ProductDataModel> productListTable;
	@FXML private Button p_addinfBtn;
	@FXML private ListView<String> infoTitleListView;
	@FXML private ListView<String> infoDataListView;
	
	
	
	//tab1 - 수정/삭제 섹션 컨트롤
	@FXML private TextField p_modiTitleTextField;
	@FXML private TextField p_modiGenreTextField;
	@FXML private TextField p_modiAgeGradeTextField;
	@FXML private TextField p_modiReleaseTextField;
	@FXML private TextField p_modiProducNumTextField;
	@FXML private TextField p_modiDirectorTextField;
	@FXML private TextField p_modiActorTextField;
	@FXML private TextField p_modiWriterTextField;
	
	@FXML private ComboBox<String> p_modiKindChoiceBox;
	@FXML private Button p_modiAddBtn;
	@FXML private Button p_modiDelBtn;
	
	
	//tab2 - 상품 추가 컨트롤
	@FXML private TextField p_titleTextField;
	@FXML private TextField p_genreTextField;
	@FXML private TextField p_ageGradeTextField;
	@FXML private TextField p_releaseTextField;
	@FXML private TextField p_producNumTextField;
	@FXML private TextField p_directorTextField;
	@FXML private TextField p_actorTextField;
	@FXML private TextField p_writerTextField;
	@FXML private ChoiceBox<String> p_kindChoiceBox;
	@FXML private Button p_addBtn;
	@FXML private Button p_cancleBtn;
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		pKindChoiceBox.setValue("전체");
		pKindChoiceBox.setItems(productKindList);
		searchKindComboBox.setItems(searchKindList);
		p_modiKindChoiceBox.setItems(productKindListForModi);
		
		TableColumn<ProductDataModel, Integer> tcP_id = (TableColumn<ProductDataModel, Integer>) productListTable.getColumns().get(0);
		TableColumn<ProductDataModel, String> tcKind = (TableColumn<ProductDataModel, String>) productListTable.getColumns().get(1);
		TableColumn<ProductDataModel, String> tcTitle = (TableColumn<ProductDataModel, String>) productListTable.getColumns().get(2);
		TableColumn<ProductDataModel, String> tcGenre = (TableColumn<ProductDataModel, String>) productListTable.getColumns().get(3);
		TableColumn<ProductDataModel, Integer> tcAgeG = (TableColumn<ProductDataModel, Integer>) productListTable.getColumns().get(4);
		TableColumn<ProductDataModel, String> tcRdate = (TableColumn<ProductDataModel, String>) productListTable.getColumns().get(5);
		TableColumn<ProductDataModel, String> tcIsrental = (TableColumn<ProductDataModel, String>) productListTable.getColumns().get(6);
		TableColumn<ProductDataModel, Integer> tcRentalCnt = (TableColumn<ProductDataModel, Integer>) productListTable.getColumns().get(7);
		tcP_id.setCellValueFactory(new PropertyValueFactory<ProductDataModel, Integer>("p_id"));
		tcKind.setCellValueFactory(new PropertyValueFactory<ProductDataModel, String>("kind"));
		tcTitle.setCellValueFactory(new PropertyValueFactory<ProductDataModel, String>("title"));
		tcGenre.setCellValueFactory(new PropertyValueFactory<ProductDataModel, String>("genre"));
		tcAgeG.setCellValueFactory(new PropertyValueFactory<ProductDataModel, Integer>("age_grade"));
		tcRdate.setCellValueFactory(new PropertyValueFactory<ProductDataModel, String>("release"));
		tcIsrental.setCellValueFactory(new PropertyValueFactory<ProductDataModel, String>("isRental"));
		tcRentalCnt.setCellValueFactory(new PropertyValueFactory<ProductDataModel, Integer>("rentalCnt"));
		
		pds = db.selectProductDatas();
		for(ProductDatas pd : pds){
			pdms.add(new ProductDataModel(pd));
		}
		productList.addAll(pdms);
		productListTable.setItems(productList);
		
		//테이블 뷰 및 선택박스 리스너 바인딩 ====시작
		pKindChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if(newValue!=null){
					productList.removeAll(pdms);
					if(newValue.equals("전체")) {
						productList.addAll(pdms);
					} else {
						for(ProductDataModel pd : pdms) {
							switch(newValue) {
							case "비디오":
								if(pd.getKind().equals("비디오"))	productList.add(pd);
								break;
							case "만화책":
								if(pd.getKind().equals("만화책"))	productList.add(pd);
								break;
							}
						}
					}
				}
				
			}
			
		});
		
		productListTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProductDataModel>() {

			@Override
			public void changed(ObservableValue<? extends ProductDataModel> arg0, ProductDataModel oldValue,
					ProductDataModel newValue) {
				// TODO Auto-generated method stub
				if(newValue!=null) {
					
					if(!infoTitleList.isEmpty()) {
						infoTitleList.removeAll("신규 구뷴", "대여료", "일 연체료");
						
						infoDataList.removeAll(oldValue.getEdition(), String.valueOf(Util.getRentCost(oldValue.getEdition(), oldValue.getKind())),
								String.valueOf(Util.getLateFee(oldValue.getEdition(), oldValue.getKind())));
						//지우기
					}
				
					infoTitleList.addAll("신규 구뷴", "대여료", "일 연체료");
					infoTitleListView.setItems(infoTitleList);
					infoDataList.addAll(newValue.getEdition(), String.valueOf(Util.getRentCost(newValue.getEdition(), newValue.getKind())),
							String.valueOf(Util.getLateFee(newValue.getEdition(), newValue.getKind())));
					
					infoDataListView.setItems(infoDataList);
					
					//수정 화면에 각 속성 불러오기
					p_modiKindChoiceBox.setValue(newValue.getKind());
					p_modiTitleTextField.setText(newValue.getTitle());
					p_modiGenreTextField.setText(newValue.getGenre());
					p_modiAgeGradeTextField.setText(String.valueOf(newValue.getAge_grade()));
					p_modiReleaseTextField.setText(newValue.getRelease().toString());
					if(newValue.getKind().equals("비디오")) {
						p_modiDirectorTextField.setEditable(true);
						p_modiActorTextField.setEditable(true);
						p_modiDirectorTextField.setText(newValue.getDirector());
						p_modiActorTextField.setText(newValue.getActor());
						p_modiWriterTextField.setText("");
						p_modiWriterTextField.setEditable(false);
					} else {
						p_modiWriterTextField.setEditable(true);
						p_modiWriterTextField.setText(newValue.getWriter());
						p_modiDirectorTextField.setText("");
						p_modiActorTextField.setText("");
						p_modiDirectorTextField.setEditable(false);
						p_modiActorTextField.setEditable(false);
					}
					p_modiProducNumTextField.setText(String.valueOf(newValue.getSupply()));
				}
			}
			
		});
		
		
	}
	
	public void resetModiTextField() {
		p_modiTitleTextField.setText("");
		p_modiGenreTextField.setText("");
		p_modiAgeGradeTextField.setText("");
		p_modiReleaseTextField.setText("");
	}
	
	public void handleSearchBtn(ActionEvent e) {
		String search = searchTextField.getText();
		String sKind = searchKindComboBox.getValue();
		if(sKind==null) {
			popNoti("조건을 선택하고 검색하세요.");
			return;
		}
		productList.removeAll(pdms);
		if(search.equals("")) {
			productList.addAll(pdms);
		} else {
			for(ProductDataModel pd : pdms) {
				switch(sKind){
				case "상품번호":
					if(String.valueOf(pd.getP_id()).equals(search))	productList.add(pd);
					break;
				case "제목":
					if(pd.getTitle().toLowerCase().contains(search.toLowerCase()))	productList.add(pd);
					break;
				case "장르":
					if(pd.getGenre().contains(search))	productList.add(pd);
					break;
				case "연령 등급":
					if(String.valueOf(pd.getAge_grade()).equals(search))	productList.add(pd);
					break;
				case "출시일":
					if(pd.getRelease().toString().contains(search.toLowerCase()))	productList.add(pd);
					break;
				}
			}
		}
		
	}
	
	
	//상품목록 새로고침 메소드
	public void refreshList() {
		productList.removeAll(pdms);
		pdms = new ArrayList<ProductDataModel>();
		pds = db.selectProductDatas();
		for(ProductDatas pd : pds){
			pdms.add(new ProductDataModel(pd));
		}
		productList.addAll(pdms);
		productListTable.setItems(productList);
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

	
	public void popNoti(String notice) {
		Util.popNoti(notice, this.getClass().getName(), (Stage)product.getScene().getWindow());
	}
	
}

