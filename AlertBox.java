package robertdaronchuckgroupproject;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
	
	//making an alert box to assist with input validation
	
	public static void alert(String title, String message)
	{
		Stage window = new Stage();
		
		//block user interaction with the program until they make the alert go away
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		window.setMinHeight(250);
		
		//tell the user what they did wrong
		Label label = new Label();
		label.setTranslateY(-50);
		label.setTextFill(Color.WHITESMOKE);
		label.setText(message);
		
		Button closeButton = new Button("Got it!");
		closeButton.setOnAction(e -> window.close());
		
		VBox layout = new VBox(20);
		layout.setStyle("-fx-background-color: dimgray");
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout, 400, 300);
		window.setScene(scene);
		window.showAndWait();	//show and wait blocks any user interaction until alert box is acknowledged
		
		
		
		
	
	}
	

}
