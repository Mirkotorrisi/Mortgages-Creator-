package financeGUI;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
	public static void display(String title, String message) {
		//Simple alert box
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(460);
		Label label = new Label();
		label.setText(message);
		Button closebutton = new Button("Close");
		closebutton.setOnAction(e -> window.close());
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closebutton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
	public static void displayScrollbar(String title, String info) {
		//Alert Box with scrollbar used for mortgage view
		
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(460);
		Label label = new Label();
		label.setText(info);
		Button closebutton = new Button("Close");
		closebutton.setOnAction(e -> window.close());
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closebutton);
		layout.setAlignment(Pos.CENTER);
		ScrollPane s1 = new ScrollPane();
		s1.setPrefSize(25, 500);
		s1.setContent(layout);
		Scene scene = new Scene(s1);
		window.setScene(scene);
		window.showAndWait();
	}
	public static void displayTable(String title, String messageLeft, String messageRight) {
		//Alert box with 2 sides, used to show info

		BorderPane border = new BorderPane();
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(460);
		Label label = new Label();
		label.setText(messageLeft);
		Label label2 = new Label();
		label2.setText(messageRight);
		Button closebutton = new Button("Close");
		closebutton.setOnAction(e -> window.close());
		HBox right = new HBox();
		VBox layout = new VBox(10);
		right.getChildren().addAll(label2);
		layout.getChildren().addAll(label, closebutton);
		layout.setAlignment(Pos.CENTER);
		border.setLeft(layout);
		border.setCenter(right);
		Scene scene = new Scene(border);
		window.setScene(scene);
		window.setWidth(300);
		window.showAndWait();
}

}
