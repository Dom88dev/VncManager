package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import book.ReservationMenuController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 * vnc manager의 메인화면 컨트롤러 클래스<br>
 * 메인화면에 있는 네개의 버튼의 이벤트 처리를 한다.
 * @author Dominic
 *
 */
public class VncManagerController implements Initializable {
	private Stage primaryStage;
	
	@FXML private Button menu1;
	@FXML private Button menu2;
	@FXML private Button menu3;
	@FXML private Button menu4;
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 메인화면에서 첫번째 회원관리 버튼을 눌렀을 경우 실행되는 이벤트 처리 메소드
	 * @param e
	 */
	public void openCustomerMenu(ActionEvent e) {
		try {
			Parent cMenu = FXMLLoader.load(Class.forName(this.getClass().getName()).getResource("CustomerMenu.fxml"));
			StackPane root = (StackPane) menu1.getScene().getRoot();
			root.getChildren().add(cMenu);
			
			//애니메이션 처리 - fade 효과
			cMenu.setOpacity(0);
			Timeline timeline = new Timeline();
			KeyValue keyValue = new KeyValue(cMenu.opacityProperty(), 1);
			KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue);//0.3초간 애니메이션 실행
			timeline.getKeyFrames().add(keyFrame);
			timeline.play();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * 메인화면에서 두번째 상품관리 버튼을 눌렀을 경우 실행되는 이벤트 처리 메소드
	 * @param e
	 */
	public void openProductMenu(ActionEvent e) {
		try {
			Parent pMenu = FXMLLoader.load(Class.forName(this.getClass().getName()).getResource("ProductMenu.fxml"));
			StackPane root = (StackPane) menu1.getScene().getRoot();
			root.getChildren().add(pMenu);
			
			//애니메이션 처리 - fade 효과
			pMenu.setOpacity(0);
			Timeline timeline = new Timeline();
			KeyValue keyValue = new KeyValue(pMenu.opacityProperty(), 1);
			KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue);//0.3초간 애니메이션 실행
			timeline.getKeyFrames().add(keyFrame);
			timeline.play();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * 메인화면에서 세번째 대여관리 버튼을 눌렀을 경우 실행되는 이벤트 처리 메소드
	 * @param e
	 */
	public void openRentReturnMenu(ActionEvent e) {
		try {
			Parent rMenu = FXMLLoader.load(Class.forName(this.getClass().getName()).getResource("RentMenu.fxml"));
			StackPane root = (StackPane) menu1.getScene().getRoot();
			root.getChildren().add(rMenu);
			
			//애니메이션 처리 - fade 효과
			rMenu.setOpacity(0);
			Timeline timeline = new Timeline();
			KeyValue keyValue = new KeyValue(rMenu.opacityProperty(), 1);
			KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue);//0.3초간 애니메이션 실행
			timeline.getKeyFrames().add(keyFrame);
			timeline.play();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * 메인화면에서 첫번째 예약관리 버튼을 눌렀을 경우 실행되는 이벤트 처리 메소드
	 * @param e
	 */
	public void openReservationMenu(ActionEvent e) {
		try {
			Parent rMenu = FXMLLoader.load(Class.forName(this.getClass().getName()).getResource("ReservationMenu.fxml"));
			StackPane root = (StackPane) menu4.getScene().getRoot();
			root.getChildren().add(rMenu);
			
			//애니메이션 처리 - fade 효과
			rMenu.setOpacity(0);
			Timeline timeline = new Timeline();
			KeyValue keyValue = new KeyValue(rMenu.opacityProperty(), 1);
			KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue);//0.3초간 애니메이션 실행
			timeline.getKeyFrames().add(keyFrame);
			timeline.play();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void popNoti(String notice) {
		Popup noti = new Popup();
		
		try {
			Parent parent = FXMLLoader.load(Class.forName(this.getClass().getName()).getResource("Notification.fxml"));
			Label noticeText = (Label)parent.lookup("#noticeText");
			noticeText.setText(notice);
			
			noti.getContent().add(parent);
			noti.setAutoHide(true);
			noti.show(primaryStage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("popNoti메소드 에러");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
