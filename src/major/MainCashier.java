package major;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainCashier extends Application{
	
//	public static MainCashier mainCashierTran =new MainCashier();

	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		FXMLLoader root = new FXMLLoader(getClass().getResource("/ui/template/cashier_main.fxml"));
		
	//	LoginController logincontroller = root.getController();
		 
		
		Scene scene = new Scene(((Parent) root.load()),850,500);
		primaryStage.setScene(scene);
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		primaryStage.show();
		
	}
	
	public static void main(String args[]) {
		Application.launch(args);
	}
}
