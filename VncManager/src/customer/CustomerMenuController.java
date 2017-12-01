package customer;

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

public class CustomerMenuController implements Initializable{
	@FXML private BorderPane customer;
	@FXML private ToggleButton c_searchToggle;
	@FXML private ToggleButton c_manageToggle;
	@FXML private Button c_homeBtn;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
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
