
package product;

import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.TabPane;
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
	private int selectedPid = 0;
	
	private ObservableList<String> productKindListForModi = FXCollections.observableArrayList("비디오", "만화책");
	private ObservableList<String> productKindList = FXCollections.observableArrayList("전체", "비디오", "만화책");
	private ObservableList<String> searchKindList = FXCollections.observableArrayList("상품번호", "제목", "장르", "연령 등급", "출시일");
	private ObservableList<ProductDataModel> productList = FXCollections.observableArrayList();
	private ObservableList<String> infoTitleList = FXCollections.observableArrayList();
	private ObservableList<String> infoDataList = FXCollections.observableArrayList();
	
	//tab1 - 조회 컨트롤
	@FXML private TabPane tab;
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
	@FXML private ComboBox<String> p_kindChoiceBox;
	@FXML private Button p_addBtn;
	@FXML private Button p_cancleBtn;
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		pKindChoiceBox.setValue("전체");
		pKindChoiceBox.setItems(productKindList);
		searchKindComboBox.setItems(searchKindList);
		p_modiKindChoiceBox.setItems(productKindListForModi);
		p_kindChoiceBox.setItems(productKindListForModi);
		
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
					p_modiTitleTextField.setEditable(true);
					p_modiGenreTextField.setEditable(true);
					p_modiAgeGradeTextField.setEditable(true);
					p_modiReleaseTextField.setEditable(true);
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
					
					p_modiAddBtn.setDisable(false);
					p_modiDelBtn.setDisable(false);
					selectedPid = newValue.getP_id();
				} else {
					resetModiTextField();
				}
			}
			
		});
		
		p_kindChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newVal) {
				// TODO Auto-generated method stub
				if(newVal!=null) {
					if(newVal.equals("비디오")) {
						p_directorTextField.setEditable(true);
						p_actorTextField.setEditable(true);
						p_writerTextField.setText("");
						p_writerTextField.setEditable(false);
					} else {
						p_directorTextField.setText("");
						p_actorTextField.setText("");
						p_directorTextField.setEditable(false);
						p_actorTextField.setEditable(false);
						p_writerTextField.setEditable(true);
					}
				}
			}
			
		});
		
		
		//tab change Listener 구현
		tab.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

			@Override
			public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab newTab) {
				// TODO Auto-generated method stub
				if(newTab.getId().equals("searchTab"))	refreshList();
			}
			
		});

	}
	
	public void resetModiTextField() {
		p_modiTitleTextField.setText("");
		p_modiGenreTextField.setText("");
		p_modiAgeGradeTextField.setText("");
		p_modiReleaseTextField.setText("");
		p_modiDirectorTextField.setText("");
		p_modiActorTextField.setText("");
		p_modiWriterTextField.setText("");
		p_modiProducNumTextField.setText("");
		p_modiTitleTextField.setEditable(false);
		p_modiGenreTextField.setEditable(false);
		p_modiAgeGradeTextField.setEditable(false);
		p_modiReleaseTextField.setEditable(false);
		p_modiDirectorTextField.setEditable(false);
		p_modiActorTextField.setEditable(false);
		p_modiWriterTextField.setEditable(false);
		p_modiProducNumTextField.setEditable(false);
		p_modiAddBtn.setDisable(true);
		p_modiDelBtn.setDisable(true);
		selectedPid = 0;
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
	
	//상품정보 수정
	public void modifyProduct(ActionEvent e) {
		ProductDatas p = new ProductDatas();
		p.setKind(p_modiKindChoiceBox.getValue().equals("비디오")?"V":"C");
		p.setTitle(p_modiTitleTextField.getText());
		p.setGenre(p_modiGenreTextField.getText());
		p.setAge_grade(Integer.valueOf(p_modiAgeGradeTextField.getText()));
		p.setRelease(Date.valueOf(p_modiReleaseTextField.getText()));
		p.setDirector(p_modiDirectorTextField.getText());
		p.setActor(p_modiActorTextField.getText());
		p.setWriter(p_modiWriterTextField.getText());
		p.setSupply(Integer.valueOf(p_modiProducNumTextField.getText()));
		p.setP_id(selectedPid);
		
		int result = db.updateProduct(p);
		if(result>0) {
			popNoti("상품정보를 변경했습니다.");
			refreshList();
		} else {
			popNoti("상품정보 변경하지 못했습니다.");
		}
	}
	
	//상품 삭제
	public void deleteProduct(ActionEvent e) {
		int result = db.deleteProduct(selectedPid);
		if(result>0) {
			popNoti("상품정보를 삭제했습니다.");
			refreshList();
		} else {
			popNoti("상품정보 삭제하지 못했습니다.");
		}
	}

	
	//상품 추가
	public void addProduct(ActionEvent e) {
		ProductDatas p = new ProductDatas();
		p.setTitle(p_titleTextField.getText());
		p.setGenre(p_genreTextField.getText());
		p.setAge_grade(Integer.valueOf(p_ageGradeTextField.getText()));
		p.setRelease(Date.valueOf(p_releaseTextField.getText()));
		p.setDirector(p_directorTextField.getText());
		p.setActor(p_actorTextField.getText());
		p.setWriter(p_writerTextField.getText());
		p.setSupply(Integer.valueOf(p_producNumTextField.getText()));
		int result = 0;
		if(p_kindChoiceBox.getValue().equals("비디오")) {
			result = db.usp_addv(p);
		} else if(p_kindChoiceBox.getValue().equals("만화책")) {
			result = db.usp_addc(p);
		} else {
			popNoti("상품종류를 선택해주세요.");
			return;
		}
		
		if(result==1){
			popNoti("상품이 추가되었습니다.");
			cancelAddProduct(e);
		} else {
			popNoti("상품을 추가하는중 문제가 발생했습니다.");
		}
	}
	
	//상품추가 취소
	public void cancelAddProduct(ActionEvent e) {
		p_titleTextField.setText("");
		p_genreTextField.setText("");
		p_ageGradeTextField.setText("");
		p_releaseTextField.setText("");
		p_directorTextField.setText("");
		p_actorTextField.setText("");
		p_writerTextField.setText("");
		p_producNumTextField.setText("");
		p_kindChoiceBox.setValue("선택");
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

