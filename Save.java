package financeGUI;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import com.itextpdf.text.DocumentException;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Save {
	static List<FinancialAccounts> accounts = new LinkedList<FinancialAccounts>();
	static Stage window = new Stage();
	static Button button;
	static Button back;
	static Scene scene;
	static String file = "C:\\Users\\LuLele\\Desktop\\MortgagesInfo.csv";
	
	public static void display(List<FinancialAccounts> accounts) {
		//This method let the user choose what mortgage to save as pdf file.
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setMinWidth(450);
		ScrollPane s1 = new ScrollPane();
		window.setTitle("Save PDF");
		Label message = new Label("Choose mortgage to Save");
		VBox box = new VBox();
		box.getChildren().addAll(message);
		
		for(FinancialAccounts account : accounts) {
			account.adjustRate();
			account.calcDates();
			double[][] quotes = account.calculateMortgage();
			Button button = new Button(account.getName()+ " "+account.getDateStart()+ " "+account.riskProfile);
			button.setOnAction(e->{ 
				try {account.savePdf(quotes);} 
				catch (FileNotFoundException | DocumentException e1) {AlertBox.display("Error", e1.getMessage());}
				AlertBox.display("", "Pdf saved for "+account.getName());});

			box.getChildren().addAll(button);
			}
		
		s1.setContent(box);
		scene = new Scene (s1, 190, 400);
		window.setScene(scene);
		window.showAndWait();
	}
}
