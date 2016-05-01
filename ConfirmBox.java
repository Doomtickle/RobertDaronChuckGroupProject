package robertdaronchuckgroupproject;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {
	
	//confirmation box to ensure the user wants to do what they said they want to do
	
	static boolean answer;
	
	public static boolean confirm(String title, String message)
	{
		Stage window = new Stage();
		
		StackPane layout = new StackPane();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		window.setMinHeight(250);
		
		
		Label label = new Label();
		
		label.setTranslateY(-50);
		label.setTextFill(Color.WHITESMOKE);
		label.setText(message);
		
		
		Button yesButton = new Button("Yes");
		Button noButton = new Button("No");
		
		yesButton.setTranslateX(50);
		yesButton.setTranslateY(50);
		yesButton.setOnAction(e -> {
			answer = true;
			window.close();
		});
		noButton.setTranslateX(-50);
		noButton.setTranslateY(50);
		noButton.setAlignment(Pos.BOTTOM_CENTER);
		noButton.setOnAction(e -> {
			answer = false;
			window.close();
		});
		
		
		
		layout.setStyle("-fx-background-color: dodgerblue");
		layout.getChildren().addAll(label, yesButton, noButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();	//show and wait blocks any user interaction until alert box is acknowledged
		
		
		return answer;
		
	
	}

}
