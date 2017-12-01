package book;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class ReservationMenuController implements Initializable {
	
	@FXML private BorderPane reservation;
	@FXML private Button b_homeBtn;
	@FXML private ToggleButton b_searchToggle;
	@FXML private ToggleButton b_manageToggle;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
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
	
	public void changeToSearch(ActionEvent e) {
		
	}
	
	public void changeToManage(ActionEvent e) {
		
	}
}
