package view;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {
	public FXMLLoader loader;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//화면 전환 및 다이얼그 팝업을 위해 각 컨트롤러에서 primaryStage 객체를 받아야함
			//primaryStage객체를 loader를 이용해  컨트롤러로 전달해 주기 위해 생성자로 컨테이너를 연결해주지 않고 로더 객체를 생성
			loader = new FXMLLoader(Class.forName(this.getClass().getName()).getResource("VncManager.fxml"));
			//StackPane root = (StackPane)FXMLLoader.load(getClass().getResource("VncManager.fxml"));
			StackPane root = loader.load();
			
			VncManagerController controller = loader.getController();
			controller.setPrimaryStage(primaryStage);
			Scene scene = new Scene(root,1000,700);
			scene.getStylesheets().add(Class.forName(this.getClass().getName()).getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
